package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.EkinAfter
import com.example.man2superapp.databinding.ActivityAddKinerjaBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.ResutlEmployeePerformance
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@Suppress("DEPRECATION")
class AddActivityEkinerja: AppCompatActivity()
{

    private lateinit var addEKinerjaBinding: ActivityAddKinerjaBinding
    private val allViewModel by viewModels<AllViewModel>()
    private var tupoksisMap: Map<String,Int>? = null
    @Inject
    lateinit var localStore: LoginTemp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEKinerjaBinding = ActivityAddKinerjaBinding.inflate(layoutInflater)
        setContentView(addEKinerjaBinding.root)
        allViewModel.listTask.observe(this@AddActivityEkinerja){taskList ->
            val taskMap = taskList.associateBy({"${it.nameTask} (${it.period} Laporan)"},{it.id})
            Log.d("AddActivityEkinerja", "onCreate: ${taskList.map { it.nameTask }}")
            val adapter = ArrayAdapter(this@AddActivityEkinerja,android.R.layout.simple_dropdown_item_1line,
                taskList.map { "${it.nameTask} (${it.period} Laporan)" })
            addEKinerjaBinding.etNameTupoksi.setAdapter(adapter)
            this@AddActivityEkinerja.tupoksisMap = taskMap
        }
        addEKinerjaBinding.apply {
            backButton.setOnClickListener {
                backToEkinerja()
            }
            btnSubmit.text = "Tambah E-Kinerja"
            tvMan.text = "Tambah E-Kinerja"
            parentNameDataSupport.visibility = View.GONE
            parentLinkGoogleDrive.visibility = View.GONE
            lifecycleScope.launch {
                localStore.getToken().collect{ data ->
                    data.token?.let { token ->
                        btnSubmit.setOnClickListener {
                            it1 -> validation(token)
                        }
                        allViewModel.fetchTask(token,this@AddActivityEkinerja)
                    }
                }
            }
        }
    }

    private fun validation(token: String)
    {
        addEKinerjaBinding.apply {
            val planAction = parentPerformanceEvaluationPlan.editText?.text.toString()
            val targetReport = parentPerformanceTargetEvaluation.editText?.text.toString()
            val parentTupoksi = parentNameTupoksi.editText?.text.toString().trim()

            if (parentTupoksi.isNotEmpty() && planAction.isNotEmpty() && targetReport.isNotEmpty())
            {
                val id = tupoksisMap?.get(parentTupoksi)
                if(id != null)
                {
                    allViewModel.createEmployeePerformance(token,
                        id,planAction,targetReport.toInt()).observe(this@AddActivityEkinerja){data -> handleResponse(data)}
                }else{
                    Help.showToast(this@AddActivityEkinerja,"terjadi kesalahan pada tupoksi")
                }
            }else{
                Help.showToast(this@AddActivityEkinerja,"Semua field wajib diisi")
            }
        }
    }

    private fun isShowProgress(isShow: Boolean)
    {
        addEKinerjaBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnSubmit.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }


    private fun handleResponse(state: States<UpdateSongketMother>)
    {
        when(state)
        {
            is States.Loading -> {isShowProgress(true)}
            is States.Success -> {
                isShowProgress(false)
                val message = if(state.data.success) state.data.message else state.data.message
                Help.showToast(this@AddActivityEkinerja,message).also { backToEkinerja() }
            }
            is States.Failed -> {
                isShowProgress(false)
                Help.showToast(this@AddActivityEkinerja,state.message).also { backToEkinerja() }
            }
        }
    }

    private fun backToEkinerja()
    {
        startActivity(Intent(this@AddActivityEkinerja,EkinAfter::class.java))
        finish()
    }
}