package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivitySplashBinding
import com.example.man2superapp.ui.adapter.SplashPagerAdapter

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titles = listOf(
            "Mudah memonitoring pekerjaan",
            "Urusan administrasi jadi lebih simpel",
            "Memantau kedisiplinan siswa",
            "Pelaporan pelanggaran aman dan transparan"
        )

        val descriptions = listOf(
            "Fitur E-Kinerja memudahkan GTK dalam melaporkan progres kerja.",
            "Songket Emak mempersingkat urusan surat menyurat siswa & GTK.",
            "Kedisiplinan siswa terpantau secara sistematis melalui fitur E-Tatib.",
            "WBS memungkinkan pengguna melaporkan tindakan pelanggaran."
        )

        val images = listOf(
            R.drawable.medium_ss1,
            R.drawable.medium_ss2,
            R.drawable.medium_ss3,
            R.drawable.medium_ss4
        )

        val splashPagerAdapter = SplashPagerAdapter(this, images, titles, descriptions)
        binding.viewPager.adapter = splashPagerAdapter

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < images.size - 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}