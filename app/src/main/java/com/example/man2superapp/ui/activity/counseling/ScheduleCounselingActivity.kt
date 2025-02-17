package com.example.man2superapp.ui.activity.counseling

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.man2superapp.databinding.ActivityCounselingBinding
import com.example.man2superapp.source.LoginTemp
import com.example.man2superapp.ui.adapter.ScheduleCounselingAdapter
import com.example.man2superapp.ui.presenter.AllViewModel
import com.example.man2superapp.utils.Help
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleCounselingActivity: AppCompatActivity()
{

    private lateinit var scheduleBinding: ActivityCounselingBinding
    @Inject
    lateinit var localStore: LoginTemp
    private val allViewModel by viewModels<AllViewModel>()
    private val adapterScheduleCounseling by lazy { ScheduleCounselingAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleBinding = ActivityCounselingBinding.inflate(layoutInflater)
        setContentView(scheduleBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ user ->
                user.token?.let { allViewModel.fetchAllScheduleCounseling(it) }
            }
        }
        scheduleBinding.apply {
            btnCardSeeSchedule.visibility = View.GONE
            mtvDate.text = setCurrentDate()
            backButton.setOnClickListener { finish() }
        }
        observerView()
        setUpRecyclerView()
    }

    private fun setCurrentDate(): String
    {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM YYYY", Locale("id","ID"))
        val currentDate = dateFormat.format(Date())
        return currentDate
    }

    private fun setUpRecyclerView()
    {
        scheduleBinding.rvItemSessionCounseling.apply {
            layoutManager = LinearLayoutManager(this@ScheduleCounselingActivity,LinearLayoutManager.VERTICAL,false)
            adapter = adapterScheduleCounseling
        }
    }

    private fun observerView()
    {
        allViewModel.loading.observe(this@ScheduleCounselingActivity){showLoading(it)}
        allViewModel.allScheduleCounseling.observe(this@ScheduleCounselingActivity){
            if (it.isEmpty())
            {
                showTextEmpty(true)
            }else{
                adapterScheduleCounseling.listUpdate(it)
                showTextEmpty(false)
            }
        }
        allViewModel.message.observe(this@ScheduleCounselingActivity){
            Help.showToast(this@ScheduleCounselingActivity,it)
        }
    }

    private fun showTextEmpty(isShow: Boolean)
    {
        scheduleBinding.apply {
            rvItemSessionCounseling.visibility = if (isShow) View.GONE else View.VISIBLE
            mtvTextEmpty.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    private fun showLoading(isShow: Boolean)
    {
        scheduleBinding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            rvItemSessionCounseling.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }
}