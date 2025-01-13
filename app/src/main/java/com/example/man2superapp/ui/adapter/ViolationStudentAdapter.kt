package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ItemListStudentViolationBinding
import com.example.man2superapp.source.local.model.LocalSchoolViolationStudent

class ViolationStudentAdapter(
    private val onAddNoteDisputeViolation: (Int) -> Unit,
    private val onNoteRejectedViolation: (Int) -> Unit,
): RecyclerView.Adapter<ViolationStudentAdapter.ViewHolder>()
{
    private val listViolationStudent = ArrayList<LocalSchoolViolationStudent>()

    inner class ViewHolder(private val binding: ItemListStudentViolationBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalSchoolViolationStudent,context: Context)
        {
            with(binding)
            {
                mtvNameSchoolViolationStudent.text = "Aturan: ${data.nameSchoolViolation}"
                mtvPoint.text = "Point: ${data.point}"
                mtvDate.text = "Tanggal: ${data.dateSchoolViolant}"
                mtvTimeSchool.text = "Waktu: ${data.timeSchoolViolant}"
                mtvNoteViolation.text = "Alasan: ${data.reason}"
                val statusText = when(data.status)
                {
                    1 -> "Antrian"
                    2 -> "Setuju"
                    99 -> "Tolak"
                    else -> "Belum ada status"
                }
                val statusColor = when(data.status){
                    1 -> ContextCompat.getColor(context, R.color.card_queue)
                    2 -> ContextCompat.getColor(context,R.color.card_accepted_leader)
                    99 -> ContextCompat.getColor(context,R.color.card_rejected)
                    else -> android.graphics.Color.GRAY
                }
                mtvViolationDispute.text = "Status: $statusText"
                mtvViolationDispute.setTextColor(statusColor)
                val visibilityView = if(data.reason == "" && data.status == 0){
                    View.GONE
                }else{
                    View.VISIBLE
                }
                btnDisputeViolation.isEnabled = !(data.status == 2 || data.status == 99)
                val visibilityViewBtn = if(data.note == "") View.GONE else View.VISIBLE
                mtvViolationDispute.visibility = visibilityView
                mtvNoteViolation.visibility = visibilityView
                btnNoteRejected.visibility = visibilityViewBtn
                btnDisputeViolation.setOnClickListener {
                    onAddNoteDisputeViolation(data.id)
                }
                btnNoteRejected.setOnClickListener {
                    onNoteRejectedViolation(data.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViolationStudentAdapter.ViewHolder = ViewHolder(
        ItemListStudentViolationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )
    override fun onBindViewHolder(holder: ViolationStudentAdapter.ViewHolder, position: Int) {
        holder.bind(listViolationStudent[position],holder.itemView.context)
    }

    override fun getItemCount(): Int = listViolationStudent.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(itemViolationStudent: List<LocalSchoolViolationStudent>)
    {
        listViolationStudent.clear()
        listViolationStudent.addAll(itemViolationStudent)
        notifyDataSetChanged()
    }
}