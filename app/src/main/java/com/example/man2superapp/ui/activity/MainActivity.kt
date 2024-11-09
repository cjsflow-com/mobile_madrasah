package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityMainBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        cardAction()
    }

    private fun cardAction() {
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                Log.d("TAG", "checkToken: ${data.token}")
                mainBinding.tvUserName.text = data.name
                data.token?.let { handleCardActions(it) }
            }
        }
        mainBinding.btnLogout.setOnClickListener {
            logoutEmployee()
        }
    }

    private fun logoutEmployee()
    {
        lifecycleScope.launch {
            localStore.putToken(LoginModel("","",0,"","","")).also {
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                    .also { finish() }
            }
        }
    }

    private fun showMaterialTextViewName(isShow: Boolean)
    {
        mainBinding.tvUserName.visibility = if (isShow) View.VISIBLE else View.GONE
        mainBinding.btnLogout.visibility = if(isShow) View.VISIBLE else View.GONE
    }

    private fun handleCardActions(token: String) {
        if(token.isEmpty())
        {
            showMaterialTextViewName(false)
        }else{
            showMaterialTextViewName(true)
        }
        mainBinding.apply {
            ekinCard.setOnClickListener {
                val target = if (token.isEmpty()) LoginActivity::class.java else EkinActivity::class.java
                startActivity(Intent(this@MainActivity,target))
            }

            jurnalCard.setOnClickListener {
                val target = if (token.isEmpty()) LoginActivity::class.java else JurnalActivity::class.java
                startActivity(Intent(this@MainActivity,target))
            }

            songketCard.setOnClickListener {
                val target = if(token.isEmpty()) LoginActivity::class.java else SongketActivity::class.java
                startActivity(Intent(this@MainActivity,target))
            }

            wbsCard.setOnClickListener {
                startActivity(Intent(this@MainActivity, wbs::class.java))
            }
        }
    }
}

//    private fun showMaterialDialog()
//    {
//        MaterialAlertDialogBuilder(this)
//            .setTitle("Informasi")
//            .setMessage("Anda ingin login terlebih dahulu atau mengisi form langsung ?")
//            .setPositiveButton("Ya, saya ingin login dulu"){dialog,_ ->
//                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
//                dialog.dismiss()
//            }
//            .setNegativeButton("Saya langsung mengisi form saja"){dialog, _ ->
//                startActivity(Intent(this@MainActivity, wbs::class.java))
//                dialog.dismiss()
//            }
//            .show()
//    }

