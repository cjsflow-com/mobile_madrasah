package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemEkinerjaBinding
import com.example.man2superapp.source.local.model.ResutlEmployeePerformance

class ResultEkinerjaAdapter(
    private val onDetail: (ResutlEmployeePerformance) -> Unit,
    private val onEdit: (ResutlEmployeePerformance) -> Unit,
    private val onAdd: (ResutlEmployeePerformance) -> Unit
): RecyclerView.Adapter<ResultEkinerjaAdapter.ViewHolder>()
{

    private val listResultEkinerja = ArrayList<ResutlEmployeePerformance>()

    inner class ViewHolder(private val binding: ItemEkinerjaBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: ResutlEmployeePerformance){
            with(binding)
            {
                nameTask.text = "Tupoksi: ${data.taskName}"
                nameAction.text = "Rencana Aksi: ${data.performanceEvaluationPlan}"
                period.text = "Target Laporan Triwulan: ${data.performanceTargetEvaluation} Laporan"
                triwulanEkinerja.text = "Triwulan: ${data.triwulan}"

                if(data.linkGoogleDrive == "" || data.nameDataSupport == "" )
                {
                    btnAddRealitation.visibility = View.VISIBLE
                }else{
                    btnAddRealitation.visibility = View.GONE
                }

                if (data.predicateWork == "?" && data.ratingEvaluationWork == "?")
                {
                    btnEdit.visibility = View.VISIBLE
                }else{
                    btnEdit.visibility = View.GONE
                }

                btnDetail.setOnClickListener {
                    onDetail(data)
                }
                btnEdit.setOnClickListener {
                    onEdit(data)
                }
                btnAddRealitation.setOnClickListener {
                    onAdd(data)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultEkinerjaAdapter.ViewHolder = ViewHolder(
        ItemEkinerjaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ResultEkinerjaAdapter.ViewHolder, position: Int) {
        holder.bind(listResultEkinerja[position])
    }

    override fun getItemCount(): Int  = listResultEkinerja.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(resultEKinerja: List<ResutlEmployeePerformance>)
    {
        listResultEkinerja.clear()
        listResultEkinerja.addAll(resultEKinerja)
        notifyDataSetChanged()
    }

}