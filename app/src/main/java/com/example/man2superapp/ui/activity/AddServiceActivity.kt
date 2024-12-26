package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityAddServiceBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddServiceActivity: AppCompatActivity()
{
    private lateinit var addServiceBinding: ActivityAddServiceBinding
    private val allViewModel by viewModels<AllViewModel>()
    @Inject
    lateinit var localStore: LoginTemp
    private var useMap: Map<String,Int>? = null

    companion object{
        const val TAG = "AddServiceActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addServiceBinding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(addServiceBinding.root)

        onBackPressedDispatcher.addCallback(this@AddServiceActivity,object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@AddServiceActivity)
            }
        })
        val typeLetter = intent.getIntExtra(Constant.LETTER_TYPE, -1)
        val type = intent.getIntExtra(Constant.TYPE,-1)

        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                data.role?.let { base(letterTye = typeLetter,type, it) }
            }
        }

        allViewModel.fetchAllOfficerService(this@AddServiceActivity)
    }

    private fun base(letterTye: Int, type: Int,role: String)
    {
        addServiceBinding.apply {

            allViewModel.userList.observe(this@AddServiceActivity){ userList ->
                val userMap = userList.associateBy({it.name},{it.id})
                Log.d(TAG, "validator: ${userList.map { it.name }}")
                val adapter = ArrayAdapter(
                    this@AddServiceActivity,
                    android.R.layout.simple_dropdown_item_1line,
                    userList.map{it.name}
                )
                addServiceBinding.etNameServiceEmployee.setAdapter(adapter)
                this@AddServiceActivity.useMap = userMap
            }

            setAdapterData(addServiceBinding.etRatingService)

            nameTitleText(role,letterTye,tvMan)

            backButton.setOnClickListener {
                backToSongketActivity()
            }

            btnAddService.setOnClickListener {
                validator(letterTye,type,role)
            }
        }
    }

    private fun validator(letterTye: Int, id: Int,role: String)
    {
        addServiceBinding.apply {
            val employee = parentServiceEmployee.editText?.text.toString()
            val ratingService = parentRatingService.editText?.text.toString()

            val relatedOfficialsId = useMap?.get(employee)

           if(employee.isEmpty() || ratingService.isEmpty())
           {
               Help.showToast(this@AddServiceActivity,getString(R.string.validation_form))
           }else{
               when(letterTye){
                   1 -> {
                       relatedOfficialsId?.let {
                           observer(role,id,
                               it,ratingService.toInt())
                       }
                   }
                   2 -> {
                       relatedOfficialsId?.let {
                           observer(role,id,
                               it,ratingService.toInt())
                       }
                   }
                   3 -> {
                       relatedOfficialsId?.let {
                           observer(role,id,
                               it,ratingService.toInt())
                       }
                   }
                   4 -> {
                       relatedOfficialsId?.let {
                           observer(role,id,
                               it,ratingService.toInt())
                       }
                   }
                   5 -> {
                       relatedOfficialsId?.let {
                           allViewModel.createServiceSongketMother(id,
                               it,ratingService.toInt()).observe(this@AddServiceActivity){data -> handLeResponse(data)}
                       }
                   }
                   6 -> {
                       relatedOfficialsId?.let {
                           allViewModel.createServiceSongketMother(id,
                               it,ratingService.toInt()).observe(this@AddServiceActivity){data -> handLeResponse(data)}
                       }
                   }
               }
           }
        }
    }

    private fun nameTitleText(role: String,letterTye: Int,textView: TextView)
    {
        if(role == "siswa")
        {
            when(letterTye)
            {
                1 -> {
                    textView.text = "Surat Keterangan Aktif Sekolah"
                }
                2 -> {
                    textView.text = "Surat Keterangan Berkelakuan Baik"
                }
                3 -> {
                    textView.text = "Surat Keterangan Ekskul/Club"
                }
                4 -> {
                    textView.text = "Surat Keterangan Lomba"
                }
                5 -> {
                    textView.text = "Surat Keterangan Peringkat"
                }
                6 -> {
                    textView.text = "Surat Rekomendasi Universitas"
                }
            }
        }else{
            when(letterTye)
            {
                1 -> textView.text = "Surat Keterangan Cuti Tahunan"
                2 -> textView.text = "Surat Keterangan Aktif Mengajar"
                3 -> textView.text = "Surat Rekomendasi"
                4 -> textView.text = "Surat Tugas"
            }
        }
    }

    private fun observer(role: String,id: Int,employee: Int,ratingService: Int)
    {
        if (role == "siswa")
        {
            allViewModel.createServiceSongketMother(id,employee,ratingService).observe(this@AddServiceActivity){handLeResponse(it)}
        }else{
            allViewModel.createServiceSongketMotherGtk(id,employee,ratingService).observe(this@AddServiceActivity){handLeResponse(it)}
        }
    }

    private fun handLeResponse(state: States<UpdateSongketMother>)
    {
        when(state)
        {
            is States.Loading -> {
                isProgress(true)
            }
            is States.Success -> {
                isProgress(false)
                if(state.data.success){
                    Help.showToast(this@AddServiceActivity,state.data.message)
                    backToSongketActivity()
                }else{
                    Help.showToast(this@AddServiceActivity,state.data.message)
                    backToSongketActivity()
                }
            }
            is States.Failed -> {
                isProgress(false)
                backToSongketActivity()
                Help.showToast(this@AddServiceActivity,state.message.toString())
            }
        }
    }

    private fun setAdapterData(text: AutoCompleteTextView)
    {
        val data: Array<String> = resources.getStringArray(R.array.opsi)
        val arrayAdapter = ArrayAdapter(this@AddServiceActivity, com.karumi.dexter.R.layout.support_simple_spinner_dropdown_item,data)
        text.setAdapter(arrayAdapter)
    }

    private fun isProgress(isShow: Boolean)
    {
        addServiceBinding.apply {
            progressBar.visibility = if(isShow) View.VISIBLE else View.GONE
            btnAddService.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun backToSongketActivity()
    {
        startActivity(Intent(this@AddServiceActivity,SongketActivity::class.java)).also { finish() }
    }

}