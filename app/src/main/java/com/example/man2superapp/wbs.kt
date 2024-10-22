package com.example.man2superapp

import android.app.DatePickerDialog
import java.util.*
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class wbs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wbs)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengakses field TextInputEditText untuk tanggal
        val etTanggalKejadian = findViewById<TextInputEditText>(R.id.etTanggalKejadian)

        // Set listener untuk memunculkan DatePickerDialog ketika field tanggal diklik
        etTanggalKejadian.setOnClickListener {
            showDatePickerDialog(etTanggalKejadian)
        }
    }

    private fun showDatePickerDialog(etTanggalKejadian: TextInputEditText) {
        // Mendapatkan tanggal saat ini
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Membuat DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Mengatur tanggal yang dipilih pada TextInputEditText
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            etTanggalKejadian.setText(formattedDate)
        }, year, month, day)

        // Menampilkan dialog
        datePickerDialog.show()
    }
}