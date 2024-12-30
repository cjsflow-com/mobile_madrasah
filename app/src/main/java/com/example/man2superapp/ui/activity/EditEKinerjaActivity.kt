package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.EkinAfter
import com.example.man2superapp.R
import com.example.man2superapp.databinding.EditEKinerjaBinding
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
class EditEKinerjaActivity : AppCompatActivity() {

    private lateinit var editBinding: EditEKinerjaBinding
    private val allViewModel by viewModels<AllViewModel>()

    @Inject
    lateinit var localStore: LoginTemp
    private var tasksMap: Map<String,Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBinding = EditEKinerjaBinding.inflate(layoutInflater)
        setContentView(editBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ model ->
                model.token?.let { token ->
                    allViewModel.fetchTask(token,this@EditEKinerjaActivity)
                }
            }
        }
        val result = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(Constant.E_KINERJA, ResutlEmployeePerformance::class.java)
        }else{
            intent.getParcelableExtra<ResutlEmployeePerformance>(Constant.E_KINERJA)
        }

        onBackPressedDispatcher.addCallback(this@EditEKinerjaActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                backToEkinAfter()
            }
        })

        editBinding.apply {
            parentNameTupoksi.isEnabled = false
            backButton.setOnClickListener {
                backToEkinAfter()
            }
            tvMan.text = "Ubah E-Kinerja"
            btnSubmit.text = "Ubah"
            result?.apply {
                if(nameDataSupport.isEmpty() || linkGoogleDrive.isEmpty())
                {
                    parentNameDataSupport.visibility = View.GONE
                    parentLinkGoogleDrive.visibility = View.GONE
                }
                val target = performanceTargetEvaluation.toString()
                etPerformanceEvaluationPlan.setText(performanceEvaluationPlan)
                etPerformanceTargetEvaluation.setText(target)
                etNameDataSupport.setText(nameDataSupport)
                etLinkGoogleDrive.setText(linkGoogleDrive)

                btnSubmit.setOnClickListener {
                    lifecycleScope.launch {
                        localStore.getToken().collect{ model ->
                            model.token?.let { it1 -> validation(it1,id) }
                        }
                    }
                }

                allViewModel.listTask.observe(this@EditEKinerjaActivity){listTask ->
                    val taskMap = listTask.associateBy({it.nameTask},{it.id})
                    val adapter = ArrayAdapter(
                        this@EditEKinerjaActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        listTask.map { "${it.nameTask} - (${it.period} laporan)" }
                    )
                    val matchingTask = listTask.find { it.nameTask == taskName}
                    matchingTask.let {
                        etNameTupoksi.setText(it?.nameTask)
                    }
                    this@EditEKinerjaActivity.tasksMap = taskMap
                    etNameTupoksi.setAdapter(adapter)
                }
            }
        }

    }

    private fun handleResponse(state: States<UpdateSongketMother>)
    {
        when(state){
            is States.Loading -> {
                isShowProgress(true)
            }
            is States.Success -> {
                isShowProgress(false)
                val message = if(state.data.success) state.data.message else state.data.message
                Help.showToast(this@EditEKinerjaActivity,message).also { backToEkinAfter() }
            }
            is States.Failed -> {
                isShowProgress(false)
                Help.showToast(this@EditEKinerjaActivity,state.message).also { backToEkinAfter() }
            }
        }
    }

    private fun isShowProgress(isShow:Boolean)
    {
        editBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnSubmit.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun validation(token: String,id: Int)
    {
        editBinding.apply {
            val nameTupoksi = parentNameTupoksi.editText?.text.toString().trim()
            val nameDataSupport = parentNameDataSupport.editText?.text.toString().trim()
            val linkGoogleDrive = parentLinkGoogleDrive.editText?.text.toString().trim()
            val targetReport = parentPerformanceTargetEvaluation.editText?.text.toString().trim()
            val planAction = parentPerformanceEvaluationPlan.editText?.text.toString().trim()

            if (nameTupoksi.isNotEmpty() && targetReport.isNotEmpty() && planAction.isNotEmpty())
            {
                allViewModel.updateEmployeePerformance(token,id,planAction,targetReport.toInt(),nameDataSupport,linkGoogleDrive)
                    .observe(this@EditEKinerjaActivity){model -> handleResponse(model)}
            }else{
                Help.showToast(this@EditEKinerjaActivity,"Semua field wajib diisi")
            }
        }
    }

    private fun backToEkinAfter()
    {
        startActivity(Intent(this@EditEKinerjaActivity,EkinAfter::class.java))
        finish()
    }
}