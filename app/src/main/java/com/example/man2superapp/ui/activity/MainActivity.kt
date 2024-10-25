package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        cardAction()
    }

    private fun cardAction()
    {
        mainBinding.wbsCard.setOnClickListener {
            showMaterialDialog()
        }
    }

    private  fun showMaterialDialog()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle("Informasi")
            .setMessage("Anda ingin login terlebih dahulu atau mengisi form langsung ?")
            .setPositiveButton("Ya, saya ingin login dulu"){dialog,_ ->
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                dialog.dismiss()
            }
            .setNegativeButton("Saya langsung mengisi form saja"){dialog,_ ->
                startActivity(Intent(this@MainActivity,WBSActivity::class.java))
                dialog.dismiss()
            }
            .show()
    }
}