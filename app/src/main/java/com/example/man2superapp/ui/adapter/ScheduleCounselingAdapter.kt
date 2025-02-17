package com.example.man2superapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.R
import com.example.man2superapp.callback.BaseDiffCallback
import com.example.man2superapp.databinding.ItemSessionCounselingBinding
import com.example.man2superapp.source.local.model.LocalSchedule
import com.example.man2superapp.utils.Help

class ScheduleCounselingAdapter: RecyclerView.Adapter<ScheduleCounselingAdapter.ViewHolder>()
{


    private var scheduleCounselingList = listOf<LocalSchedule>()

    inner class ViewHolder(private val binding: ItemSessionCounselingBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalSchedule,context: Context)
        {
            with(binding)
            {
                mtvNameCounseling.text = "Sesi: ${data.sessionName}"
                nameCounselor.text = "Nama Konselor: ${data.counselorName}"
                timeCounseling.text = "Waktu: ${data.time}"
                mtvDateCounseling.text = "Tanggal: ${Help.formatDate(data.dateCounseling)}"
                val statusColor = when(data.status){
                    "antrian" -> ContextCompat.getColor(context, R.color.manblue)
                    "proses" -> ContextCompat.getColor(context,R.color.mangold)
                    "selesai" -> ContextCompat.getColor(context,R.color.card_accepted)
                    "tolak" -> ContextCompat.getColor(context,R.color.card_rejected)
                    else -> ContextCompat.getColor(context,R.color.card_container_text_clock)
                }
                mtvStatus.setTextColor(statusColor)
                mtvStatus.text = "Status: ${data.status}"
                bookingNow.visibility = View.GONE
                mtvNameStudent.text = "Nama Siswa: ${data.studentName}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleCounselingAdapter.ViewHolder = ViewHolder(
        ItemSessionCounselingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ScheduleCounselingAdapter.ViewHolder, position: Int) {
        holder.bind(scheduleCounselingList[position],holder.itemView.context)
    }

    override fun getItemCount(): Int = scheduleCounselingList.size

    fun listUpdate(newLists: List<LocalSchedule>)
    {
        val diffCallback = BaseDiffCallback(oldList = scheduleCounselingList, newList = newLists
        , areItemsSame = {oldItem, newItem -> oldItem.id == newItem.id}, areContentSame = {oldItem, newItem -> oldItem == newItem})
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        scheduleCounselingList = newLists
        diffResult.dispatchUpdatesTo(this)
    }

}