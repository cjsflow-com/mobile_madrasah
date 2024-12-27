package com.example.man2superapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.man2superapp.databinding.ActivityWebViewBinding

class WebViewActivity: AppCompatActivity()
{

    private lateinit var webBinding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(webBinding.root)
        webBinding.webView.apply {
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            val url = intent.getStringExtra("URL")
            url?.let { loadUrl(it) }
        }
    }
}
