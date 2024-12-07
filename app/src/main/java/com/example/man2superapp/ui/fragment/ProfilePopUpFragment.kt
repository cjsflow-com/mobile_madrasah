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
import com.example.man2superapp.databinding.ProfilePopupBinding

class ProfilePopUpFragment(
    private val role: String?,
    private val name: String?,
    private val email: String?,
    private val nisn: String?,
    private val className: String?,
    private val gender: Int?,
    private val context: Context
) : DialogFragment()
{
    private var _binding: ProfilePopupBinding? = null
    private val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = ProfilePopupBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        dialog?.window?.apply {
            attributes.windowAnimations = R.style.DialogAnimation
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.apply {
                tvName.text = "Nama: $name"
                binding.tvEmail.text = "Email: $email"
                if(gender == 1){
                    tvGender.text = "Jenis Kelamin: Laki-Laki"
                }else if(gender == 2){
                    tvGender.text = "Jenis Kelamin: Perempuan"
                }
                tvRole.text = "Role: $role"

                if(role == "siswa"){
                    tvNisn.visibility = View.VISIBLE
                    tvClassName.visibility = View.VISIBLE
                    tvNisn.text = "NISN: $nisn"
                    tvClassName.text = "Kelas: $className"
                }else{
                    tvNisn.visibility = View.GONE
                    tvClassName.visibility = View.GONE
                }
            }
        }
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
