package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityLoginStudentBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginStudent: AppCompatActivity()
{

    private lateinit var loginStudentBinding: ActivityLoginStudentBinding
    private val loginStudentViewModel by viewModels<AllViewModel>()
    @Inject
    lateinit var localStore: LoginTemp

    companion object{
        const val TAG = "LoginActivityStudent"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStudentBinding = ActivityLoginStudentBinding.inflate(layoutInflater)
        setContentView(loginStudentBinding.root)

        onBackPressedDispatcher.addCallback(this@LoginStudent,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@LoginStudent)
            }
        })

        with(loginStudentBinding){
            backButton.setOnClickListener {
                startActivity(Intent(this@LoginStudent,MainActivity::class.java))
                    .also { finish() }
            }
            loginButton.setOnClickListener {
                action()
            }
        }
    }

    private fun action()
    {
        with(loginStudentBinding){
            val nisn = nisnEditText.text.toString()
            val password = passwordEditText.text.toString()
            if(nisn.isEmpty() && password.isEmpty())
            {
                Help.showToast(this@LoginStudent,"Email dan Password tidak boleh kosong")
            }else{
                loginStudentViewModel.loginStudent(nisn,password).observe(this@LoginStudent){ state ->
                    when(state)
                    {
                        is States.Loading -> {
                            isShoProgressBar(true)
                        }
                        is States.Success -> {
                            isShoProgressBar(false)
                            lifecycleScope.launch {
                                Log.d(TAG, "action: ${state.data.message}")
                                if(state.data.success)
                                {
                                    Help.showToast(this@LoginStudent,state.data.message)
                                    localStore.putToken(
                                        LoginModel(state.data.student?.name,state.data.student?.id,state.data.student?.email
                                            ,state.data.student?.nisn,state.data.student?.className,state.data.student?.gender,
                                            state.data.token,state.data.student?.profile,state.data.student?.nameMother,state.data.student?.nameFather,state.data.student?.numberHandphone,"",state.data.student?.address,state.data.student?.dateBirthday,state.data.student?.placeBirthday,"siswa")
                                            .also { startActivity(Intent(this@LoginStudent,MainActivity::class.java))
                                                .also { finish() }}
                                    )
                                }else{
                                    Help.showToast(this@LoginStudent,state.data.message)
                                }
                            }
                        }
                        is States.Failed -> {
                            isShoProgressBar(false)
                            Help.showToast(this@LoginStudent,state.message)
                        }
                    }
                }
            }
        }
    }

    private fun isShoProgressBar(isShow: Boolean)
    {
        loginStudentBinding.apply {
            loginButton.visibility = if(isShow) View.GONE else View.VISIBLE
            progressBar.visibility = if(isShow) View.VISIBLE else View.GONE
        }
    }
}