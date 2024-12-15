package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityCreateSongketBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateSongket : AppCompatActivity()
{

    private lateinit var createSongketBinding: ActivityCreateSongketBinding
    private var letterType: Int = -1

    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSongketBinding = ActivityCreateSongketBinding.inflate(layoutInflater)
        setContentView(createSongketBinding.root)
        val letterStatement = intent.getStringExtra(Constant.LETTER_STATEMENT)
        letterType = intent.getIntExtra(Constant.LETTER_TYPE,-1)
        onBackPressedDispatcher.addCallback(this@CreateSongket,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@CreateSongket)
            }

        })

        createSongketBinding.apply {
            tvMan.text = letterStatement

            backButton.setOnClickListener {
                startActivity(Intent(this@CreateSongket,SongketActivity::class.java))
                    .also { finish() }
            }

                when(letterType){
                    3 -> {
                        tvEskul.visibility = View.VISIBLE
                        parentNameClub.visibility = View.VISIBLE
                        tvClub.visibility = View.VISIBLE
                        parentExtracurricular.visibility = View.VISIBLE
                    }
                    5 -> {
                        tvSemester.visibility = View.VISIBLE
                        tvRanking.visibility = View.VISIBLE
                        tvAverageValue.visibility = View.VISIBLE
                        tvTotalStudents.visibility = View.VISIBLE
                        parentSemester.visibility = View.VISIBLE
                        parentAverageValue.visibility = View.VISIBLE
                        parentRanking.visibility = View.VISIBLE
                        parentTotalStudents.visibility = View.VISIBLE
                    }
                    6 -> {
                        tvNameUnversity.visibility = View.VISIBLE
                        tvMajor.visibility = View.VISIBLE
                        parentMajor.visibility = View.VISIBLE
                        parentNameUniversity.visibility = View.VISIBLE
                    }
            }

            btnCreate.setOnClickListener {
                lifecycleScope.launch {
                    localStore.getToken().collect{data ->
                        data.id?.let { action(it,letterType) }
                    }
                }
            }
        }
    }

    private fun handleResponse(state: States<CreateSongketMother>){
        when(state){
            is States.Loading -> {

            }
            is States.Success -> {
                Log.d(Constant.TAG_CREATE_SONGKET, "handleResponse: ${state.data.message}")
                Help.showToast(this@CreateSongket,state.data.message)
                    .also {
                        startActivity(Intent(this@CreateSongket, MainActivity::class.java))
                        finish()
                    }
            }
            is States.Failed -> {
                Log.d(Constant.TAG_CREATE_SONGKET, "handleResponse: ${state.message}")
                Help.showToast(this@CreateSongket,state.message)
            }
        }
    }

    private fun action(id: Int,letterType: Int)
    {
        createSongketBinding.apply {
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
                    if(extracurricular.isBlank() || parentClub.isBlank())
                    {
                        Help.showToast(this@CreateSongket,getString(R.string.validation_letter_club))
                    }else{
                        allViewModel.createSongketMother(
                            letterType,"","",extracurricular,parentClub,id,
                            "","","","","",0.0
                        ).observe(this@CreateSongket){state -> handleResponse(state)}
                    }
                }
                5 -> {
                   if(semester.isBlank() && ranking.isBlank() && totalStudent.isBlank() && averageValue.isBlank())
                   {
                       Help.showToast(this@CreateSongket,getString(R.string.validation_form))
                   }else{
                       allViewModel.createSongketMother(letterType,"","","",
                           "",id,"","",ranking,semester,totalStudent,averageValue.toDouble())
                           .observe(this@CreateSongket){state -> handleResponse(state)}
                   }
                }
                6 -> {
                    if(nameUniversity.isBlank() && major.isBlank())
                    {
                        Help.showToast(this@CreateSongket,getString(R.string.validation_form))
                    }else{
                        allViewModel.createSongketMother(letterType,"","","",
                            "",id,nameUniversity,major,"","","",0.0)
                            .observe(this@CreateSongket){state -> handleResponse(state)}
                    }
                }
            }
        }
    }
}