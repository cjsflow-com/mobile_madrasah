package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivitySongketBinding
import com.example.man2superapp.databinding.BottomSheetDialogSongketMotherBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.ListSongketMother
import com.example.man2superapp.source.local.model.SongketMotherGTK
import com.example.man2superapp.source.local.model.toGenerateListSongketMother
import com.example.man2superapp.source.local.model.toGenerateListSongketMotherGtk
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.adapter.SongketMotherAdapterGtk
import com.example.man2superapp.ui.adapter.SongketMotherAdapterStudent
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SongketActivity : AppCompatActivity() {

    private lateinit var songketBinding: ActivitySongketBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private val adapterSongketList by lazy { SongketMotherAdapterStudent(::onEdit,::onAdd) }
    private val adapterSongketListGtk by lazy { SongketMotherAdapterGtk(::onEditGtk, ::onAddServiceGtk) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songketBinding = ActivitySongketBinding.inflate(layoutInflater)
        setContentView(songketBinding.root)
        setCurrentDate()
        onBackPressedDispatcher.addCallback(this@SongketActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@SongketActivity)
            }
        })
        lifecycleScope.launch {
            localStore.getToken().collect{data ->
                cardAction(data.id!!)
                setCountStatus(data.id, data.role!!,data.token!!)
                clickCard(data.id,data.role,data.token)
                isShowGtk(data.role)
            }
        }
        songketBinding.apply {
            ivBack.setOnClickListener {
                startActivity(Intent(this@SongketActivity,MainActivity::class.java))
                finish()
            }
        }
    }

    private fun isShowGtk(role: String)
    {
        songketBinding.apply {
            if(role == "siswa")
            {
                ekinCard.visibility = View.VISIBLE
                BBaik.visibility = View.VISIBLE
                club.visibility = View.VISIBLE
                univ.visibility = View.VISIBLE
                peringkat.visibility = View.VISIBLE
                cardAcceptedLeader.visibility  = View.VISIBLE
                cardSendLetter.visibility = View.GONE
            }else{
                eHolidayYear.visibility = View.VISIBLE
                eActivateTeaching.visibility = View.VISIBLE
                eRecomendation.visibility = View.VISIBLE
                eTask.visibility = View.VISIBLE
                univ.visibility = View.GONE
            }
        }

    }

    private fun setCountStatus(id: Int,role: String,token: String)
    {
        songketBinding.apply {
            if (role == "siswa") {
                allViewModel.getCountStatus(id).observe(this@SongketActivity) { state ->
                    when (state) {
                        is States.Loading -> {
                            tvAccepted.text = "--/--"
                            tvQueue.text = "--/--"
                            tvRejected.text = "--/--"
                            tvAcceptedLeader.text = "--/--"
                        }

                        is States.Success -> {
                            tvAcceptedLeader.text = state.data.completed.toString()
                            tvAccepted.text = state.data.accepted.toString()
                            tvQueue.text = state.data.queue.toString()
                            tvRejected.text = state.data.reject.toString()
                        }

                        is States.Failed -> {
                            tvAcceptedLeader.text = "0"
                            tvAccepted.text = "0"
                            tvQueue.text = "0"
                            tvRejected.text = "0"
                            Help.showToast(this@SongketActivity, state.message)
                        }
                    }
                }
            }else{
                allViewModel.getCountStatusSongketMotherGtk(token).observe(this@SongketActivity){ state ->
                    when(state)
                    {
                        is States.Loading -> {
                            tvAccepted.text = "--/--"
                            tvQueue.text = "--/--"
                            tvRejected.text = "--/--"
                            tvAcceptedLeader.text = "--/--"
                            tvSendLetter.text = "--/--"
                        }
                        is States.Success -> {
                            tvAccepted.text = state.data.acceptedLeaderMadrasah.toString()
                            tvQueue.text = state.data.queue.toString()
                            tvRejected.text = state.data.rejected.toString()
                            tvAcceptedLeader.text = state.data.done.toString()
                            tvSendLetter.text = state.data.requestLeaderMadrasah.toString()

                        }
                        is States.Failed -> {
                            tvAccepted.text = "0"
                            tvAcceptedLeader.text = "0"
                            tvSendLetter.text = "0"
                            tvQueue.text = "0"
                            tvRejected.text = "0"
                        }
                    }
                }
            }
        }
    }

    private fun setCurrentDate() {
        // Format tanggal: "EEEE, d MMMM" (Contoh: "Rabu, 9 Oktober")
        val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("id", "ID"))
        val currentDate = dateFormat.format(Date())

        // Atur teks ke TextView
        songketBinding.tvDate.text = currentDate
    }

    private fun showAlertDialog(title: String,message: String,letterStatement: Int,id: Int)
    {
        MaterialAlertDialogBuilder(this@SongketActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ya"){dialog, _ ->
                    allViewModel.createSongketMother(letterStatement,"","","","",
                        id,"","","","","",0.0
                    ).observe(this@SongketActivity){state ->
                        when(state){
                            is States.Loading -> {}
                            is States.Success -> {
                                Intent(this@SongketActivity,MainActivity::class.java).also {
                                    startActivity(it)
                                }.also { finish() }
                                Help.showToast(this@SongketActivity,state.data.message)
                                dialog.dismiss()
                            }
                            is States.Failed -> {
                                Help.showToast(this@SongketActivity,state.message)
                                dialog.dismiss()
                            }
                        }
                    }
            }
            .setNegativeButton("Tidak"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun clickCard(id: Int,role: String, token: String)
    {
        songketBinding.apply {
            cardAccepted.setOnClickListener {
                showBottomDialogShow("setuju",id,role,token)
            }
            cardQueue.setOnClickListener {
                showBottomDialogShow("antrian",id,role,token)
            }
            cardRejected.setOnClickListener {
                showBottomDialogShow("tolak",id,role,token)
            }
            cardAcceptedLeader.setOnClickListener {
                showBottomDialogShow("selesai",id,role,token)
            }
            cardSendLetter.setOnClickListener {
                showBottomDialogShow("ajukan",id,role,token)
            }
        }
    }

    private fun onEdit(songketMother: ListSongketMother)
    {
        Intent(this@SongketActivity,EditSongketActivity::class.java).apply {
            putExtra(Constant.LETTER_TYPE,songketMother.letterStatement)
            putExtra(Constant.TYPE,songketMother.id)
            putExtra(Constant.LIST_SONGKET_MOTHER,songketMother)
        }.also { startActivity(it) }
    }

    private fun onAdd(songketMother: ListSongketMother)
    {
        Intent(this@SongketActivity,AddServiceActivity::class.java).apply {
            putExtra(Constant.LETTER_TYPE,songketMother.letterStatement)
            putExtra(Constant.TYPE,songketMother.id)
        }.also { startActivity(it) }
    }

    private fun onAddServiceGtk(songketMotherGtk: SongketMotherGTK)
    {
        Intent(this@SongketActivity,AddServiceActivity::class.java).apply {
            putExtra(Constant.LETTER_TYPE,songketMotherGtk.letterStatement)
            putExtra(Constant.TYPE,songketMotherGtk.id)
        }.also { startActivity(it) }
    }



    private fun onEditGtk(songketMotherGtk: SongketMotherGTK)
    {
        Intent(this@SongketActivity,EditSongketGtkActivity::class.java).apply {
            putExtra(Constant.LETTER_TYPE,songketMotherGtk.letterStatement)
            putExtra(Constant.TYPE,songketMotherGtk.id)
            putExtra(Constant.LIST_SONGKET_MOTHER,songketMotherGtk)
        }.also { startActivity(it) }
    }

    private fun showBottomDialogShow(status: String,id: Int,role: String,token: String)
    {
        val dialog = BottomSheetDialog(this@SongketActivity)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_songket_mother,null)
        dialog.setContentView(view)

        val tvStatus = view.findViewById<MaterialTextView>(R.id.tvTitleStatus)
        tvStatus.text = "Status: $status"
        val rvSongketMotherStatus = view.findViewById<RecyclerView>(R.id.rv_item_songket_mother)
        val progresBar = view.findViewById<ProgressBar>(R.id.progressBarSongket)
        val tvError = view.findViewById<MaterialTextView>(R.id.tvError)

        if(role == "siswa")
        {
            allViewModel.listSongketMotherByStatus(id,status).observe(this@SongketActivity){ state ->
                when(state){
                    is States.Loading -> {
                        isShowProgressBar(true,rvSongketMotherStatus,progresBar)
                    }
                    is States.Success -> {
                        val songketList = state.data.songketEmak.toGenerateListSongketMother()
                        if(songketList.isEmpty())
                        {
                            progresBar.visibility = View.GONE
                            rvSongketMotherStatus.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = "Tidak ada data sama sekali"
                        }
                        rvSongketMotherStatus.layoutManager = LinearLayoutManager(this@SongketActivity)
                        rvSongketMotherStatus.adapter = adapterSongketList
                        adapterSongketList.submitListData(songketList)
                        isShowProgressBar(false,rvSongketMotherStatus,progresBar)
                    }
                    is States.Failed -> {
                        progresBar.visibility = View.GONE
                        rvSongketMotherStatus.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        tvError.text = "Terjadi kesalahan pada sistem"
                        Help.showToast(this@SongketActivity,state.message)
                    }
                }
            }
        }else{
            allViewModel.getByStatusSongketMotherGtk(token,status).observe(this@SongketActivity){ state ->
                when(state)
                {
                    is States.Loading -> {isShowProgressBar(true,rvSongketMotherStatus,progresBar)}
                    is States.Success -> {
                        val dataSongketMotherGkt = state.data.songketEmak.toGenerateListSongketMotherGtk()
                        if(dataSongketMotherGkt.isEmpty())
                        {
                            progresBar.visibility = View.GONE
                            rvSongketMotherStatus.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = "Tidak ada data sama sekali"
                        }
                        rvSongketMotherStatus.layoutManager = LinearLayoutManager(this@SongketActivity)
                        rvSongketMotherStatus.adapter = adapterSongketListGtk
                        adapterSongketListGtk.submitListData(dataSongketMotherGkt)
                        isShowProgressBar(false,rvSongketMotherStatus,progresBar)
                    }
                    is States.Failed -> {
                        progresBar.visibility = View.GONE
                        rvSongketMotherStatus.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        tvError.text = "Terjadi kesalahan pada sistem"
                        Help.showToast(this@SongketActivity,state.message)
                    }
                }
            }
        }

        dialog.setContentView(view)

        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
        }

        dialog.show()
    }

    private fun isShowProgressBar(isShow: Boolean,rv: RecyclerView, progressBar: ProgressBar)
    {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
        rv.visibility = if(isShow) View.GONE else View.VISIBLE
    }


    private fun cardAction(id: Int)
    {
        songketBinding.apply {
            ekinCard.setOnClickListener {
                showAlertDialog("Konfirmasi buat Surat Keterangan Aktif Sekolah",
                    "Apakah anda yakin ingin membuat surat keterangan aktif sekolah",1,
                    id)
            }

            club.setOnClickListener {
                Intent(this@SongketActivity,CreateSongket::class.java).apply {
                    putExtra(Constant.LETTER_TYPE,3)
                    putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Ekskul/Club")
                }.also { startActivity(it)}.also { finish() }
            }

            peringkat.setOnClickListener {
                Intent(this@SongketActivity,CreateSongket::class.java).apply {
                    putExtra(Constant.LETTER_TYPE,5)
                    putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Peringkat")
                }.also { startActivity(it)}.also { finish() }
            }

            univ.setOnClickListener {
                Intent(this@SongketActivity,CreateSongket::class.java).apply {
                    putExtra(Constant.LETTER_TYPE,6)
                    putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Rekomendasi Universitas")
                }.also { startActivity(it) }.also { finish() }
            }

            BBaik.setOnClickListener {
                showAlertDialog("Konfirmasi buat Surat Keterangan Berkelakuan Baik",
                    "Apakah anda yakin ingin membuat surat keterangan berkelakuan baik",2,
                    id)
            }
            eHolidayYear.setOnClickListener {
                Intent(this@SongketActivity,CreateSongketGtk::class.java)
                    .apply {
                        putExtra(Constant.LETTER_TYPE,1)
                        putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Cuti Tahunan")
                    }.also { startActivity(it) }.also { finish() }
            }
            eActivateTeaching.setOnClickListener {
                Intent(this@SongketActivity,CreateSongketGtk::class.java)
                    .apply {
                        putExtra(Constant.LETTER_TYPE,2)
                        putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Aktif Mengajar")
                    }.also { startActivity(it) }.also { finish() }
            }
            eRecomendation.setOnClickListener {
                Intent(this@SongketActivity,CreateSongketGtk::class.java)
                    .apply {
                        putExtra(Constant.LETTER_TYPE,3)
                        putExtra(Constant.LETTER_STATEMENT,"Surat Rekomendasi")
                    }.also { startActivity(it) }.also { finish() }
            }
        }
    }
}