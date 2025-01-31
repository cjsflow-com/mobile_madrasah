package com.example.man2superapp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.provider.Telephony.Mms.Intents
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ActivityAttendanceStudentBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.toAttendance
import com.example.man2superapp.source.local.model.toGenerateAttendance
import com.example.man2superapp.source.network.States
import com.example.man2superapp.ui.adapter.AttendanceAdapter
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AttendanceStudent : AppCompatActivity()
{
    private lateinit var attendanceBinding: ActivityAttendanceStudentBinding
    @Inject
    lateinit var localStore: LoginTemp
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val allViewModel by viewModels<AllViewModel>()
    private val adapterAttendance by lazy { AttendanceAdapter()}

    companion object{
        const val TAG = "AttendanceStudent"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attendanceBinding = ActivityAttendanceStudentBinding.inflate(layoutInflater)
        setContentView(attendanceBinding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@AttendanceStudent)
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                val location = p0.lastLocation
                location?.let {
                    getAddressFromLocation(it.latitude, it.longitude)
                }
                super.onLocationResult(p0)
            }
        }
        setLayoutManager()
        attendanceBinding.apply {
            allViewModel.timeIn.observe(this@AttendanceStudent){ timeIn ->
                mtvContentInTime.text = timeIn
            }
            allViewModel.timeOut.observe(this@AttendanceStudent){timeOut ->
                mtvContentTimeOut.text = timeOut
            }
            floatingActionButton.setOnClickListener { finish() }
            scanFace.isEnabled = false
            scanFace.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(
                this@AttendanceStudent,R.color.inactivate
            ))
            scanFace.setOnClickListener { scanFace() }
            timeToday.format24Hour = "HH:mm:ss a"
            setLocationUser()
            setCurrentDate()
        }
        listByMonth()
        setAttendanceStudentToday()
        lifecycleScope.launch {
            localStore.getToken().collect{ model ->
                model.token?.let { allViewModel.setAllAttendanceMonth(it) }
            }
        }
    }

    private fun listByMonth()
    {
            allViewModel.listAttendance.observe(this@AttendanceStudent){state ->
                when(state)
                {
                    is States.Loading -> {
                        showProgressBar(true)
                        attendanceBinding.mtvTextEmpty.visibility = View.GONE
                    }
                    is States.Success -> {
                        if(!state.data.success)
                        {
                            attendanceBinding.apply {
                                mtvTextEmpty.visibility = View.VISIBLE
                                mtvTextEmpty.text = state.data.message
                            }
                        }else{
                           val attendance = state.data.attendance.toGenerateAttendance()
                            if(attendance.isEmpty())
                            {
                                attendanceBinding.apply {
                                    mtvTextEmpty.visibility = View.VISIBLE
                                    mtvTextEmpty.text = "Tidak ada absensi pada bulan ini"
                                    progressBarAttendance.visibility = View.GONE
                                    rvAttendanceAllToday.visibility = View.GONE
                                }
                                return@observe
                            }
                            Help.showToast(this@AttendanceStudent,state.data.message)
                            showProgressBar(false)
                            attendanceBinding.mtvTextEmpty.visibility = View.GONE
                            adapterAttendance.submitListData(attendance)
                        }
                    }
                    is States.Failed -> {
                        attendanceBinding.apply {
                            mtvTextEmpty.visibility = View.VISIBLE
                            rvAttendanceAllToday.visibility = View.GONE
                            progressBarAttendance.visibility = View.GONE
                        }
                    }
                }
            }
    }

    private fun showProgressBar(isShow: Boolean)
    {
        attendanceBinding.apply {
            progressBarAttendance.visibility = if (isShow) View.VISIBLE else View.GONE
            rvAttendanceAllToday.visibility = if (isShow) View.GONE else View.VISIBLE
//            mtvTextEmpty.visi
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAttendanceStudentToday()
    {
        lifecycleScope.launch {
            localStore.getToken().collect{ model ->
                attendanceBinding.mtvUser.text = "Hallo ${model.name}"
                model.token?.let { data ->  getAttendanceToday(data) }
            }
        }
    }

    private fun getAttendanceToday(token: String)
    {
        allViewModel.getAttendanceToday(token).observe(this@AttendanceStudent)
        { model ->
            when(model)
            {
                is States.Loading -> {}
                is States.Success -> {
                    if(model.data.timeAttendanceStudentIn == "?" && model.data.timeAttendanceStudentOut == "?")
                    {
                        attendanceBinding.mtvTextEmpty.visibility = View.VISIBLE
                    }
                    attendanceBinding.mtvContentInTime.text = model.data.timeAttendanceStudentIn
                    attendanceBinding.mtvContentTimeOut.text = model.data.timeAttendanceStudentOut
                }
                is States.Failed -> Help.showToast(this@AttendanceStudent,model.message)
            }
        }
    }

    private fun scanFace()
    {
        val intent = Intent(this@AttendanceStudent,CameraActivity::class.java)
        scanFaceLauncherIntent.launch(intent)
    }

    private val scanFaceLauncherIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == RESULT_OK)
        {
            val resultFace = it.data?.getBooleanExtra(Constant.IS_FACE_DETECTED,false)?: false
            if(resultFace)
            {
                addAttendanceStudent()
                Log.d(TAG, "ResultScan: $resultFace")
            }else{
                Help.showToast(this@AttendanceStudent,"Wajah anda tidak terdeteksi")
            }
        }
    }

    private fun addAttendanceStudent(){
        lifecycleScope.launch {
            localStore.getToken().collect{ it ->
                it.token?.let { it1 ->
                    allViewModel.addAttendanceToday(it1).observe(this@AttendanceStudent){face ->
                        when(face){
                            is States.Loading -> {}
                            is States.Success -> {
                                val position = face.data.position
                                val message = if(position == 1) {
                                    "Assalamualaikum, anak anda sudah tiba di Madrasah pada **JAM MASUK**. Klik link berikut untuk melihat detailnya \nLink: ${face.data.url}"
                                }else{
                                    "Assalamualaikum, anak anda sudah **MENINGGALKAN** Madrasah pada **JAM PULANG**. Klik link berikut untuk melihat detailnya \nLink: ${face.data.url}"
                                }
                                if(!face.data.success && face.data.code == 400){
                                    Help.showToast(this@AttendanceStudent,face.data.message)
                                    sendWhatsAppMessage(it.numberPhoneParent!!,message)
                                    Log.d(TAG, "addAttendanceStudent: ${face.data.message},${face.data.success}")

                                }else if(!face.data.success && face.data.code == 404){
                                    Help.showToast(this@AttendanceStudent,face.data.message)
                                }else if(!face.data.success && face.data.code == 600){
                                    Help.showToast(this@AttendanceStudent,face.data.message)
                                }else{
                                    val attendanceAdd = face.data.attendance.toAttendance()
                                    sendWhatsAppMessage(it.numberPhoneParent!!,message)
                                    attendanceAdd.timeIn.let { it2 ->
                                        allViewModel.setTimeIn(it2)
                                    }
                                    attendanceAdd.timeOut.let { it2 ->
                                        allViewModel.setTimeOut(it2)
                                    }
                                    Help.showToast(this@AttendanceStudent,face.data.message)
                                    allViewModel.setAllAttendanceMonth(it1)
                                }
                            }
                            is States.Failed -> {
                                Help.showToast(this@AttendanceStudent,face.message)
                                Log.d(TAG, "addAttendanceStudent: failed -> ${face.message}")}
                        }
                    }
                }
            }
        }
    }

    private fun sendWhatsAppMessage(numberParentStudent: String,message: String)
    {
       val url = "https://wa.me/$numberParentStudent?text=${message}"
       val intent = Intent(Intent.ACTION_VIEW).apply {
           data = Uri.parse(url)
       }
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "WhatsApp tidak ditemukan: ${e.message}")
            Help.showToast(this, "WhatsApp tidak terinstal di perangkat ini")
        }
    }

    private fun setLocationUser(){
        Dexter.withContext(this@AttendanceStudent).withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if(p0?.areAllPermissionsGranted() == true)
                {
                    locationUser()
                }else if(p0?.isAnyPermissionPermanentlyDenied == true){
                    showSettingDialog()
                }else{
                    showPermissionDeniedDialog()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                showPermissionRationaleDialog(p1!!)
            }

        }).onSameThread().check()
    }

    private fun showPermissionRationaleDialog(token: PermissionToken)
    {
        MaterialAlertDialogBuilder(this@AttendanceStudent)
            .setTitle("Izin dibutuhkan")
            .setMessage("Aplikasi ini memerlukan izin lokasi untuk berfungsi. Mohon berikan izin tersebut")
            .setPositiveButton("Izin"){_,_ ->
                token.continuePermissionRequest()
            }
            .setNegativeButton("Tidak"){dialog, _ ->
                dialog.dismiss()
                token.cancelPermissionRequest()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun showPermissionDeniedDialog(){
        MaterialAlertDialogBuilder(this@AttendanceStudent)
            .setTitle("Izin di tolak")
            .setMessage("Izin lokasi diperlukan agar aplikasi ini dapat berfungsi. Harap berikan izin tersebut")
            .setNegativeButton("Coba lagi"){_,_ ->
                setLocationUser()
            }
            .setNegativeButton("Tidak"){dialog,_ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun showSettingDialog()
    {
        MaterialAlertDialogBuilder(this@AttendanceStudent)
            .setTitle("Membutuhkan Izin")
            .setMessage("Aplikasi ini memerlukan izin lokasi untuk berfungsi. " +
                    "Anda dapat memberikan izin tersebut di pengaturan aplikasi")
            .setNegativeButton("Pergi ke Setting"){dialog,_ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package",packageName,null)
                intent.data = uri
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Tidak"){dialog,_ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double){
        val geoCoder = Geocoder(this@AttendanceStudent,Locale.getDefault())
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            geoCoder.getFromLocation(latitude,longitude,1){list ->
                if (list.size != 0){
                    val addressName = list[0]
                    val thoroughfare = addressName.thoroughfare // Jalan
                    val subThoroughfare = addressName.subThoroughfare // Nomor rumah
                    val locality = addressName.locality // Kecamatan

                    val formattedAddress = "$thoroughfare $subThoroughfare, $locality"
                    attendanceBinding.mtvLocation.text = formattedAddress
                    Log.d(TAG, "getAddressFromLocation: $formattedAddress")
                }
            }
        }else{
            try {
                val list = geoCoder.getFromLocation(latitude,longitude,1)
                if (list != null && list.size != 0){
                    val addressName = list[0].getAddressLine(0)
                    attendanceBinding.mtvLocation.text = addressName
                    Log.d(TAG, "getAddressFromLocation: $addressName")
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun setLayoutManager()
    {
        attendanceBinding.rvAttendanceAllToday.apply {
            layoutManager = LinearLayoutManager(this@AttendanceStudent,LinearLayoutManager.HORIZONTAL,false)
            adapter = adapterAttendance
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentDate()
    {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateNow = dateFormat.format(calendar.time)
        attendanceBinding.mtvDateToday.text = "Tanggal: ${dateNow}"
    }

    @SuppressLint("MissingPermission")
    private fun locationUser()
    {
        lifecycleScope.launch {
            localStore.getToken().collect{model ->
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let { data ->
                        allViewModel.getLatAndLong(model.token!!).observe(this@AttendanceStudent){locationApi -> 
                            when(locationApi)
                            {
                                is States.Success -> {
                                    val lat = locationApi.data.latitude
                                    val long = locationApi.data.longitude
                                    val distance = FloatArray(1)
                                    Location.distanceBetween(data.latitude,data.longitude,lat,long,distance)
                                    val radius = 150.0
                                    val distanceInMeter = distance[0]
                                    Log.d(TAG, "locationUser: Jarak antara lokasi saya dengan lokasi sebelah ${distanceInMeter} meter")
                                    if(distance[0] < radius)
                                    {
                                        attendanceBinding.scanFace.apply {
                                            isEnabled = true
                                            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(
                                                this@AttendanceStudent, R.color.mangold
                                            ))
                                        }
                                    }else{
                                        attendanceBinding.scanFace.apply {
                                            isEnabled = false
                                            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(
                                                this@AttendanceStudent, R.color.inactivate
                                            ))
                                        }
                                    }
                                }
                                is States.Failed -> Help.showToast(this@AttendanceStudent, locationApi.message)
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates()
    {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,10000
        ).apply { setMinUpdateIntervalMillis(50000) }.build()
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates()
    {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }
}