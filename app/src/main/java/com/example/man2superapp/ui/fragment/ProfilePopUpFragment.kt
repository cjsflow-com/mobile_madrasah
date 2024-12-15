package com.example.man2superapp.ui.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ProfilePopupBinding
import com.example.man2superapp.ui.activity.UpdateStudent
import com.example.man2superapp.utils.Constant

class ProfilePopUpFragment(
    private val role: String?,
    private val name: String?,
    private val email: String?,
    private val nisn: String?,
    private val className: String?,
    private val numberHandphone: String?,
    private val nameMother: String?,
    private val nameFather: String?,
    private val address: String?,
    private val position: String?,
    private val dateBirthday: String?,
    private val placeBirthday: String?,
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
                tvEmail.text = "Email: $email"
                tvNumberHandphone.text = "Nomor Handphone: $numberHandphone"
                if(gender == 1){
                    tvGender.text = "Jenis Kelamin: Laki-Laki"
                }else if(gender == 2){
                    tvGender.text = "Jenis Kelamin: Perempuan"
                }
                tvRole.text = "Role: $role"

                if(role == "siswa"){
                    tvNisn.visibility = View.VISIBLE
                    tvClassName.visibility = View.VISIBLE
                    tvNameFather.visibility = View.VISIBLE
                    tvNameMother.visibility = View.VISIBLE
                    tvDateBirthday.visibility = View.VISIBLE
                    tvPlaceBirthday.visibility = View.VISIBLE
                    tvAddress.visibility = View.VISIBLE
                    tvPosition.visibility = View.GONE
                    tvNameFather.text = "Nama Ayah $nameFather"
                    tvNameMother.text = "Nama Ibu $nameMother"
                    tvDateBirthday.text = "Tanggal lahir: $dateBirthday"
                    tvPlaceBirthday.text = "Tempat Lahir: $placeBirthday"
                    tvAddress.text = "Alamat: $address"
                    tvNisn.text = "NISN: $nisn"
                    tvClassName.text = "Kelas: $className"
                }else{
                    tvNisn.visibility = View.GONE
                    tvClassName.visibility = View.GONE
                    tvNameFather.visibility = View.GONE
                    tvNameMother.visibility = View.GONE
                    tvDateBirthday.visibility = View.GONE
                    tvPlaceBirthday.visibility = View.GONE
                    tvAddress.visibility = View.GONE
                    tvPosition.visibility = View.VISIBLE
                    tvPosition.text = "Jabatan: $position"
                }

                btnUpdatePassword.setOnClickListener {
                    Intent(context,UpdateStudent::class.java).apply {
                        putExtra(Constant.TYPE,1)
                        putExtra(Constant.TYPE_ROLE,role)
                    }.also { startActivity(it) }.also { requireActivity().finish() }
                }

                btnUpdateProfile.setOnClickListener {
                    Intent(context,UpdateStudent::class.java).apply {
                        putExtra(Constant.TYPE,2)
                        putExtra(Constant.TYPE_ROLE,role)
                        putExtra(Constant.NAME,name)
                        putExtra(Constant.EMAIL,email)
                        putExtra(Constant.NISN,nisn)
                        putExtra(Constant.CLASS,className)
                        putExtra(Constant.GENDER,gender)
                        putExtra(Constant.PHONE,numberHandphone)
                        putExtra(Constant.NAME_FATHER,nameFather)
                        putExtra(Constant.NAME_MOTHER,nameMother)
                        putExtra(Constant.ADDRESS,address)
                        putExtra(Constant.DATE_BIRTHDAY,dateBirthday)
                        putExtra(Constant.PLACE_BIRTHDAY,placeBirthday)
                        putExtra(Constant.POSITION,position)
                    }.also { startActivity(it) }.also { requireActivity().finish() }
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
