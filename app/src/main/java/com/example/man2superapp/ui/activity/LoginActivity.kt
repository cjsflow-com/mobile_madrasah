package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityLoginBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val loginViewModel by viewModels<AllViewModel>()
    @Inject
    lateinit var localStore: LoginTemp

    companion object{
        const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java)).also { finish() }
        }
    }

    private fun action()
    {
        with(loginBinding)
        {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if(email.isEmpty() && password.isEmpty())
            {
                Help.showToast(this@LoginActivity,"Email dan Password tidak boleh kosong")
            }else{
                loginViewModel.loginEmployee(email,password).observe(this@LoginActivity){state ->

                }
            }
        }
    }
}