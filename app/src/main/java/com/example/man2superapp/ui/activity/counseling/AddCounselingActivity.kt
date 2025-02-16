package com.example.man2superapp.ui.activity.counseling

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.man2superapp.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.man2superapp.databinding.ActivityAddScheduleCounselingBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddCounselingActivity: AppCompatActivity()
{
    private lateinit var addCounselingBinding: ActivityAddScheduleCounselingBinding
    private val allViewModel by viewModels<AllViewModel>()
    @Inject
    lateinit var localStore: LoginTemp
    private var counselorMap: Map<String, Int>? = null
    private var idCounseling: Int = -1
    private var nameCounseling: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCounselingBinding = ActivityAddScheduleCounselingBinding.inflate(layoutInflater)
        setContentView(addCounselingBinding.root)
        nameCounseling = intent.getStringExtra(Constant.NAME_COUNSELING_SESSION) ?: "Tidak ditemukan"
        idCounseling = intent.getIntExtra(Constant.ID_COUNSELING,-1)
        lifecycleScope.launch {
            localStore.getToken().collect{ student ->
                student.token?.let {
                    allViewModel.fetchAllCounselor(it)
                    actionSubmit(it)
                }
            }
        }
        observerView()
    }

    private fun actionSubmit(token: String)
    {
        addCounselingBinding.apply {
            backButton.setOnClickListener { finish() }
            etCounselingName.setText(nameCounseling)
            etCounselingName.isEnabled = false
            btnSubmit.setOnClickListener {
                val sessionCounselingName = parentSessionCounseling.editText?.text?.trim().toString()
                val nameCounselor = parentCounselor.editText?.text.toString()
                val dateCounseling = parentDateCounseling.editText?.text.toString()
                if (sessionCounselingName.isEmpty() || nameCounselor.isEmpty() || dateCounseling.isEmpty())
                {
                    Help.showToast(this@AddCounselingActivity,getString(R.string.validation_form))
                }else{
                    val formattedDate = formatDateToStandard(dateCounseling)
                    val counselorId = counselorMap?.get(nameCounselor)
                    counselorId?.let { allViewModel.createScheduleCounseling(token,idCounseling,formattedDate,counselorId) }
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean)
    {
        addCounselingBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnSubmit.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun getUpWeekDates(): List<String>{
        val dateList = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id","ID"))
        val calendar = Calendar.getInstance()

        calendar.add(Calendar.DATE,1)

        for (i in 0 until 6)
        {
            val date = calendar.time
            dateList.add(dateFormat.format(date))
            calendar.add(Calendar.DATE,1)
        }
        return dateList
    }

    private fun formatDateToStandard(date: String): String {
        val inputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id","ID"))  // Format input dari AutoCompleteTextView
        val outputFormat = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale("id","ID")
        )  // Format yang diinginkan (tanggal-bulan-tahun)

        return try {
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate ?: Date())  // Mengubah ke format yang diinginkan
        } catch (e: ParseException) {
            e.printStackTrace()
            date  // Jika gagal, kembalikan tanggal asli
        }
    }

    private fun observerView()
    {
        allViewModel.allCounselor.observe(this@AddCounselingActivity){counselorList ->
            val counselorMap = counselorList.associateBy({it.name},{it.id})
            val adapter = ArrayAdapter(
                this@AddCounselingActivity,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                counselorList.map { it.name }
            )
            addCounselingBinding.etCounselor.setAdapter(adapter)
            this@AddCounselingActivity.counselorMap = counselorMap
        }
        val dateList = getUpWeekDates()
        val adapter = ArrayAdapter(this@AddCounselingActivity, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,dateList)
        addCounselingBinding.etDateCounseling.setAdapter(adapter)
        allViewModel.textSuccess.observe(this@AddCounselingActivity){Help.showToast(this@AddCounselingActivity,it).also { finish() }}
        allViewModel.message.observe(this@AddCounselingActivity){Help.showToast(this@AddCounselingActivity,it)}
        allViewModel.loading.observe(this@AddCounselingActivity){showLoading(it)}
    }
}