package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.R
import com.example.man2superapp.databinding.ItemSongketMotherStatusBinding
import com.example.man2superapp.source.local.model.SongketMotherGTK

class SongketMotherAdapterGtk(
    private val onEdit: (SongketMotherGTK) -> Unit,
    private val onAddGtk: (SongketMotherGTK) -> Unit
): RecyclerView.Adapter<SongketMotherAdapterGtk.ViewHolder>()
{

    private val listSongketMotherGtk = ArrayList<SongketMotherGTK>()

    inner class ViewHolder(private val binding: ItemSongketMotherStatusBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: SongketMotherGTK,context: Context)
        {
            with(binding)
            {
                changeSongketMother.setOnClickListener {
                    onEdit(data)
                }

                addService.setOnClickListener {
                    onAddGtk(data)
                }

                val letterConfig = mapOf(
                    1 to Pair(
                        "Surat Keterangan Cuti Tahunan", listOf(
                            averageValue,
                            universityAndMajor,
                            majorStudent,
                            nameExtracurricular,
                            nameClub,
                        )
                    ),
                    2 to Pair(
                        "Surat Keterangan Aktif Mengajar", listOf(
                            rankingSemester,
                            totalStudent,
                            majorStudent,
                            nameExtracurricular,
                            nameClub,
                        )
                    ),
                    3 to Pair(
                        "Surat Rekomendasi", listOf(
                            averageValue,
                            rankingSemester,
                            totalStudent,
                            averageValue,
                        )
                    )
                )
                // Ambil Konfigurasi berdasarkan letterStatement
                val config = letterConfig[data.letterStatement]
                // Update Judul
                mtvTitle.text = config?.first ?: "Unknown Letter Statement"

                val elementsToHide = config?.second ?: emptyList()

                listOf(
                    nameActivityCompletition,organizerCompletition,rankingSemester,totalStudent,
                    averageValue,universityAndMajor,majorStudent,nameExtracurricular,
                ).forEach { view ->
                    view.visibility = if(view in elementsToHide) View.GONE else View.VISIBLE
                }

                val statusText = when(data.status){
                    1 -> "Antrian"
                    2 -> "Diajukan ke Kepala Madrasah"
                    3 -> "Disetujui oleh Kepala Madrasah"
                    4 -> "Selesai"
                    99 -> "Tolak"
                    else -> "Status tidak diketahui"
                }

                if(data.status == 99)
                {
                    changeSongketMother.visibility = View.VISIBLE
                }

                if(data.status == 3)
                {
                    addService.visibility = View.VISIBLE
                }

                val statusColor = when(data.status){
                    1 -> ContextCompat.getColor(context, R.color.card_queue)
                    2 -> ContextCompat.getColor(context,R.color.manblue)
                    3 -> ContextCompat.getColor(context,R.color.card_accepted_leader)
                    4 -> ContextCompat.getColor(context,R.color.card_accepted)
                    99 -> ContextCompat.getColor(context,R.color.card_rejected)
                    else -> android.graphics.Color.GRAY
                }
                nameClub.visibility = View.GONE
                mtvStatus.setTextColor(statusColor)
                mtvStatus.text = "Status: $statusText"
                nameActivityCompletition.text = "Pangkat/Golongan: ${data.rankOrGrade?: "-"}"
                organizerCompletition.text = "NIP: ${data.nip?: "-"}"
                rankingSemester.text = "Mulai Cuti: ${data.startHoliday}"
                totalStudent.text = "Akhir Cuti: ${data.endHoliday}"
                averageValue.text = "NUPTK: ${data.nuptk}"
                universityAndMajor.text = "Bidang Studi / Mata Pelajaran: ${data.fieldStudy}"
                majorStudent.text = "Pernha Mengajar Mata Pelajaran: ${data.haveYourEverTaughtSubject}"
                nameExtracurricular.text = "Rekomendasi: ${data.recommendationTitle}"
                numberLetter.text = "Nomor Surat: ${data.numberLetter}"
            }
        }
    }

    /*
    nameActivityCompletition, // Pangkat Atau Golongan
    organizerCompletition, // NIP Karyawan
    rankingSemester, // Tanggal Mulai Cuti
    total_student, // Tanggal Akhir Cuti
    averageValue, // NUPTK,
    nameUniversity => Bidang Studi / Mata pelajaran
    majorStudent => Pernah Mengajar Mata pelajaran
    nameExtracuriciller, => Rekomendasi
     */

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongketMotherAdapterGtk.ViewHolder {
        return ViewHolder(ItemSongketMotherStatusBinding.inflate(LayoutInflater.from(parent.context),parent
        ,false))
    }

    override fun onBindViewHolder(holder: SongketMotherAdapterGtk.ViewHolder, position: Int) {
        holder.bind(listSongketMotherGtk[position],holder.itemView.context)
    }

    override fun getItemCount(): Int = listSongketMotherGtk.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(songketMotherGtk: List<SongketMotherGTK>)
    {
        listSongketMotherGtk.clear()
        listSongketMotherGtk.addAll(songketMotherGtk)
        notifyDataSetChanged()
    }

}
