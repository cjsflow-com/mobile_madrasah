package com.example.man2superapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.callback.BaseDiffCallback
import com.example.man2superapp.databinding.ItemSessionCounselingBinding
import com.example.man2superapp.source.local.model.LocalCounselingSession
import com.example.man2superapp.source.local.model.LocalSchedule

class SessionCounselingAdapter(
    private val onBookingNow: (LocalCounselingSession) -> Unit
): RecyclerView.Adapter<SessionCounselingAdapter.ViewHolder>()
{

    private var counselingSessionList = listOf<LocalCounselingSession>()

    inner class ViewHolder(private val binding: ItemSessionCounselingBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(counselingSession: LocalCounselingSession)
        {
            with(binding)
            {
                mtvStatus.visibility = View.GONE
                nameCounselor.visibility = View.GONE
                mtvDateCounseling.visibility = View.GONE
                mtvNameStudent.visibility = View.GONE
                mtvNameCounseling.text = "Sessi Konseling: ${counselingSession.counselingName}"
                timeCounseling.text = "Waktu Konseling: ${counselingSession.startTime} - ${counselingSession.endTime}"
                bookingNow.setOnClickListener {
                    onBookingNow(counselingSession)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionCounselingAdapter.ViewHolder =
        ViewHolder(ItemSessionCounselingBinding.inflate(LayoutInflater.from(parent.context),parent
        ,false))

    override fun onBindViewHolder(holder: SessionCounselingAdapter.ViewHolder, position: Int) {
        holder.bind(counselingSessionList[position])
    }

    override fun getItemCount(): Int = counselingSessionList.size

    fun submittedList(newLists: List<LocalCounselingSession>){
        val diffCallback = BaseDiffCallback(oldList = counselingSessionList, newList = newLists
        , areItemsSame = {oldItem, newItem -> oldItem.id == newItem.id}, areContentSame = {oldItem, newItem -> oldItem == newItem})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        counselingSessionList = newLists
        diffResult.dispatchUpdatesTo(this)
    }
}