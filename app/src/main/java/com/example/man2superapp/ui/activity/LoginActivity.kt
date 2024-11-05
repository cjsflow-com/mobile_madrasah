package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity,MainActivity::class.java)).also { finish() }
        }
    }
}