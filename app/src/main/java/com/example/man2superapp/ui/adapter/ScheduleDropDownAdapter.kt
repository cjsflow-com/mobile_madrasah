package com.example.man2superapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.man2superapp.databinding.ItemScheduleDropdownBinding
import com.example.man2superapp.source.local.model.LocalResultScheduleCounseling

class ScheduleDropDownAdapter(
    private val context: Context,
    private val schedules: List<LocalResultScheduleCounseling>,
): ArrayAdapter<LocalResultScheduleCounseling>(context,0,schedules)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View{
        val binding = ItemScheduleDropdownBinding.inflate(LayoutInflater.from(context),parent,false)
        val item = schedules[position]
        binding.tvSchedule.text = item.date

        val color = if (item.status) Color.RED else Color.BLACK

        binding.tvSchedule.setTextColor(color)

        return binding.root
    }
}