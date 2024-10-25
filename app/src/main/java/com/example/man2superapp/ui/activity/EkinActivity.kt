package com.example.man2superapp.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityEkinBinding

class EkinActivity : AppCompatActivity() {
    private lateinit var eKinBinding: ActivityEkinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekin)
    }
}