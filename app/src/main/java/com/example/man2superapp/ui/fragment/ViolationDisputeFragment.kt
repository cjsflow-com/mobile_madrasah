package com.example.man2superapp.ui.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.man2superapp.R
import com.example.man2superapp.databinding.AddReasonDisputeViolationBinding
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViolationDisputeFragment(
    private val context: Context,
    private val id: Int,
    private val token: String?,
    private val onSubmit:(Int, String, String) -> Unit,
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
            binding.apply {

                btnVioaltionDispute.setOnClickListener {
                    val inputReason = parentNoteViolationDispute.editText?.text.toString().trim()
                    if (inputReason.isEmpty()) {
                        Help.showToast(context,"Alasan tidak boleh kosong")
                    } else {
                        token?.let { it1 -> onSubmit(id, it1,inputReason) }
                        requireActivity().finish()
                        dismiss()
                    }
                }
                btnBack.setOnClickListener { dismiss() }
            }
        }
        return dialog
    }
}