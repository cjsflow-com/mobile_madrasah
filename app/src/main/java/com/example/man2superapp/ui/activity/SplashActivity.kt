package com.example.man2superapp.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivitySplashBinding
import com.example.man2superapp.ui.adapter.SplashPagerAdapter

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        // Periksa apakah aplikasi sudah pernah dibuka
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (!isFirstRun) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

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

        // Set initial dot indicator state
        updateDotIndicator(0)

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < images.size - 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        // menangkap perubahan halaman untuk memperbarui indikator titik
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                updateDotIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun updateDotIndicator(position: Int) {
        val dots = listOf(
            binding.dotIndicator.getChildAt(0),
            binding.dotIndicator.getChildAt(1),
            binding.dotIndicator.getChildAt(2),
            binding.dotIndicator.getChildAt(3)
        )

        dots.forEachIndexed { index, view ->
            val drawableRes = if (index == position) R.drawable.dotactive else R.drawable.dotinactive
            view.setBackgroundResource(drawableRes)
        }
    }
}