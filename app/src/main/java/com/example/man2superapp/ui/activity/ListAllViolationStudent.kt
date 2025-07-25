package com.example.man2superapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.man2superapp.databinding.ActivityLayoutListStudentBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.toNoteRejected
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.adapter.ViolationStudentAdapter
import com.example.man2superapp.ui.fragment.NoteRejectedFragment
import com.example.man2superapp.ui.fragment.ViolationDisputeFragment
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListAllViolationStudent: AppCompatActivity()
{
    @Inject
    lateinit var localStore: LoginTemp
    private lateinit var listALlViolationStudentBinding: ActivityLayoutListStudentBinding
    private val allViewModel by viewModels<AllViewModel>()
    lateinit var token: String
    private val listAllStudentAdapter by lazy { ViolationStudentAdapter(::onAddNoteDisputeViolation, ::onNoteRejectedViolation) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listALlViolationStudentBinding = ActivityLayoutListStudentBinding.inflate(layoutInflater)
        setContentView(listALlViolationStudentBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                token = data.token!!
                allViewModel.fetchAllViolationSchoolStudent(token)
            }
        }
        observer()
        setAdapter()
        actionComponent()
    }

    private fun actionComponent()
    {
        listALlViolationStudentBinding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun observer()
    {
        allViewModel.allViolationStudent.observe(this@ListAllViolationStudent){listAllStudentAdapter.submitListData(it)}
        allViewModel.textSuccess.observe(this@ListAllViolationStudent){Help.showToast(this@ListAllViolationStudent,it)}
        allViewModel.textError.observe(this@ListAllViolationStudent){Help.showToast(this@ListAllViolationStudent,it)}
        allViewModel.loading.observe(this@ListAllViolationStudent){isShowProgress(it)}
    }

    private fun isShowProgress(isShow: Boolean)
    {
        listALlViolationStudentBinding.apply {
            progressBarList.visibility = if (isShow) View.VISIBLE else View.GONE
            rvListViolationStudent.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun onAddNoteDisputeViolation(violationId: Int)
    {
        val dialog = ViolationDisputeFragment(context = this@ListAllViolationStudent, id = violationId,token,
            onSubmit = {
                id,token,reason -> allViewModel.createViolationDisputeStudent(token,id,reason)
            })
        dialog.show(supportFragmentManager,"ViolationDisputeFragment")
    }

    private fun onNoteRejectedViolation(id: Int)
    {
        allViewModel.getNoteDisputeViolation(token,id)
        allViewModel.noteRejected.observeOnce(this@ListAllViolationStudent){note ->
            showFragment(note)
        }
    }

    private fun setAdapter()
    {
        listALlViolationStudentBinding.rvListViolationStudent.apply {
            layoutManager = LinearLayoutManager(this@ListAllViolationStudent)
            adapter = listAllStudentAdapter
        }
    }

    private fun showFragment(note: String)
    {
        val dialog = NoteRejectedFragment(note,this@ListAllViolationStudent)
        dialog.show(supportFragmentManager,"NoteRejectedFragment")
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this)
            }

        })
    }
}
