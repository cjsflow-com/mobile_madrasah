package com.example.man2superapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemSessionCounselingBinding
import com.example.man2superapp.source.local.model.LocalCounselingSession
import com.example.man2superapp.source.local.model.LocalSchedule

class SessionCounselingAdapter: RecyclerView.Adapter<SessionCounselingAdapter.ViewHolder>()
{

    inner class ViewHolder(private val binding: ItemSessionCounselingBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(counselingSession: LocalCounselingSession)
        {
            with(binding)
            {
                mtvStatus.visibility = View.GONE
                nameCounselor.visibility = View.GONE
                mtvDateCounseling.visibility = View.GONE
                mtvNameCounseling.text = "Sessi Konseling: ${counselingSession.counselingName}"
                timeCounseling.text = "Waktu Konseling: ${counselingSession.startTime} - ${counselingSession.endTime}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionCounselingAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SessionCounselingAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}