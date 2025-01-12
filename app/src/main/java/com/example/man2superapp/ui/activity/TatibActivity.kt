package com.example.man2superapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.man2superapp.databinding.ETatibActivityBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.ui.adapter.ViolationMasterAdapter
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TatibActivity: AppCompatActivity()
{
    private lateinit var tatibBinding: ETatibActivityBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private val listAllViolationMasterAdapter by lazy { ViolationMasterAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tatibBinding = ETatibActivityBinding.inflate(layoutInflater)
        setContentView(tatibBinding.root)
        observerView()
        setAdapter()
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                data.token?.let { allViewModel.fetchAllViolationMaster(it) }
                data.role?.let { roles ->
                    tatibBinding.apply {
                        showAddViolation(roles)
                    }
                }
            }
        }
    }

    private fun showAddViolation(role: String)
    {
        tatibBinding.apply {
            floatingAddViolation.visibility = if (role == "guru") View.VISIBLE else View.GONE
            listViolationStudent.visibility = if(role == "guru") View.GONE else View.VISIBLE
        }
    }

    private fun observerView()
    {
        allViewModel.allViolationMaster.observe(this@TatibActivity){
            listAllViolationMasterAdapter.submitListData(it)
        }
        allViewModel.loading.observe(this@TatibActivity){showProgressBar(it)}
        allViewModel.textSuccess.observe(this@TatibActivity){ Help.showToast(this@TatibActivity,it)}
        allViewModel.textError.observe(this@TatibActivity){Help.showToast(this@TatibActivity,it)}
    }

    private fun setAdapter()
    {
        tatibBinding.rvItemViolationMaster.apply {
            layoutManager = LinearLayoutManager(this@TatibActivity)
            adapter = listAllViolationMasterAdapter
        }
    }

    private fun showProgressBar(isShow: Boolean)
    {
        tatibBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            rvItemViolationMaster.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }
}