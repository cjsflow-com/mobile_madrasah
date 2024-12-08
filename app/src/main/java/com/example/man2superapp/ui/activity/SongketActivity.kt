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
import com.example.man2superapp.source.local.model.toGenerateListSongketMother
import com.example.man2superapp.source.network.States
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
    private val adapterSongketList by lazy { SongketMotherAdapterStudent(::onEdit) }

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
                data.id?.let {
                    cardAction(it)
                    setCountStatus(it)
                    clickCard(it)
                }
            }
        }
    }

    private fun setCountStatus(id: Int)
    {
        songketBinding.apply {
            allViewModel.getCountStatus(id).observe(this@SongketActivity){
                state ->
                when(state){
                    is States.Loading -> {
                        tvAccepted.text = "--/--"
                        tvQueue.text = "--/--"
                        tvRejected.text = "--/--"
                    }
                    is States.Success -> {
                        tvAccepted.text = state.data.accepted.toString()
                        tvQueue.text = state.data.queue.toString()
                        tvRejected.text = state.data.reject.toString()
                    }
                    is States.Failed -> {
                        tvAccepted.text = "Tidak ada data"
                        tvQueue.text = "Tidak ada data"
                        tvRejected.text = "Tidak ada data"
                        Help.showToast(this@SongketActivity,state.message)
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

    private fun clickCard(id: Int)
    {
        songketBinding.apply {
            cardAccepted.setOnClickListener {
                showBottomDialogShow("setuju",id)
            }
            cardQueue.setOnClickListener {
                showBottomDialogShow("antrian",id)
            }

            cardRejected.setOnClickListener {
                showBottomDialogShow("tolak",id)
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

    private fun showBottomDialogShow(status: String,id: Int)
    {
        val dialog = BottomSheetDialog(this@SongketActivity)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_songket_mother,null)
        dialog.setContentView(view)

        val tvStatus = view.findViewById<MaterialTextView>(R.id.tvTitleStatus)
        tvStatus.text = "Status: $status"
        val rvSongketMotherStatus = view.findViewById<RecyclerView>(R.id.rv_item_songket_mother)
        val progresBar = view.findViewById<ProgressBar>(R.id.progressBarSongket)
        val tvError = view.findViewById<MaterialTextView>(R.id.tvError)

        allViewModel.listSongketMotherByStatus(id,status).observe(this@SongketActivity){ state ->
            when(state){
                is States.Loading -> {
                    progresBar.visibility = View.VISIBLE
                    rvSongketMotherStatus.visibility = View.GONE
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
                    progresBar.visibility = View.GONE
                    rvSongketMotherStatus.visibility = View.VISIBLE
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

        dialog.setContentView(view)

        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
        }

        dialog.show()

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
                    Intent(this@SongketActivity,CreateSongket::class.java).apply {
                        putExtra(Constant.LETTER_TYPE,3)
                        putExtra(Constant.LETTER_STATEMENT,"Surat Keterangan Ekskul/Club")
                    }.also { startActivity(it)}.also { finish() }
                }
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
        }
    }
}