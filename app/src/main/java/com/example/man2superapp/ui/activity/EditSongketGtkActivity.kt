@file:Suppress("DEPRECATION")
package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityCreateSongketGtkBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.SongketMotherGTK
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.gtk.SongketEmak
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditSongketGtkActivity : AppCompatActivity()
{
    private lateinit var editGtkBinding: ActivityCreateSongketGtkBinding
    private val allViewModel by viewModels<AllViewModel>()
    @Inject
    lateinit var localStore: LoginTemp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editGtkBinding = ActivityCreateSongketGtkBinding.inflate(layoutInflater)
        setContentView(editGtkBinding.root)

        onBackPressedDispatcher.addCallback(this@EditSongketGtkActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@EditSongketGtkActivity)
            }
        })
        val typeLetter = intent.getIntExtra(Constant.LETTER_TYPE,-1)
        val type = intent.getIntExtra(Constant.TYPE,-1)
        base(typeLetter,type)
    }

    private fun base(typeLetter: Int,type: Int)
    {
        editGtkBinding.apply {
            backButton.setOnClickListener {
                backToSongket()
            }
            btnCreate.text = "Update Songket Emak GTK"
            btnCreate.setOnClickListener {
                validationActionUpdate(letterType = typeLetter,type)
            }
            val listSongketMotherGtk = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            {
                intent.getParcelableExtra(Constant.LIST_SONGKET_MOTHER,SongketMotherGTK::class.java)
            }else{
                intent.getParcelableExtra<SongketMotherGTK>(Constant.LIST_SONGKET_MOTHER)
            }
            listSongketMotherGtk?.let { data ->
                when(data.letterStatement)
                {
                    1 -> {
                        tvSubtitle.text = "Surat Keterangan Cuti Tahunan"
                        parentRankOrGrade.visibility = View.VISIBLE
                        parentEndHoliday.visibility = View.VISIBLE
                        parentStartHoliday.visibility = View.VISIBLE
                        parentNip.visibility = View.VISIBLE
                        tvNipEmployee.visibility = View.VISIBLE
                        tvStartHoliday.visibility = View.VISIBLE
                        tvEndHoliday.visibility = View.VISIBLE
                        tvRankOrGrade.visibility = View.VISIBLE

                        etRankOrGrade.setText(data.rankOrGrade)
                        etNip.setText(data.nip)
                        etHoliday.setText(data.startHoliday)
                        etEndHoliday.setText(data.endHoliday)
                    }
                    2 -> {
                        tvSubtitle.text = "Surat Aktif Mengajar"
                        tvRankOrGrade.visibility = View.VISIBLE
                        parentRankOrGrade.visibility = View.VISIBLE
                        tvNipEmployee.visibility = View.VISIBLE
                        parentNip.visibility = View.VISIBLE
                        tvNuptk.visibility = View.VISIBLE
                        parentNuptk.visibility = View.VISIBLE
                        tvFieldStudy.visibility = View.VISIBLE
                        parentFieldStudy.visibility = View.VISIBLE

                        etRankOrGrade.setText(data.rankOrGrade)
                        etNip.setText(data.nip)
                        etNuptk.setText(data.nuptk)
                        etFieldStudy.setText(data.fieldStudy)
                    }
                    3 -> {
                        tvSubtitle.text = "Surat Rekomendasi"
                        tvRankOrGrade.visibility = View.VISIBLE
                        parentRankOrGrade.visibility = View.VISIBLE
                        tvNipEmployee.visibility = View.VISIBLE
                        parentNip.visibility = View.VISIBLE
                        tvFieldStudy.visibility = View.VISIBLE
                        parentFieldStudy.visibility = View.VISIBLE
                        tvHaveStudy.visibility = View.VISIBLE
                        parentHaveStudy.visibility = View.VISIBLE
                        tvTitleRecomendation.visibility = View.VISIBLE
                        parentTitleRecomendation.visibility = View.VISIBLE

                        etRankOrGrade.setText(data.rankOrGrade)
                        etNip.setText(data.nip)
                        etFieldStudy.setText(data.fieldStudy)
                        etHaveStudy.setText(data.haveYourEverTaughtSubject)
                        etTitleRecomendation.setText(data.recommendationTitle)
                    }
                }
            }
        }
    }

    private fun validationActionUpdate(letterType: Int,id: Int)
    {
        editGtkBinding.apply {
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
                        Help.showToast(this@EditSongketGtkActivity,"Mulai Cuti dan Akhir Cuti tidak boleh kosong")
                    }else{
                        allViewModel.updateSongketMotherGtk(id,ranKOrGrade,nip,"","","",startHoliday,endHoliday,"")
                            .observe(this@EditSongketGtkActivity){state -> handleResponse(state)}
                    }
                }
                2 -> {
                    allViewModel.updateSongketMotherGtk(
                       id,ranKOrGrade,nip,nuptk,fieldStudy,"","","",""
                    ).observe(this@EditSongketGtkActivity){state -> handleResponse(state)}
                }
                3 -> {
                    if(titleRecomendation.isBlank())
                    {
                        Help.showToast(this@EditSongketGtkActivity,"Rekemondasi wajib diisi")
                    }else{
                        allViewModel.updateSongketMotherGtk(id,ranKOrGrade,nip,"",fieldStudy,haveStudy,"","",titleRecomendation)
                            .observe(this@EditSongketGtkActivity){state -> handleResponse(state)}
                    }
                }
            }
        }
    }

    private fun handleResponse(state: States<UpdateSongketMother>)
    {
        when(state)
        {
            is States.Loading -> {}
            is States.Success -> {
                Help.showToast(this@EditSongketGtkActivity,state.data.message)
                backToSongket()
            }
            is States.Failed -> {
                backToSongket()
            }
        }
    }

    private fun backToSongket()
    {
        startActivity(Intent(this@EditSongketGtkActivity,SongketActivity::class.java))
        finish()
    }
}