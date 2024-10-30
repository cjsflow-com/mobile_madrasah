package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityMainBinding
import com.example.man2superapp.wbs
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(mainBinding.root)
//    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        cardAction()
        // Set OnClickListener pada card

    }

    private fun cardAction()
    {
        mainBinding.apply {
            ekinCard.setOnClickListener {
                val intent = Intent(this@MainActivity, EkinActivity::class.java)
                startActivity(intent)
            }

            jurnalCard.setOnClickListener {
                val intent = Intent(this@MainActivity, JurnalActivity::class.java)
                startActivity(intent)
            }

            songketCard.setOnClickListener {
                val intent = Intent(this@MainActivity, SongketActivity::class.java)
                startActivity(intent)
            }

            wbsCard.setOnClickListener {
                showMaterialDialog()
            }
        }
    }

    private fun showMaterialDialog()
    {
        MaterialAlertDialogBuilder(this)
            .setTitle("Informasi")
            .setMessage("Anda ingin login terlebih dahulu atau mengisi form langsung ?")
            .setPositiveButton("Ya, saya ingin login dulu"){dialog,_ ->
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                dialog.dismiss()
            }
            .setNegativeButton("Saya langsung mengisi form saja"){dialog, _ ->
                startActivity(Intent(this@MainActivity,wbs::class.java))
                dialog.dismiss()
            }
            .show()
    }

}