package com.example.man2superapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi CardView
        val ekinCard: CardView = findViewById(R.id.ekinCard)
        val jurnalCard: CardView = findViewById(R.id.jurnalCard)
        val songketCard: CardView = findViewById(R.id.songketCard)
        val etatibCard: CardView = findViewById(R.id.etatibCard)
        val wbsCard: CardView = findViewById(R.id.wbsCard)
        val pelajarCard: CardView = findViewById(R.id.pelajarCard)

        // Set OnClickListener pada card
        ekinCard.setOnClickListener {
            val intent = Intent(this, EkinActivity::class.java)
            startActivity(intent)
        }

        songketCard.setOnClickListener {
            val intent = Intent(this, SongketActivity::class.java)
            startActivity(intent)
        }

        wbsCard.setOnClickListener {
            val intent = Intent(this, wbs::class.java)
            startActivity(intent)
        }
    }
}