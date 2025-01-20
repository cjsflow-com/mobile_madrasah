package com.example.man2superapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.man2superapp.databinding.DialogChooseEtatibBinding
import com.example.man2superapp.ui.activity.AttendanceStudent
import com.example.man2superapp.ui.activity.TatibActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseViewFragment : BottomSheetDialogFragment()
{
    private var _binding: DialogChooseEtatibBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogChooseEtatibBinding.inflate(inflater,container,false)

        binding.apply {
            cardTatib.setOnClickListener {
                Intent(requireActivity(),TatibActivity::class.java).also { startActivity(it) }
            }
            cardAttendanceStudent.setOnClickListener {
                Intent(requireActivity(),AttendanceStudent::class.java).also { startActivity(it) }
            }
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}