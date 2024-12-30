package com.example.man2superapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ActivityEkinAfterBinding
import com.example.man2superapp.databinding.AddRealiationKinerjaBinding
import com.example.man2superapp.databinding.DetailBottomDialogShowBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.ResutlEmployeePerformance
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.activity.AddActivityEkinerja
import com.example.man2superapp.ui.activity.EditEKinerjaActivity
import com.example.man2superapp.ui.activity.MainActivity
import com.example.man2superapp.ui.adapter.ResultEkinerjaAdapter
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class EkinAfter : AppCompatActivity() {
    private lateinit var eKinBinding: ActivityEkinAfterBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private val adapterEkin by lazy { ResultEkinerjaAdapter(::onDetail,::onEdit,::onAddCreation) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eKinBinding = ActivityEkinAfterBinding.inflate(layoutInflater)
        setContentView(eKinBinding.root)
        setLayoutManagerRecyclerView()
        lifecycleScope.launch {
            localStore.getToken().collect{
                it.token?.let { it1 -> dataViewModel(it1) }
            }
        }

        onBackPressedDispatcher.addCallback(this@EkinAfter,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@EkinAfter)
            }
        })
        observerViewModel()
        eKinBinding.apply {
            btnBack.setOnClickListener {
                navigateBack()
            }
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            tvRencanaKerja.text = "Rencana Kerja Tahun ${currentYear}"
            addEkinerja.setOnClickListener {
                addEkinerja()
            }
        }
        setCurrentDate()
    }

    private fun setCurrentDate()
    {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM",Locale("id","ID"))
        val currentDate = dateFormat.format(Date())
        eKinBinding.tvTanggal.text = currentDate
    }

    private fun addEkinerja()
    {
        startActivity(Intent(this@EkinAfter,AddActivityEkinerja::class.java))
        finish()
    }

    private fun observerViewModel()
    {
        eKinBinding.apply {
            allViewModel.listEkinerja.observe(this@EkinAfter){ model ->
                if(model.isEmpty())
                {
                    tvEmpty.text = "Tidak ada data sama sekali"
                    tvEmpty.visibility = View.VISIBLE
                    rvEkinerja.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
                adapterEkin.submitListData(model)
            }
            allViewModel.hasApprovedTask.observe(this@EkinAfter){
                if (it){
                    addEkinerja.setBackgroundColor(ContextCompat.getColor(this@EkinAfter,R.color.mangold))
                    addEkinerja.isEnabled = true
                }else{
                    addEkinerja.setBackgroundColor(ContextCompat.getColor(this@EkinAfter,R.color.gray))
                    addEkinerja.isEnabled = false
                }
            }
            allViewModel.loading.observe(this@EkinAfter){isLoading ->
                isShow(progressBar,rvEkinerja,isLoading)
            }
        }
    }

    private fun setLayoutManagerRecyclerView()
    {
        eKinBinding.apply {
            rvEkinerja.layoutManager = LinearLayoutManager(this@EkinAfter)
            rvEkinerja.adapter = adapterEkin
        }
    }

    private fun onDetail(result: ResutlEmployeePerformance)
    {
        val dialog = BottomSheetDialog(this@EkinAfter)
        val view = DetailBottomDialogShowBinding.inflate(layoutInflater)
        dialog.setContentView(view.root)

        view.apply {
            tvTitleStatus.text = "Detail E-Kinerja"
            result.apply {
                nameTask.text = "Tupoksi: ${taskName}"
                nameAction.text = "Rencana Aksi: ${performanceEvaluationPlan}"
                period.text = "Target Laporan Triwulan: ${performanceTargetEvaluation}"
                triwulanEkinerja.text = "Triwulan: $triwulan"
                tvRealitation.text = "Realisasi: ${nameDataSupport?: "Belum ada realisasi"}"
                ratingHasilKerja.text = "Rating Hasil Kerja: ${ratingEvaluationWork?: "Belum ada rating hasil kerja"}"
                predikatKerja.text = "Predikat Kerja: ${predicateWork?: "Belum ada predikat kerja"}"
            }
        }

        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet?.let {
           val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog.show()
    }

    private fun onEdit(resultEmployeePerformance: ResutlEmployeePerformance)
    {
        Intent(this@EkinAfter,EditEKinerjaActivity::class.java).apply {
            putExtra(Constant.E_KINERJA,resultEmployeePerformance)
        }.also { startActivity(it) }
    }

    private fun onAddCreation(result: ResutlEmployeePerformance)
    {
        val dialog = BottomSheetDialog(this@EkinAfter)
        val view = AddRealiationKinerjaBinding.inflate(layoutInflater)
        dialog.setContentView(view.root)
        view.apply {
            tvTitleAddRealitation.text = "Tambah Realisasi E-Kinerja"
            nameTask.text = "Tupoksi: ${result.taskName}"
            nameAction.text = "Rencana Aksi: ${result.performanceEvaluationPlan}"
            period.text = "Target Laporan Triwulan: ${result.performanceTargetEvaluation} Lapooran"
            triwulanEkinerja.text = "Triwulan: ${result.triwulan}"
            btnAddRealitation.setOnClickListener {
                val nameDataSupport = parentNameDataSupport.editText?.text.toString()
                val linkGoogleDrive = parentLinkGoogleDrive.editText?.text.toString()
                lifecycleScope.launch {
                    localStore.getToken().collect{ data ->
                        if(nameDataSupport.isEmpty() || linkGoogleDrive.isEmpty())
                        {
                            Help.showToast(this@EkinAfter,"Nama data pendukung dan link google drive tidak boleh kosong")
                        }else{
                            data.token?.let { allViewModel.createRealitation(it,result.id,nameDataSupport,linkGoogleDrive).observe(this@EkinAfter){ state ->
                                when(state)
                                {
                                    is States.Loading -> {}
                                    is States.Success -> {
                                        dialog.dismiss()
                                        if(state.data.success) Help.showToast(this@EkinAfter,state.data.message)
                                        else Help.showToast(this@EkinAfter,state.data.message)
                                    }
                                    is States.Failed -> {
                                        dialog.dismiss()
                                        Help.showToast(this@EkinAfter,state.message)}
                                }
                            } }
                        }
                    }
                }
            }
        }
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog.show()
    }

    private fun isShow(progressBar: ProgressBar, recylerView: RecyclerView,isShow: Boolean)
    {
        progressBar.visibility = if(isShow) View.VISIBLE else View.GONE
        recylerView.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    private fun navigateBack()
    {
        startActivity(Intent(this@EkinAfter,MainActivity::class.java)).also { finish() }
    }

    private fun dataViewModel(token: String)
    {
        allViewModel.fetchTask(token,this@EkinAfter)
        allViewModel.fetchApprovedTask(token,this@EkinAfter)
        allViewModel.fetchResultEmployeePerformance(token,this@EkinAfter)
    }
}