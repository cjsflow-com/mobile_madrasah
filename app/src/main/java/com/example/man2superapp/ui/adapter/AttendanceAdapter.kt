package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemAttendanceBinding
import com.example.man2superapp.source.local.model.LocalAttendance
import com.example.man2superapp.source.local.model.LocalStudent
import okhttp3.internal.notifyAll

class AttendanceAdapter: RecyclerView.Adapter<AttendanceAdapter.ViewHolder>()
{

    private val listAttendanceStudent = ArrayList<LocalAttendance>()

    inner class ViewHolder(private val binding: ItemAttendanceBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalAttendance){
            with(binding)
            {
                mtvContentCheckInTime.text = data.timeIn
                mtvContentCheckOutTime.text = data.timeOut
                mtvContentStatusIn.text = data.statusIn
                mtvContentStatusOut.text = data.statusOut
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttendanceAdapter.ViewHolder {
        return ViewHolder(
            ItemAttendanceBinding.inflate(
                android.view.LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.ViewHolder, position: Int) {
        holder.bind(listAttendanceStudent[position])
    }

    override fun getItemCount(): Int = listAttendanceStudent.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(listAttendanceAttendance: List<LocalAttendance>){
        listAttendanceStudent.clear()
        listAttendanceStudent.addAll(listAttendanceAttendance)
        notifyDataSetChanged()
    }

}