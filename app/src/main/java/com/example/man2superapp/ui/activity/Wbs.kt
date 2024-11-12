package com.example.man2superapp.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import java.util.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityWbsBinding
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class wbs : AppCompatActivity() {

    private lateinit var wbsBinding: ActivityWbsBinding
    private var getFile: File? = null
    private val allViewModel by viewModels<AllViewModel>()
    private var userMap: Map<String,Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wbsBinding = ActivityWbsBinding.inflate(layoutInflater)
        setContentView(wbsBinding.root)
        // Mengakses field TextInputEditText untuk tanggal

        // Set listener untuk memunculkan DatePickerDialog ketika field tanggal diklik
        wbsBinding.etTanggalKejadian.setOnClickListener {
            showDatePickerDialog(wbsBinding.etTanggalKejadian)
        }
        showProgressBar(false)
        showImage(false)
        allViewModel.fetchAllUsers(this@wbs)
        clickButtonUploadBukti()
    }

    private fun clickButtonUploadBukti()
    {
        wbsBinding.apply {
            uploadBukti.setOnClickListener {
                checkGallery()
            }

            btnSend.setOnClickListener {
                postWbs()
            }

            allViewModel.userList.observe(this@wbs) { userList ->
                val userMap = userList.associateBy({it.name},{it.id})
                Log.d("WBS", "user: ${userList.map { it.name }}")
                val adapter = ArrayAdapter(
                    this@wbs,
                    android.R.layout.simple_dropdown_item_1line,
                    userList.map { it.name })
                etPejabat.setAdapter(adapter)
                this@wbs.userMap = userMap
            }
        }
    }

    private fun showProgressBar(isShow: Boolean)
    {
        wbsBinding.apply {
            progressBarWbs.visibility = if (isShow) View.VISIBLE else View.GONE
            scrollFormWbs.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }


    private fun checkGallery()
    {
        Dexter.withContext(this@wbs)
            .withPermissions(
                android.Manifest.permission.CAMERA
            ).withListener(object: MultiplePermissionsListener{
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    Help.showToast(this@wbs,"Permission di izinkan")
                    takeABuktiFromGallery()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            }).onSameThread().check()
    }

    private fun takeABuktiFromGallery()
    {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }

        val chooser = Intent.createChooser(intent,"Pilih gambar untuk menjadi bukti")
        launcherGallery.launch(chooser)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    {
        if(it.resultCode == RESULT_OK)
        {
            val selectPdf: Uri = it.data?.data as Uri

            val filePDf = Help.uriToFile(selectPdf,this@wbs)
            getFile = filePDf
            val result = BitmapFactory.decodeFile(getFile?.path)
            showImage(true)
            wbsBinding.imageBukti.load(result)
            {
                crossfade(true)
                crossfade(600)
                error(R.drawable.baseline_broken_image_24)
                placeholder(R.drawable.baseline_image_24)
            }
        }
    }

    private fun showImage(isShow: Boolean)
    {
        wbsBinding.imageBukti.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun postWbs(){
        wbsBinding.apply {
            val parentTopikPengaduan = parentPengaduan.editText?.text.toString()
            val parentKejadian = parentTanggalKejadian.editText?.text.toString()
            val parentPejabat = parentEtPejabat.editText?.text.toString()
            val parentUniWork = parentUnitKerja.editText?.text.toString()
            val parentLocation = parentLokasi.editText?.text.toString()
            val parentUraianWbs = parentUraian.editText?.text.toString()

            if (parentTopikPengaduan.isEmpty())
            {
                parentPengaduan.error = "Topik Pengaduan tidak boleh kosong"
                parentPengaduan.isErrorEnabled = true
                parentPengaduan.requestFocus()
            }else if(parentKejadian.isEmpty()){
                parentTanggalKejadian.error = "Tanggal Kejadi tidak boleh kosong"
                parentTanggalKejadian.isErrorEnabled = true
                parentTanggalKejadian.requestFocus()
            }else if(parentPejabat.isEmpty()) {
                parentEtPejabat.error = "Pejabat tidak boleh kosong"
                parentEtPejabat.isErrorEnabled = true
                parentEtPejabat.requestFocus()

            }else if (parentUniWork.isEmpty())
            {
                parentUnitKerja.error = "Unit Kerja tidak boleh kosong"
                parentUnitKerja.isErrorEnabled = true
                parentUnitKerja.requestFocus()
            }else if(parentLocation.isEmpty())
            {
                parentLokasi.error = "Lokasi tidak boleh kosong"
                parentLokasi.isErrorEnabled = true
                parentLokasi.requestFocus()
            }else if(parentUraianWbs.isEmpty())
            {
                parentUraian.error = "Uraian tidak boleh kosong"
                parentUraian.isErrorEnabled = true
                parentUraian.requestFocus()
            }else{
                    val topicPengaduan = parentTopikPengaduan.toRequestBody("text/plain".toMediaType())
                    val tanggalKejadian = parentKejadian.toRequestBody("text/plain".toMediaType())
                    val unitKerja = parentUniWork.toRequestBody("text/plain".toMediaType())
                    val lokasi = parentLocation.toRequestBody("text/plain".toMediaType())
                    val uraianParent = parentUraianWbs.toRequestBody("text/plain".toMediaType())
//                    val documentation =  fileData.asRequestBody("image/jpeg".toMediaType())
                    val imageData: MultipartBody.Part? = getFile?.let { file ->
                        val fileData = Help.reduceFileImage(file)
                        val documentation = fileData.asRequestBody("image/jpeg".toMediaType())
                        MultipartBody.Part.createFormData("documentation", fileData.name, documentation)
                    }

                     val relatedOfficialsId = userMap?.get(parentPejabat)
                    Log.d("CreateWBS", "postWbsExperiment: $relatedOfficialsId")

                    if(relatedOfficialsId != null) {
                        val officialsIdRequestBody = relatedOfficialsId.toString().toRequestBody("text/plain".toMediaType())
                        lifecycleScope.launch {
                            allViewModel.createWbs(topicPengaduan,
                                tanggalKejadian,officialsIdRequestBody,unitKerja,
                                lokasi,uraianParent,
                                imageData).observe(this@wbs) { state ->
                                when (state) {
                                    is States.Loading -> {
                                        showProgressBar(true)
                                    }

                                    is States.Success -> {
                                        showProgressBar(false)
                                        Log.d("postWBS", "postWbs: ${state.data.message}")
                                        Help.showToast(this@wbs, state.data.message)
                                            .also { startActivity(Intent(this@wbs, MainActivity::class.java)).also { finish() } }
                                    }
                                    is States.Failed -> {
                                        showProgressBar(false)
                                        Log.d("TAG", "postWbs: error => ${state.message}")
                                        Help.showToast(this@wbs,state.message)
                                    }
                                }
                            }
                        }
                    }else{
                        Help.showToast(this@wbs,"Pejabat tidak ditemukan")
                    }
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
}