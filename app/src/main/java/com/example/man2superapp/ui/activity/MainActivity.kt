package com.example.man2superapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Contacts.Intents
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.man2superapp.databinding.ActivityMainBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.fragment.ProfilePopUpFragment
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
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
        actionToProfile(mainBinding.imProfile)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Help.alertDialog(this@MainActivity)
            }
        })
        allViewModel.getAllArticle()
        observerView()
        setUpSlider()
    }

    private fun observerView()
    {
        allViewModel.loading.observe(this@MainActivity){ loading ->
            mainBinding.apply {
                progressBar.visibility = if(loading) View.VISIBLE else View.GONE
                sliderCard.visibility = if(loading) View.GONE else View.VISIBLE
            }
        }
    }

    private fun setUpSlider()
    {
        val imageSlider = mainBinding.sliderCard
        val slideModels = mutableListOf<SlideModel>()
        allViewModel.article.observe(this@MainActivity){ articles ->
            articles.let {
                for(article in it){
                    val contentImage = Constant.IMAGE_URL_NEWS + article.image
                    slideModels.add(SlideModel(contentImage,article.title,ScaleTypes.CENTER_CROP))
                }
                imageSlider.setImageList(slideModels, ScaleTypes.FIT)
                imageSlider.setItemClickListener(object: ItemClickListener{
                    override fun doubleClick(position: Int) {
                        openWebView("https://www.m2mpekanbaru.sch.id/berita")
                    }
                    override fun onItemSelected(position: Int) {
                        openWebView("https://www.m2mpekanbaru.sch.id/berita")
                    }
                })
            }
        }
    }

    private fun openWebView(url: String)
    {
        Intent(this@MainActivity,WebViewActivity::class.java).apply {
            putExtra("URL",url)
        }.also { startActivity(it) }

    }

    private fun cardAction() {
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                Log.d("TAG", "checkToken: ${data.token}")
                mainBinding.tvUserName.text = data.name
                data.token?.let { handleCardActions(it) }
                data.role?.let{checkRoleLogin(it)}
            }
        }
        mainBinding.btnLogout.setOnClickListener {
            logoutEmployee()
        }
    }

    private fun checkRoleLogin(role: String)
    {
        mainBinding.apply {
            if(role == "siswa")
            {
                songketCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                ekinCard.visibility = View.GONE
                pelajarCard.visibility = View.VISIBLE
                etatibCard.visibility = View.VISIBLE
            }else if (role == "kepala_madrasah"){
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                songketCard.visibility = View.GONE
                pelajarCard.visibility = View.GONE
                etatibCard.visibility = View.GONE
            }else if (role == "super_admin"){
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                jurnalCard.visibility = View.GONE
                songketCard.visibility = View.VISIBLE
                pelajarCard.visibility = View.GONE
                etatibCard.visibility = View.GONE
            }else{
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                songketCard.visibility = View.VISIBLE
            }
        }
    }

    private fun logoutEmployee()
    {
        lifecycleScope.launch {
            localStore.putToken(LoginModel("",0,"","","",0,"","","","","",
                "","","","","")).also {
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                    .also { finish() }
            }
        }
    }

    private fun actionToProfile(imageView:ImageView)
    {
        imageView.setOnClickListener {
            popUpToProfile()
        }
    }

    private fun showMaterialTextViewName(isShow: Boolean)
    {
        mainBinding.tvUserName.visibility = if (isShow) View.VISIBLE else View.GONE
        mainBinding.btnLogout.visibility = if(isShow) View.VISIBLE else View.GONE
        mainBinding.imProfile.visibility = if(isShow) View.VISIBLE else View.GONE
    }

    private fun popUpToProfile()
    {
        lifecycleScope.launch {
            localStore.getToken().collect{ data ->
                data.id?.let {
                    if(data.role == "siswa")
                    {
                        allViewModel.showProfileStudent(data.id).observe(this@MainActivity){ state ->
                            when(state){
                                is States.Loading -> {}
                                is States.Success -> {
                                    fragmentShow(data.role,state.data.data.name,state.data.data.email,state.data.data.nisn,state.data.data.classX.nameClass,state.data.data.numberHandphone,
                                        state.data.data.mother,state.data.data.father,state.data.data.address,"",state.data.data.dateBirthday,
                                        state.data.data.placeBirthday,state.data.data.gender,state.data.data.classX.id)
                                }
                                is States.Failed -> {
                                    Help.showToast(this@MainActivity,state.message)
                                }
                            }
                        }
                    }else{
                        allViewModel.showProfileEmployee(data.id).observe(this@MainActivity){ state ->
                            when(state)
                            {
                                is States.Loading -> {}
                                is States.Success -> {
                                    data.role?.let { it1 ->
                                        fragmentShow(
                                            it1,state.data.data.name,state.data.data.email,"","",state.data.data.numberHandphone,"","","",state.data.data.position,
                                            "","",state.data.data.gender,0)
                                    }
                                }
                                is States.Failed -> {
                                    Help.showToast(this@MainActivity,state.message)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fragmentShow(role: String,name: String,email: String,nisn: String,className: String,numberPhone: String,
                              mother: String,father: String,address: String,posititon: String,dateBirthday: String,
                             placeBirthday: String, gender: Int,classId: Int)
    {
        val fragment =  ProfilePopUpFragment(role,name,email,nisn,className,numberPhone,mother,father,
            address,posititon,dateBirthday,placeBirthday,gender,classId,this@MainActivity)
        fragment.show(supportFragmentManager,"ProfilePopupFragment")
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
                Help.showToast(this@MainActivity,"Fitur masih dalam tahap pengembangan")
//                val target = if (token.isEmpty()) LoginActivity::class.java else JurnalActivity::class.java
//                startActivity(Intent(this@MainActivity,target))
            }

            etatibCard.setOnClickListener {
                Help.showToast(this@MainActivity,"Fitur masih dalam tahap pengembangan")
            }

            pelajarCard.setOnClickListener {
                Help.showToast(this@MainActivity,"Fitur masih dalam tahap pengembangan")
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

