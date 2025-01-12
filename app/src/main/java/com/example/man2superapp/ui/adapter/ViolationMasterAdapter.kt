package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemViolationMasterBinding
import com.example.man2superapp.source.local.model.LocalViolationMaster

class ViolationMasterAdapter: RecyclerView.Adapter<ViolationMasterAdapter.ViewHolder>()
{

    private val listViolationMaster = ArrayList<LocalViolationMaster>()

    inner class ViewHolder(private val binding: ItemViolationMasterBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalViolationMaster){
            with(binding)
            {
                mtvTitle.text = "Aturan: ${data.nameSchoolViolation}"
                mtvPoint.text = "Poin: ${data.point}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViolationMasterAdapter.ViewHolder = ViewHolder(
        ItemViolationMasterBinding.inflate(LayoutInflater.from(parent.context),parent
        ,false)
    )

    override fun onBindViewHolder(holder: ViolationMasterAdapter.ViewHolder, position: Int) {
        holder.bind(listViolationMaster[position])
    }

    override fun getItemCount(): Int = listViolationMaster.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(itemViolationMaster: List<LocalViolationMaster>)
    {
        listViolationMaster.clear()
        listViolationMaster.addAll(itemViolationMaster)
        notifyDataSetChanged()
    }

}