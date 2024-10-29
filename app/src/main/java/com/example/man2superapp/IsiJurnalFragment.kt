package com.example.man2superapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class IsiJurnalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_isi_jurnal, container, false)

        // Contoh untuk menambah waktu dihari senin
        val timeContainerMonday: LinearLayout = view.findViewById(R.id.timeContainerMonday)
        val addTimeMonday: TextView = view.findViewById(R.id.addTimeMonday)

        addTimeMonday.setOnClickListener {
            // Tambahkan item_time_input.xml baru di bawahnya
            val timeItemView = layoutInflater.inflate(R.layout.item_time_input, timeContainerMonday, false)
            timeContainerMonday.addView(timeItemView)

            // Dapatkan referensi akhir pada input baru
            val endTime: EditText = timeItemView.findViewById(R.id.endTime)
            endTime.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && endTime.text.toString() > "15.40") {
                    endTime.error = "Batas waktu hanya sampai 15.40"
                    endTime.setText("")
                }
            }
        }

        return view
    }
}