@file:Suppress("DEPRECATION")

package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityCreateSongketBinding
import com.example.man2superapp.source.local.model.ListSongketMother
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditSongketActivity : AppCompatActivity()
{
    private lateinit var editBinding: ActivityCreateSongketBinding
    private val alLViewModel by viewModels<AllViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBinding = ActivityCreateSongketBinding.inflate(layoutInflater)
        setContentView(editBinding.root)
        onBackPressedDispatcher.addCallback(this@EditSongketActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@EditSongketActivity)
            }
        })
        val typeLetter = intent.getIntExtra(Constant.LETTER_TYPE,-1)
        val type = intent.getIntExtra(Constant.TYPE,-1)
        base(letterType = typeLetter, type = type)
    }

    private fun base(letterType: Int,type: Int)
    {
        editBinding.apply {
            btnCreate.text = "Update Songket Emak"
            backButton.setOnClickListener {
                backToSongket()
            }
            btnCreate.setOnClickListener {
                validationAnActionUpdate(letterType,type)
            }
            val listSongketMother = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                intent.getParcelableExtra(Constant.LIST_SONGKET_MOTHER,ListSongketMother::class.java)
            }else{
                intent.getParcelableExtra<ListSongketMother>(Constant.LIST_SONGKET_MOTHER)
            }

            listSongketMother?.let { data ->
                when(data.letterStatement){
                    3 -> {
                        tvMan.text = "Surat Keterangan Ekskul/Club"
                        parentExtracurricular.visibility = View.VISIBLE
                        parentNameClub.visibility = View.VISIBLE
                        tvClub.visibility = View.VISIBLE
                        tvEskul.visibility = View.VISIBLE
                        etNameExtracurricular.setText(data.nameExtracurricular)
                        etNameClub.setText(data.nameClub)
                    }
                    5 -> {
                        tvMan.text = "Surat Keterangan Peringkat"
                        tvSemester.visibility = View.VISIBLE
                        tvRanking.visibility = View.VISIBLE
                        tvAverageValue.visibility = View.VISIBLE
                        tvTotalStudents.visibility = View.VISIBLE
                        parentSemester.visibility = View.VISIBLE
                        parentAverageValue.visibility = View.VISIBLE
                        parentRanking.visibility = View.VISIBLE
                        parentTotalStudents.visibility = View.VISIBLE

                        etSemester.setText(data.semester)
                        etAverageValue.setText(data.averageValue.toString())
                        etRanking.setText(data.ranking)
                        etTotalStudents.setText(data.totalStudent)
                    }
                    6 -> {
                        tvMan.text = "Surat Rekomendasi Universitas"
                        tvNameUnversity.visibility = View.VISIBLE
                        tvMajor.visibility = View.VISIBLE
                        parentMajor.visibility = View.VISIBLE
                        parentNameUniversity.visibility = View.VISIBLE

                        etNameUniversity.setText(data.nameUniversity)
                        etMajor.setText(data.major)
                    }
                }
            }
        }
    }

    private fun validationAnActionUpdate(letterType: Int,id: Int)
    {
        editBinding.apply {
            val extracurricular = parentExtracurricular.editText?.text.toString()
            val parentClub = parentNameClub.editText?.text.toString()
            val nameUniversity = parentNameUniversity.editText?.text.toString()
            val major = parentMajor.editText?.text.toString()
            val semester = parentSemester.editText?.text.toString()
            val ranking = parentRanking.editText?.text.toString()
            val totalStudent = parentTotalStudents.editText?.text.toString()
            val averageValue = parentAverageValue.editText?.text.toString()

            when(letterType){
                3 -> {
                    if(extracurricular.isBlank() || parentClub.isBlank()){
                        alLViewModel.updateSongketMother(id,"","",extracurricular,parentClub,
                            "","","","","",0.0)
                            .observe(this@EditSongketActivity){handleResponse(it)}
                    }else{
                        Help.showToast(this@EditSongketActivity,getString(R.string.validation_letter_club))
                    }
                }
                5 -> {
                    if(semester.isBlank() || ranking.isBlank() || totalStudent.isBlank() || averageValue.isBlank())
                    {
                        Help.showToast(this@EditSongketActivity,getString(R.string.validation_form))
                    }else{
                        alLViewModel.updateSongketMother(
                            id,"","","","","",
                            "",ranking,semester,totalStudent,averageValue.toDouble()
                        ).observe(this@EditSongketActivity){handleResponse(it)}
                    }
                }
                6 ->{
                    if (nameUniversity.isBlank() || major.isBlank())
                    {
                        Help.showToast(this@EditSongketActivity,getString(R.string.validation_form))
                    }else{
                        alLViewModel.updateSongketMother(id,"","","","",nameUniversity,
                            major,"","","",0.0).observe(
                                this@EditSongketActivity
                            ){handleResponse(it)}
                    }
                }
            }

        }
    }

    private fun handleResponse(state: States<UpdateSongketMother>)
    {
        when(state){
            is States.Loading -> {

            }
            is States.Success -> {
                Log.d(Constant.TAG_UPDATE_SONGKET_MOTHER, "handleResponse: ${state.data.message}")
                Help.showToast(this@EditSongketActivity,state.data.message)
                backToSongket()
            }
            is States.Failed -> {
                Log.d(Constant.TAG_UPDATE_SONGKET_MOTHER ,"handleResponse: ${state.message}")
                backToSongket()
            }
        }
    }

    private fun backToSongket()
    {
        startActivity(Intent(this@EditSongketActivity,SongketActivity::class.java))
        finish()
    }

}
