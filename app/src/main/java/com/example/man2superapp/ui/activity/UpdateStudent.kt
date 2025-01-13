package com.example.man2superapp.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.UpdatePasswordBottomSheetBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class UpdateStudent : AppCompatActivity()
{

    private lateinit var updateBinding: UpdatePasswordBottomSheetBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private var classesMap: Map<String,Int>? = null
    private var selectGenderValue: Int = -1
    private val genderOptions = listOf("Laki-Laki","Perempuan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = UpdatePasswordBottomSheetBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val role = intent.getStringExtra(Constant.TYPE_ROLE)
        val type = intent.getIntExtra(Constant.TYPE,-1)
        val name = intent.getStringExtra(Constant.NAME)
        val email = intent.getStringExtra(Constant.EMAIL)
        val position = intent.getStringExtra(Constant.POSITION)
        val nisn = intent.getStringExtra(Constant.NISN)
        val gender = intent.getIntExtra(Constant.GENDER,0)
        val className = intent.getIntExtra(Constant.CLASS,-1)
        val numberHandphone = intent.getStringExtra(Constant.PHONE)
        val placeBirthday = intent.getStringExtra(Constant.PLACE_BIRTHDAY)
        val nameFather = intent.getStringExtra(Constant.NAME_FATHER)
        val nameMother = intent.getStringExtra(Constant.NAME_MOTHER)
        val address = intent.getStringExtra(Constant.ADDRESS)
        val dateBirthday = intent.getStringExtra(Constant.DATE_BIRTHDAY)
        val phoneNumberParent = intent.getStringExtra(Constant.PHONE_PARENT)

        allViewModel.fetchAllClassStudent(this@UpdateStudent)

        onBackPressedDispatcher.addCallback(this@UpdateStudent,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@UpdateStudent)
            }
        })

        setAdapterGender()

        setUpDefaultGender(gender)

        if(type == 1)
        {
            showUpdatePassword(true)
        }else{
            role?.let { checkRoleForView(it) }
            showUpdatePassword(false)
        }

        updateBinding.etBirthday.setOnClickListener {
            showDatePickerDialog(updateBinding.etBirthday)
        }

        allViewModel.classList.observe(this@UpdateStudent){ classesList ->
            val classMap = classesList.associateBy({it.name_class},{it.id})
            Log.d("Update Student", "onCreate: ${classesList.map { it.name_class }}")
            val adapter = ArrayAdapter(
                this@UpdateStudent,
                android.R.layout.simple_dropdown_item_1line,
                classesList.map { it.name_class })

            val matchingClass = classesList.find { it.id == className}
            matchingClass.let {
                updateBinding.etClasses.setText(it?.name_class)
            }
            this@UpdateStudent.classesMap = classMap
            updateBinding.etClasses.setAdapter(adapter)
        }

        updateBinding.apply {
            etEmail.setText(email)
            etName.setText(name)
            etPosition.setText(position)
            etNisn.setText(nisn)
            etNameMother.setText(nameMother)
            etNameFather.setText(nameFather)
            etAddress.setText(address)
            etPlaceBirthday.setText(placeBirthday)
            etBirthday.setText(dateBirthday)
            etNumberHandphone.setText(numberHandphone)
            etNumberParent.setText(phoneNumberParent)
            tvBack.setOnClickListener {
                startActivity(Intent(this@UpdateStudent,MainActivity::class.java))
                finish()
            }
            lifecycleScope.launch {
                localStore.getToken().collect{ data ->
                    data.token?.let { tokens ->
                        role?.let { roles ->
                            btnSavePassword.setOnClickListener {
                                action(roles,tokens)
                            }
                            btnChangeProfile.setOnClickListener {
                                actionUpdateProfile(roles,tokens)
                            }
                        }
                    }
                }
            }

            progressBar.visibility = View.GONE
            btnSavePassword.visibility = View.VISIBLE
        }
    }

    private fun showUpdatePassword(isShow: Boolean){
        updateBinding.apply {
            scrollViewUpdateProfile.visibility = if(isShow) View.GONE else View.VISIBLE
            linearUpdatePassword.visibility = if(isShow) View.VISIBLE else View.GONE
        }
    }

    private fun actionUpdateProfile(role: String,token: String)
    {
        updateBinding.apply {
            val name = parentName.editText?.text.toString().trim()
            val email = parentEmail.editText?.text.toString().trim()
            val placeBirthDay = parentPlaceBirthday.editText?.text.toString().trim()
            val position = parentPosition.editText?.text.toString().trim()
            val address = parentAddress.editText?.text.toString().trim()
            val phoneNumber = parentNumberHandphone.editText?.text.toString().trim()
            val nameFather = parentNameFather.editText?.text.toString().trim()
            val nameMother = parentNameMother.editText?.text.toString().trim()
            val nisn = parentNisn.editText?.text.toString().trim()
            val dateBirthday = parentDateBirthday.editText?.text.toString().trim()
            val classes = parentClasses.editText?.text.toString().trim()
            val phoneNumberParent = parentNumberHandphoneParent.editText?.text.toString().trim()
            if(role == "siswa")
            {
                val takeClass = classesMap?.get(classes)
                 updateProfileStudent(
                    token,name,email,phoneNumber,selectGenderValue,address,nisn,takeClass,
                    placeBirthDay,dateBirthday,nameFather,nameMother,phoneNumberParent
                )
            }else{
                updateProfileEmployee(token,name,email,phoneNumber,selectGenderValue,position)
            }
        }
    }

    private fun action(role: String,token: String)
    {
            val textPassword = updateBinding.tilPassword.editText?.text.toString().trim()
            if (role == "siswa")
            {
                if(textPassword.isEmpty())
                {
                    Help.showToast(this@UpdateStudent,"Password tidak boleh kosong")
                }else{
                   actionUpdatePasswordStudent(token,textPassword)
                }
            }else{
                if(textPassword.isEmpty()){
                    Help.showToast(this@UpdateStudent,"Password tidak boleh kosong")
                }else{
                  actionUpdatePasswordEmployee(token,textPassword)
                }
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
            val formattedNewDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
//            val formattedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
            etTanggalKejadian.setText(formattedNewDate)
        }, year, month, day)

        // Menampilkan dialog
        datePickerDialog.show()
    }

    private fun setAdapterGender()
    {
        val adapter = ArrayAdapter(
            this@UpdateStudent,
            android.R.layout.simple_dropdown_item_1line,
            genderOptions,
        )
        updateBinding.apply {
            etGender.setAdapter(adapter)
            etGender.setOnItemClickListener{_,_,position,_ ->
                selectGenderValue = when(genderOptions[position]){
                    "Laki-Laki" -> 1
                    "Perempuan" -> 2
                    else -> -1
                }
            }
        }
    }

    private fun setUpDefaultGender(gender: Int){
        updateBinding.etGender.setText(
            when(gender){
                1 -> "Laki-Laki"
                2 -> "Perempuan"
                else -> ""
            },false
        )
        selectGenderValue = gender
    }

    private fun updateProfileEmployee(token: String,name: String,email: String, number_phone: String,
                                      gender: Int, position: String)
    {
        updateBinding.apply {
            allViewModel.updateProfileEmployee(token,name,email,number_phone,gender,position).observe(this@UpdateStudent){ state ->
                when(state)
                {
                    is States.Loading -> {
                        isShowProgress(true)
                    }
                    is States.Success -> {
                        isShowProgress(false)
                        Help.showToast(this@UpdateStudent,state.data)
                        actionBackToHome()
                    }
                    is States.Failed -> {
                        isShowProgress(false)
                        Help.showToast(this@UpdateStudent,state.message)
                        actionBackToHome()
                    }
                }
            }
        }
    }

    private fun checkRoleForView(role: String)
    {
        updateBinding.apply {
            if(role == "siswa")
            {
                parentPosition.visibility = View.GONE
            }else{
                parentNisn.visibility = View.GONE
                parentClasses.visibility = View.GONE
                parentPlaceBirthday.visibility = View.GONE
                parentDateBirthday.visibility = View.GONE
                parentNameFather.visibility = View.GONE
                parentNameMother.visibility = View.GONE
                parentAddress.visibility = View.GONE
                parentDateBirthday.visibility = View.GONE
            }
        }

    }

    private fun updateProfileStudent(token: String,name: String,email: String,number_phone: String,gender: Int,
                                     address: String,nisn: String,classes: Int?,place_birthday: String,
                                     date_birthday: String,name_father: String,name_mother: String,phoneParent: String)
    {
        classes?.let {
            allViewModel.updateProfileStudent(token,name,email,place_birthday,nisn, number_phone,
                it,gender,
                name_father,name_mother,address,date_birthday,phoneParent).observe(this@UpdateStudent){ state ->
                when(state) {
                    is States.Loading -> {
                        isShowProgress(true)
                    }
                    is States.Success -> {
                        Help.showToast(this@UpdateStudent,state.data)
                        actionBackToHome()
                        isShowProgress(false)
                    }

                    is States.Failed -> {
                        Help.showToast(this@UpdateStudent,state.message)
                        actionBackToHome()
                        isShowProgress(false)
                    }
                }
            }
        }
    }

    private fun actionUpdatePasswordStudent(token: String,password: String)
    {
        allViewModel.updatePasswordStudent(token,password).observe(this@UpdateStudent){ state ->
            when(state)
            {
                is States.Loading -> {
                    isShowProgress(true)
                }
                is States.Success -> {
                    Help.showToast(this@UpdateStudent,state.data.message)
                    actionBackToHome()
                    isShowProgress(false)
                }
                is States.Failed -> {
                    Help.showToast(this@UpdateStudent,state.message)
                    actionBackToHome()
                    isShowProgress(false)
                }
            }
        }
    }

    private fun actionUpdatePasswordEmployee(token: String,password: String)
    {
        allViewModel.updatePasswordEmployee(token,password).observe(this@UpdateStudent){ state ->
            when(state){
                is States.Loading -> {
                    isShowProgress(true)
                }
                is States.Success -> {
                    isShowProgress(false)
                    Help.showToast(this@UpdateStudent,state.data)
                    actionBackToHome()
                }
                is States.Failed -> {
                    isShowProgress(false)
                    Help.showToast(this@UpdateStudent,state.message)
                    actionBackToHome()
                }
            }
        }
    }

    private fun actionBackToHome()
    {
        startActivity(Intent(this@UpdateStudent,MainActivity::class.java))
        finish()
    }

    private fun isShowProgress(isShow: Boolean)
    {
        updateBinding.apply {
            progressBarUpdate.visibility = if(isShow) View.VISIBLE else View.GONE
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnChangeProfile.visibility = if(isShow) View.GONE else View.VISIBLE
            btnSavePassword.visibility = if(isShow) View.GONE else View.VISIBLE
        }
    }


}
