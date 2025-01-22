package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemAttendanceBinding
import com.example.man2superapp.source.local.model.LocalAttendance
import com.example.man2superapp.source.local.model.LocalStudent
import okhttp3.internal.notifyAll

class AttendanceAdapter: RecyclerView.Adapter<AttendanceAdapter.ViewHolder>()
{

    inner class ViewHolder(private val binding: ItemAttendanceBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalAttendance){
            with(binding)
            {
                mtvContentCheckInTime.text = data.timeIn?: "?"
                mtvContentCheckOutTime.text = data.timeOut?: "?"
                mtvContentStatusIn.text = data.statusIn?: "?"
                mtvContentStatusOut.text = data.statusOut?: "?"
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
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this, object: DiffUtil.ItemCallback<LocalAttendance>(){
        override fun areItemsTheSame(oldItem: LocalAttendance, newItem: LocalAttendance): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocalAttendance,
            newItem: LocalAttendance
        ): Boolean {
           return  oldItem == newItem
        }

    })

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(listAttendanceAttendance: List<LocalAttendance>){
        differ.submitList(listAttendanceAttendance)
    }

}