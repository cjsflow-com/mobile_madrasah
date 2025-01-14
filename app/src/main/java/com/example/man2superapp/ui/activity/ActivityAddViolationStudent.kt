package com.example.man2superapp.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityAddViolationStudentBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityAddViolationStudent : AppCompatActivity()
{
    private lateinit var addViolationBinding: ActivityAddViolationStudentBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private var studentMap: Map<String,Int>? = null
    private var violationMap: Map<String,Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addViolationBinding = ActivityAddViolationStudentBinding.inflate(layoutInflater)
        setContentView(addViolationBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ model ->
                model.token?.let {
                    allViewModel.fetchAllStudent(it)
                    actionComponent(it)
                }
            }
        }
        observerView()
    }


    private fun actionComponent(token: String)
    {
        addViolationBinding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            floatingBtnSubmit.setOnClickListener {
                validation(token)
            }
        }
    }

    private fun validation(token: String)
    {
        addViolationBinding.apply {
            val tilStudent = parentNameStudent.editText?.text.toString().trim()
            val tilSchoolViolation = parentViolation.editText?.text.toString().trim()

            if (tilStudent.isEmpty() || tilSchoolViolation.isEmpty())
            {
                Help.showToast(this@ActivityAddViolationStudent,"Inputan siswa dan pelanggaran tidak boleh kosong")
            }else{
                val idFromStudent = studentMap?.get(tilStudent)
                val idFromViolationMaster = violationMap?.get(tilSchoolViolation)
                allViewModel.createViolationStudent(token,idFromStudent,idFromViolationMaster).observe(this@ActivityAddViolationStudent)
                { state ->
                    when(state)
                    {
                        is States.Loading -> {}
                        is States.Success -> {
                            if (state.data.success)
                            {
                                Help.showToast(this@ActivityAddViolationStudent,state.data.message)
                                finish()
                            }else{
                                Help.showToast(this@ActivityAddViolationStudent,state.data.message)
                            }
                        }
                        is States.Failed -> {
                            Help.showToast(this@ActivityAddViolationStudent,state.message)
                        }
                    }
                }
            }
        }
    }

    private fun observerView()
    {
        allViewModel.allViolationMaster.observe(this@ActivityAddViolationStudent){violations ->
            val violationMap = violations.associateBy({it.nameSchoolViolation},{it.id})
            val adapter = ArrayAdapter(
                this@ActivityAddViolationStudent,
                android.R.layout.simple_dropdown_item_1line,
                violations.map { it.nameSchoolViolation })
            addViolationBinding.etViolation.setAdapter(adapter)
            this@ActivityAddViolationStudent.violationMap = violationMap
        }
        allViewModel.allStudent.observe(this@ActivityAddViolationStudent){students ->
            val studentMap = students.associateBy({it.name},{it.id })
            val adapter = ArrayAdapter(
                this@ActivityAddViolationStudent,
                android.R.layout.simple_dropdown_item_1line,
                students.map { it.name }
            )
            addViolationBinding.etNameStudent.setAdapter(adapter)
            this@ActivityAddViolationStudent.studentMap = studentMap
        }
    }
}