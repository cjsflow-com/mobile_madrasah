package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.UpdatePasswordBottomSheetBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UpdateStudent : AppCompatActivity()
{

    private lateinit var updateBinding: UpdatePasswordBottomSheetBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = UpdatePasswordBottomSheetBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val role = intent.getStringExtra(Constant.TYPE_ROLE)
        val type = intent.getIntExtra(Constant.TYPE,-1)

        onBackPressedDispatcher.addCallback(this@UpdateStudent,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@UpdateStudent)
            }

        })

        updateBinding.apply {
            tvBack.setOnClickListener {
                startActivity(Intent(this@UpdateStudent,MainActivity::class.java))
                finish()
            }
            btnSavePassword.setOnClickListener {
                val textPassword = updateBinding.tilPassword.editText?.text.toString().trim()
                if(textPassword.isEmpty())
                {
                    Help.showToast(this@UpdateStudent,"Password tidak boleh kosong")
                }else{
                    role?.let { it1 -> action(type, it1, password =textPassword ) }
                }
            }
            progressBar.visibility = View.GONE
            btnSavePassword.visibility = View.VISIBLE
        }
    }

    private fun action(type: Int,role: String,password: String)
    {
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                if(type == 1)
                {
                    if (role == "siswa")
                    {
                        data.token?.let { actionUpdatePasswordStudent(it,password) }
                    }else{
                        data.token?.let { actionUpdatePasswordEmployee(it,password) }
                    }
                }else if(type == 2)
                {
                    if(role == "siswa")
                    {
                        data.token?.let {  }
                    }else{
                        data.token?.let {  }
                    }
                }
            }
        }

    }

    private fun actionUpdatePasswordStudent(token: String,password: String)
    {
        allViewModel.updatePasswordStudent(token,password).observe(this@UpdateStudent){ state ->
            when(state)
            {
                is States.Loading -> {
                    isShowProgress(true)
                }
                is States.Success -> {
                    Help.showToast(this@UpdateStudent,state.data.message)
                    actionBackToHome()
                    isShowProgress(false)
                }
                is States.Failed -> {
                    Help.showToast(this@UpdateStudent,state.message)
                    actionBackToHome()
                    isShowProgress(false)
                }
            }
        }
    }

    private fun actionUpdatePasswordEmployee(token: String,password: String)
    {
        allViewModel.updatePasswordEmployee(token,password).observe(this@UpdateStudent){ state ->
            when(state){
                is States.Loading -> {
                    isShowProgress(true)
                }
                is States.Success -> {
                    isShowProgress(false)
                    Help.showToast(this@UpdateStudent,state.data)
                    actionBackToHome()
                }
                is States.Failed -> {
                    isShowProgress(false)
                    Help.showToast(this@UpdateStudent,state.message)
                    actionBackToHome()
                }
            }
        }
    }

    private fun actionBackToHome()
    {
        startActivity(Intent(this@UpdateStudent,MainActivity::class.java))
        finish()
    }

    private fun isShowProgress(isShow: Boolean)
    {
        updateBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnSavePassword.visibility = if(isShow) View.GONE else View.VISIBLE
        }
    }


}
