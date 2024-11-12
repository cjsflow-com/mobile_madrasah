package com.example.man2superapp

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*

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
            // Tambahkan item_time_input.xml baru di timeContainerMonday
            val timeItemView = layoutInflater.inflate(R.layout.item_time_input, timeContainerMonday, false)
            timeContainerMonday.addView(timeItemView)

            // Setting untuk waktu mulai dan waktu selesai
            val startTime: TextView = timeItemView.findViewById(R.id.startTime)
            val endTime: TextView = timeItemView.findViewById(R.id.endTime)

            // Buka TimePicker untuk startTime
            startTime.setOnClickListener {
                showTimePicker { time -> startTime.text = time }
            }

            // Buka TimePicker untuk endTime dengan pembatasan maksimal 15:40
            endTime.setOnClickListener {
                showTimePicker { time ->
                    if (time <= "15:40") {
                        endTime.text = time
                    } else {
                        endTime.error = "Batas waktu hanya sampai 15:40"
                    }
                }
            }
        }

        return view
    }

    // Fungsi untuk membuka TimePicker dan mengatur waktu
    private fun showTimePicker(onTimeSet: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSet(formattedTime)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}