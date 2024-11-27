package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityCreateSongketBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.network.States
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

    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createSongketBinding = ActivityCreateSongketBinding.inflate(layoutInflater)
        setContentView(createSongketBinding.root)
        val letterStatement = intent.getStringExtra(Constant.LETTER_STATEMENT)
        createSongketBinding.tvMan.text = letterStatement

        createSongketBinding.apply {
            btnCreate.setOnClickListener {
                lifecycleScope.launch {
                    localStore.getToken().collect{data ->
                        data.id?.let { action(it) }
                    }
                }
            }
        }
    }

    private fun action(id: Int)
    {
        createSongketBinding.apply {
            val parentEskul = parentExtracurricular.editText?.text.toString()
            val parentClub = parentNameClub.editText?.text.toString()
            allViewModel.createSongketMother(3,"","",parentEskul,parentClub,id)
                .observe(this@CreateSongket){ state ->
                    when(state)
                    {
                        is States.Loading -> {

                        }
                        is States.Success -> {
                            Log.d("TAG", "action: ${state.data.message}")
                            Help.showToast(this@CreateSongket,state.data.message)
                                .also { startActivity(Intent(this@CreateSongket,MainActivity::class.java))
                                    .also { finish() }}
                        }
                        is States.Failed -> {
                            Log.d("TAG", "action: ${state.message}")
                            Help.showToast(this@CreateSongket,state.message)
                        }
                    }
                }
        }
    }
}