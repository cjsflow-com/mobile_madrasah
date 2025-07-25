package com.example.man2superapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Secure.getString
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.man2superapp.EkinAfter
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityMainBinding
import com.example.man2superapp.databinding.DialogTargetPointBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.activity.counseling.SessionCounselingActivity
import com.example.man2superapp.ui.fragment.ChooseViewFragment
import com.example.man2superapp.ui.fragment.ProfilePopUpFragment
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private var isDialogShown = false

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

    @SuppressLint("SetTextI18n")
    private fun observerView()
    {
        allViewModel.textSuccess.observe(this@MainActivity){
            Help.showToast(this@MainActivity,it)
        }
        allViewModel.loading.observe(this@MainActivity){ loading ->
            mainBinding.apply {
                progressBar.visibility = if(loading) View.VISIBLE else View.GONE
                sliderCard.visibility = if(loading) View.GONE else View.VISIBLE
            }
        }
        allViewModel.totalPoint.observe(this@MainActivity){ model ->
            if(model == 0)
            {
                mainBinding.tvTotalPoints.setText("Total Poin Pelanggaran: -")
            }else{
                mainBinding.tvTotalPoints.setText("Total Poin Pelanggaran: $model")
            }
        }
        allViewModel.textError.observe(this@MainActivity){
            Help.showToast(this@MainActivity,it)
        }
        allViewModel.messageViolation.observe(this@MainActivity){
            setDialogViolationTarget(it)
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
                withContext(Dispatchers.Main)
                {
                    if(data.role == "siswa"){
                        if (data.numberPhoneParent == "" && !isDialogShown){
                            data.token?.let {
                                setDialogInput(it)
                            }
                        }
                        data.token?.let { allViewModel.getTotalPointStudent(it) }
                        data.token?.let { allViewModel.getTargetViolation(it) }
                    }
                    data.token?.let {
                        handleCardActions(it)
                    }
                    Log.d("TAG", "checkToken: ${data.token}")
                    mainBinding.tvUserName.text = data.name
                    data.role?.let{checkRoleLogin(it)}
                }
            }
        }

        mainBinding.btnLogout.setOnClickListener {
            logoutEmployee()
        }
    }

    private fun setDialogViolationTarget(message: String)
    {
        val view = layoutInflater.inflate(R.layout.dialog_target_point,null)
        val mtvError = view.findViewById<MaterialTextView>(R.id.mtvError)
        val imageClose = view.findViewById<MaterialCardView>(R.id.close)
        val dialog = MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Peringatan")
            .setView(view)
            .setCancelable(false)
            .create()
        mtvError.text = "$message \n Point anda telah melampui dari target poin yang telah diberikan oleh madrasah, Silahkan jumpai admin"
        imageClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setDialogInput(token: String) {
        val view = layoutInflater.inflate(R.layout.dialog_input, null)
        val textInputLayout = view.findViewById<TextInputLayout>(R.id.parentNumberParentPhone)

        // Membuat AlertDialog
        val dialog = MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Masukkan data nomor orangtua siswa")
            .setView(view)
            .setCancelable(false)
            .setPositiveButton("Oke", null) // Tombol Oke, tapi listener di-overwrite nanti
            .create()

        dialog.show()

        // Overwrite tombol PositiveButton setelah dialog ditampilkan
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var inputText = textInputLayout.editText?.text.toString().trim()

            if (inputText.isNotEmpty()) {
                // Jika input tidak kosong, panggil ViewModel untuk update
                if(inputText.startsWith("0"))
                {
                    inputText = "62" + inputText.substring(1)
                }
                allViewModel.updatePhoneNumberParent(token, inputText).observe(this@MainActivity) { state ->
                    when (state) {
                        is States.Loading -> {}
                        is States.Success -> {
                            lifecycleScope.launch {
                                localStore.putToken(LoginModel(numberPhoneParent = inputText))
                            }
                            if (state.data.success) {
                                Help.showToast(this@MainActivity, state.data.message)
                                isDialogShown = false
                                dialog.dismiss() // Tutup dialog jika berhasil
                            } else {
                                Help.showToast(this@MainActivity, state.data.message)
                            }
                        }
                        is States.Failed -> {
                            Help.showToast(this@MainActivity, state.message)
                        }
                    }
                }
            }else if(inputText.length > 13)
            {
                Help.showToast(this@MainActivity,"Nomor tidak boleh lebih dari 13 karakter")
            } else {
                // Tampilkan pesan jika input kosong dan dialog tidak ditutup
                Help.showToast(this@MainActivity, "Inputan nomor orangtua siswa harus diisi")
            }
        }
    }


    private fun checkRoleLogin(role: String)
    {
        mainBinding.apply {
            if(role == "siswa")
            {
                songketCard.visibility = View.VISIBLE
                tvTotalPoints.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                ekinCard.visibility = View.GONE
                jurnalCard.visibility = View.GONE
                pelajarCard.visibility = View.VISIBLE
                etatibCard.visibility = View.VISIBLE
            }else if (role == Constant.LEADER){
                tvTotalPoints.visibility = View.GONE
                ekinCard.visibility = View.GONE
                wbsCard.visibility = View.VISIBLE
                songketCard.visibility = View.GONE
                pelajarCard.visibility = View.GONE
                etatibCard.visibility = View.GONE
            }else if (role == Constant.SUPER_ADMIN){
                tvTotalPoints.visibility = View.GONE
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                jurnalCard.visibility = View.GONE
                songketCard.visibility = View.VISIBLE
                pelajarCard.visibility = View.GONE
                etatibCard.visibility = View.GONE
            }else if(role == Constant.TEACHER || role == Constant.KONSELOR){
                tvTotalPoints.visibility = View.GONE
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                pelajarCard.visibility = View.GONE
                songketCard.visibility = View.VISIBLE
                jurnalCard.visibility = View.VISIBLE
                etatibCard.visibility = View.VISIBLE
            }else if (role == Constant.KESISWAAN || role == Constant.KARYAWAN || role == Constant.PTSP)
            {
                tvTotalPoints.visibility = View.GONE
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                songketCard.visibility = View.VISIBLE
                jurnalCard.visibility = View.GONE
                pelajarCard.visibility = View.GONE
                etatibCard.visibility = View.GONE
            }else{
                tvTotalPoints.visibility = View.GONE
                ekinCard.visibility = View.VISIBLE
                wbsCard.visibility = View.VISIBLE
                songketCard.visibility = View.VISIBLE
                jurnalCard.visibility = View.VISIBLE
                pelajarCard.visibility = View.VISIBLE
                etatibCard.visibility = View.VISIBLE
            }
        }
    }

    private fun logoutEmployee()
    {
        lifecycleScope.launch {
            localStore.putToken(LoginModel("",0,"","","",0,"","","","","",
                "","","","","","")).also {
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
        mainBinding.tvTotalPoints.visibility = if (isShow) View.VISIBLE else View.GONE
        mainBinding.imProfile.visibility = if(isShow) View.VISIBLE else View.GONE
        mainBinding.iconBell.visibility = if(isShow) View.VISIBLE else View.GONE
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
                                        state.data.data.placeBirthday,state.data.data.gender,state.data.data.classX.id,state.data.data.totalPoint,state.data.data.numberHandphoneParent)
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
                                            "","",state.data.data.gender,0,0,"")
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
                             placeBirthday: String, gender: Int,classId: Int,totalPoint: Int,numberPhoneParent: String)
    {
        val fragment =  ProfilePopUpFragment(role,name,email,nisn,className,numberPhone,mother,father,
            address,posititon,dateBirthday,placeBirthday,gender,classId,totalPoint,numberPhoneParent,this@MainActivity)
        fragment.show(supportFragmentManager,"ProfilePopupFragment")
    }

    @SuppressLint("HardwareIds")
    fun showDeviceInfoDialog()
    {
        val deviceName = Build.MODEL
        val deviceId = getString(contentResolver,Settings.Secure.ANDROID_ID)
        val message = """
            Nama Device Anda: $deviceName
            ID Device Anda: $deviceId
        """.trimIndent()

        AlertDialog.Builder(this@MainActivity)
            .setTitle("Informasi Perangkat")
            .setMessage(message)
            .setPositiveButton("Oke"){dialog,_ -> dialog.dismiss()}
            .show()
    }

    private fun handleCardActions(token: String) {
        if(token.isEmpty())
        {
            showMaterialTextViewName(false)
        }else{
            showMaterialTextViewName(true)
        }
        mainBinding.apply {
            iconBell.setOnClickListener {
                showDeviceInfoDialog()
            }
            ekinCard.setOnClickListener {
                val target = if (token.isEmpty()) LoginActivity::class.java else EkinAfter::class.java
                startActivity(Intent(this@MainActivity,target))
            }

            jurnalCard.setOnClickListener {
                Help.showToast(this@MainActivity,"Fitur masih dalam tahap pengembangan")
//                val target = if (token.isEmpty()) LoginActivity::class.java else JurnalActivity::class.java
//                startActivity(Intent(this@MainActivity,target))
            }

            etatibCard.setOnClickListener {
//                Help.showToast(this@MainActivity,"Fitur masih dalam tahap pengembangan")
                if (token.isEmpty())
                {
                    startActivity(Intent(this@MainActivity,LoginActivity::class.java)).also { finish() }
                }else{
                    showFragment()
                }
            }

            pelajarCard.setOnClickListener {
                val target = if (token.isEmpty()) LoginActivity::class.java else SessionCounselingActivity::class.java
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

    private fun showFragment()
    {
        val bottomSheetFragment = ChooseViewFragment()
        bottomSheetFragment.show(supportFragmentManager,"ChooseViewFragment")
    }
}

