package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityLoginBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
        isShowProgressBar(false)
        onBackPressedDispatcher.addCallback(this@LoginActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@LoginActivity)
            }

        })
        setupRegisterText()

        loginBinding.apply {
            backButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    .also { finish() }
            }
            loginButton.setOnClickListener {
                action()
            }
            loginAsStudent.setOnClickListener {
                startActivity(Intent(this@LoginActivity,LoginStudent::class.java))
                    .also { finish() }
            }
        }
    }

    private fun setupRegisterText() {
        val text = "Belum punya akun? Daftar"
        val spannableString = SpannableString(text)

        // Mengatur warna abu abu untuk "Belum punya akun?"
        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.gray)),
            0, 16,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Mengatur warna biru dan klik untuk "Daftar"
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Menuju halaman pendaftaran
//                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                Help.showToast(this@LoginActivity,"Fitur masih dalam tahap pengembangan")
            }
        }, 17, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(
            ForegroundColorSpan(getColor(R.color.manblue)), // Warna biru
            17, text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set text dan aktifkan LinkMovementMethod
        loginBinding.registeredTextView.text = spannableString
        loginBinding.registeredTextView.movementMethod = LinkMovementMethod.getInstance()
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
                    when(state)
                    {
                        is States.Loading -> {
                            isShowProgressBar(true)
                        }
                        is States.Success -> {
                            lifecycleScope.launch {
                                Help.showToast(this@LoginActivity,state.data.messages)
                                Log.d(TAG, "action: ${state.data.messages}")
                                localStore.putToken(
                                    LoginModel(state.data.name,state.data.id,state.data.email,"","",state.data.gender,state.data.token,state.data.profile,
                                        "","",state.data.numberPhone,state.data.numberPhone,"","","",state.data.role).also {
                                            startActivity(Intent(this@LoginActivity,MainActivity::class.java)).also { finish() }
                                    }
                                )
                                isShowProgressBar(false)
                            }
                        }
                        is States.Failed -> {
                            Help.showToast(this@LoginActivity,state.message)
                            isShowProgressBar(false)
                        }
                    }
                }
            }
        }
    }

    private fun isShowProgressBar(isShow: Boolean)
    {
        loginBinding.apply {
            progressBarLogin.visibility = if (isShow) View.VISIBLE else View.GONE
            loginButton.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }
}