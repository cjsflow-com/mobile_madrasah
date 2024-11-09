package com.example.man2superapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.man2superapp.databinding.ActivityLoginStudentBinding

class LoginStudent: AppCompatActivity()
{

    private lateinit var loginStudentBinding: ActivityLoginStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStudentBinding = ActivityLoginStudentBinding.inflate(layoutInflater)
        setContentView(loginStudentBinding.root)
    }
}