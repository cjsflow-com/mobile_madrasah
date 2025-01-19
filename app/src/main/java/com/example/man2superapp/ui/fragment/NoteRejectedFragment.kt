package com.example.man2superapp.ui.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.man2superapp.R
import com.example.man2superapp.databinding.AddReasonDisputeViolationBinding

class NoteRejectedFragment(
    private val note: String,
    private val context: Context
): DialogFragment()
{
    private var _binding: AddReasonDisputeViolationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = AddReasonDisputeViolationBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.apply {
            attributes.windowAnimations = R.style.DialogAnimation
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.apply {
                etNoteViolationDispute.setText(note)
                btnBack.setOnClickListener {
                    dismiss()
                }
                parentNoteViolationDispute.isEnabled = false
                btnVioaltionDispute.visibility = View.GONE
            }
        }
        return dialog
    }
}