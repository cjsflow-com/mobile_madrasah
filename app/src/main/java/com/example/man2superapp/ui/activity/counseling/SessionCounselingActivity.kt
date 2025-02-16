package com.example.man2superapp.ui.activity.counseling

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.man2superapp.databinding.ActivityCounselingBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.source.local.model.LocalCounselingSession
import com.example.man2superapp.ui.adapter.SessionCounselingAdapter
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SessionCounselingActivity: AppCompatActivity()
{
    private lateinit var counselingSessionBinding: ActivityCounselingBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private val adapterSessionCounseling by lazy { SessionCounselingAdapter(::onBookingNow) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        counselingSessionBinding = ActivityCounselingBinding.inflate(layoutInflater)
        setContentView(counselingSessionBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ student ->
                student.token?.let { showApiFromService(it) }
            }
        }
        observerView()
        counselingSessionBinding.apply {
            mtvDate.text = setCurrentDate()
            backButton.setOnClickListener { finish() }
            btnCardSeeSchedule.setOnClickListener {
                startActivity(Intent(this@SessionCounselingActivity,ScheduleCounselingActivity::class.java))
            }
        }
        setAdapterRecyclerView()
    }

    private fun setCurrentDate(): String
    {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM YYYY",Locale("id","ID"))
        val currentDate = dateFormat.format(Date())
        return currentDate
    }

    private fun setAdapterRecyclerView()
    {
        counselingSessionBinding.rvItemSessionCounseling.apply {
            layoutManager = LinearLayoutManager(this@SessionCounselingActivity,LinearLayoutManager.VERTICAL,false)
            adapter = adapterSessionCounseling
        }
    }

    private fun showApiFromService(token: String)
    {
        allViewModel.fetchAllCounseling(token)
    }

    private fun observerView()
    {
        allViewModel.loading.observe(this@SessionCounselingActivity){showLoading(it)}
        allViewModel.message.observe(this@SessionCounselingActivity){
            Help.showToast(this@SessionCounselingActivity,it)
        }
        allViewModel.allSessionCounseling.observe(this@SessionCounselingActivity){
            if (it.isEmpty())
            {
               showTextEmpty(true)
            }else{
                adapterSessionCounseling.submittedList(it)
                showTextEmpty(false)
            }
        }
    }

    private fun showTextEmpty(isShow: Boolean)
    {
        counselingSessionBinding.apply {
            rvItemSessionCounseling.visibility = if (isShow) View.GONE else View.VISIBLE
            mtvTextEmpty.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    private fun showLoading(isShow: Boolean)
    {
        with(counselingSessionBinding)
        {
            progressBar.visibility = if(isShow) View.VISIBLE else View.GONE
            btnCardSeeSchedule.visibility = if (isShow) View.GONE else View.VISIBLE
            rvItemSessionCounseling.visibility = if (isShow) View.GONE else View.VISIBLE
            mtvTextEmpty.visibility = if (isShow) View.GONE else View.GONE
        }
    }

    private fun onBookingNow(localSessionCounseling: LocalCounselingSession)
    {
        Intent(this@SessionCounselingActivity,AddCounselingActivity::class.java).apply {
            putExtra(Constant.ID_COUNSELING,localSessionCounseling.id)
            putExtra(Constant.NAME_COUNSELING_SESSION,localSessionCounseling.counselingName)
        }.also { startActivity(it) }
    }
}