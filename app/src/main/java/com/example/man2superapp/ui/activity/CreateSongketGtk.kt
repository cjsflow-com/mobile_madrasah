package com.example.man2superapp.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityCreateSongketGtkBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class CreateSongketGtk: AppCompatActivity()
{

    private lateinit var createSongketGtkBinding: ActivityCreateSongketGtkBinding
    private var letterType: Int = -1

    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSongketGtkBinding = ActivityCreateSongketGtkBinding.inflate(layoutInflater)
        setContentView(createSongketGtkBinding.root)
        val letterStatement = intent.getStringExtra(Constant.LETTER_STATEMENT)
        letterType = intent.getIntExtra(Constant.LETTER_TYPE,-1)
        onBackPressedDispatcher.addCallback(this@CreateSongketGtk,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@CreateSongketGtk)
            }
        })

        createSongketGtkBinding.apply {
            etHoliday.setOnClickListener { showDatePickerDialog(etHoliday) }
            etEndHoliday.setOnClickListener { showDatePickerDialog(etEndHoliday) }
            tvSubtitle.text = letterStatement
            backButton.setOnClickListener {
                startActivity(Intent(this@CreateSongketGtk,SongketActivity::class.java))
                    .also { finish() }
            }
            when(letterType)
            {
                1 -> {
                    tvRankOrGrade.visibility = View.VISIBLE
                    parentRankOrGrade.visibility = View.VISIBLE
                    tvNipEmployee.visibility = View.VISIBLE
                    parentNip.visibility = View.VISIBLE
                    tvStartHoliday.visibility = View.VISIBLE
                    parentStartHoliday.visibility = View.VISIBLE
                    tvEndHoliday.visibility = View.VISIBLE
                    parentEndHoliday.visibility = View.VISIBLE
                }
                2 -> {
                    tvRankOrGrade.visibility = View.VISIBLE
                    parentRankOrGrade.visibility = View.VISIBLE
                    tvNipEmployee.visibility = View.VISIBLE
                    parentNip.visibility = View.VISIBLE
                    tvNuptk.visibility = View.VISIBLE
                    parentNuptk.visibility = View.VISIBLE
                    tvFieldStudy.visibility = View.VISIBLE
                    parentFieldStudy.visibility = View.VISIBLE
                }
                3 -> {
                    tvRankOrGrade.visibility = View.VISIBLE
                    parentRankOrGrade.visibility = View.VISIBLE
                    tvNipEmployee.visibility = View.VISIBLE
                    parentNip.visibility = View.VISIBLE
                    tvFieldStudy.visibility = View.VISIBLE
                    parentFieldStudy.visibility = View.VISIBLE
                    tvFieldStudy.text = "Mata Pelajaran"
                    parentFieldStudy.hint = "Mata Pelajaran"
                    tvHaveStudy.visibility = View.VISIBLE
                    parentHaveStudy.visibility = View.VISIBLE
                    tvTitleRecomendation.visibility = View.VISIBLE
                    parentTitleRecomendation.visibility = View.VISIBLE
                }
            }
            btnCreate.setOnClickListener {
                lifecycleScope.launch {
                    localStore.getToken().collect{ data ->
                        data.token?.let { action(it,letterType) }
                    }
                }
            }
        }
    }

    private fun handleResponse(state: States<CreateSongketMother>)
    {
        when(state)
        {
            is States.Loading -> {}
            is States.Success -> {
                Help.showToast(this@CreateSongketGtk,state.data.message)
                    .also {
                        startActivity(Intent(this@CreateSongketGtk,MainActivity::class.java))
                        finish()
                    }
            }
            is States.Failed -> {
                Help.showToast(this@CreateSongketGtk,state.message)
            }
        }
    }

    private fun action(token: String, letterType: Int)
    {
        createSongketGtkBinding.apply {
            val ranKOrGrade = parentRankOrGrade.editText?.text.toString()
            val nip = parentNip.editText?.text.toString()
            val startHoliday = parentStartHoliday.editText?.text.toString()
            val endHoliday = parentEndHoliday.editText?.text.toString()
            val fieldStudy = parentFieldStudy.editText?.text.toString()
            val nuptk = parentNuptk.editText?.text.toString()
            val haveStudy = parentHaveStudy.editText?.text.toString()
            val titleRecomendation = parentTitleRecomendation.editText?.text.toString()

            when(letterType)
            {
                1 -> {
                    if(startHoliday.isBlank() && endHoliday.isBlank())
                    {
                        Help.showToast(this@CreateSongketGtk,"Mulai Cuti dan Akhir Cuti tidak boleh kosong")
                    }else{
                        allViewModel.createSongketMotherGtk(token,letterType,ranKOrGrade,nip,"","","",startHoliday,endHoliday,"")
                            .observe(this@CreateSongketGtk){state -> handleResponse(state)}
                    }
                }
                2 -> {
                    allViewModel.createSongketMotherGtk(
                        token,letterType,ranKOrGrade,nip,nuptk,fieldStudy,"","","",""
                    ).observe(this@CreateSongketGtk){state -> handleResponse(state)}
                }
                3 -> {
                    allViewModel.createSongketMotherGtk(
                        token,letterType,ranKOrGrade,nip,"",fieldStudy,haveStudy,"","",titleRecomendation
                    ).observe(this@CreateSongketGtk){state -> handleResponse(state)}
                }
            }
        }
    }

    private fun showDatePickerDialog(textInput: TextInputEditText){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Membuat DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Mengatur tanggal yang dipilih pada TextInputEditText
            val formattedNewDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
//            val formattedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
            textInput.setText(formattedNewDate)
        }, year, month, day)
        datePickerDialog.show()
    }
}