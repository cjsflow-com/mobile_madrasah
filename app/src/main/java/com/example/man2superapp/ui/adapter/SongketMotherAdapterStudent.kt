package com.example.man2superapp.ui.adapter

import android.annotation.SuppressLint
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.man2superapp.databinding.ItemSongketMotherStatusBinding
import com.example.man2superapp.source.local.model.ListSongketMother

class SongketMotherAdapterStudent(
    private val onEdit: (ListSongketMother) -> Unit
): RecyclerView.Adapter<SongketMotherAdapterStudent.ViewHolder>()
{

    private val listSongketMother = ArrayList<ListSongketMother>()

    inner class ViewHolder(private val binding: ItemSongketMotherStatusBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListSongketMother) {
            with(binding) {
                changeSongketMother.setOnClickListener {
                    onEdit(data)
                }
                // Map konfigurasi berdasarkan letterStatement
                val letterConfig = mapOf(
                    1 to Pair(
                        "Surat Keterangan Aktif Sekolah", listOf(
                            nameActivityCompletition,
                            organizerCompletition,
                            universityAndMajor,
                            majorStudent,
                            nameClub,
                            nameExtracurricular,
                            rankingSemester,
                            averageValue,
                            totalStudent,
                            semester,
                        )
                    ),
                    2 to Pair(
                        "Surat Keterangan Berkelakuan Baik", listOf(
                            nameActivityCompletition,
                            organizerCompletition,
                            universityAndMajor,
                            majorStudent,
                            nameClub,
                            nameExtracurricular,
                            rankingSemester,
                            averageValue,
                            totalStudent,
                            semester,
                        )
                    ),
                    3 to Pair(
                        "Surat Keterangan Ekskul/Club", listOf(
                            nameActivityCompletition,
                            organizerCompletition,
                            rankingSemester,
                            universityAndMajor,
                            averageValue,
                            majorStudent,
                            semester,
                            totalStudent
                        )
                    ),
                    4 to Pair(
                        "Surat Keterangan Lomba", listOf(
                            rankingSemester, universityAndMajor, nameClub, nameExtracurricular,
                            majorStudent,semester,totalStudent,averageValue
                        )
                    ),
                    5 to Pair(
                        "Surat Keterangan Peringkat", listOf(
                            nameActivityCompletition,
                            organizerCompletition,
                            nameClub,
                            nameExtracurricular,
                            universityAndMajor,
                            majorStudent,
                        )
                    ),
                    6 to Pair(
                        "Surat Keterangan Universitas", listOf(
                            nameActivityCompletition,
                            organizerCompletition,
                            nameClub,
                            nameExtracurricular,
                            rankingSemester,
                            semester,
                            totalStudent,
                            averageValue,
                        )
                    )
                )

                // Ambil konfigurasi berdasarkan letterStatement
                val config = letterConfig[data.letterStatement]

                // Update judul
                mtvTitle.text = config?.first ?: "Unknown Letter Statement"

                // Sembunyikan elemen-elemen sesuai konfigurasi
                val elementsToHide = config?.second ?: emptyList()
                listOf(
                    nameActivityCompletition, organizerCompletition, rankingSemester,
                    universityAndMajor, nameClub, nameExtracurricular, totalStudent, averageValue,
                    majorStudent,

                ).forEach { view ->
                    view.visibility = if (view in elementsToHide) View.GONE else View.VISIBLE
                }
                val statusText = when(data.status){
                    1 -> "Antrian"
                    2 -> "Setuju"
                    99 -> "Tolak"
                    else -> "Status tidak diketahui"
                }

                if(data.status == 99){
                    changeSongketMother.visibility = View.VISIBLE
                }

                val statusColor = when(data.status){
                    1 -> android.graphics.Color.YELLOW
                    2 -> android.graphics.Color.GREEN
                    99 -> android.graphics.Color.RED
                    else -> android.graphics.Color.GRAY
                }
                if(data.nameClub == "" || data.nameExtracurricular == "") {
                    nameClub.visibility = View.GONE
                    nameExtracurricular.visibility = View.GONE
                }
                mtvStatus.text = "Status: $statusText"
                mtvStatus.setTextColor(statusColor)
                nameActivityCompletition.text = "Nama Lomba: ${data.nameActivityCompletition}"
                organizerCompletition.text = "Penyelenggara: ${data.organizerCompletition}"
                rankingSemester.text = "Semester: ${data.ranking}"
                universityAndMajor.text = "Nama Universitas: ${data.nameUniversity}"
                nameExtracurricular.text = "Ekstrakurikuller: ${data.nameExtracurricular}"
                nameClub.text = "Klub : ${data.nameClub}"
                majorStudent.text = "Jurusan: ${data.major}"
                semester.text = "Semester : ${data.semester}"
                averageValue.text = "Nilai Rata Rata: ${data.averageValue.toString()}"
                totalStudent.text = "Total Siswa: ${data.totalStudent}"
                numberLetter.text = "Nomor Surat: ${data.numberLetter?: "?"}"
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongketMotherAdapterStudent.ViewHolder {
        return ViewHolder(ItemSongketMotherStatusBinding.inflate(LayoutInflater.from(parent.context),parent
        ,false))
    }

    override fun onBindViewHolder(holder: SongketMotherAdapterStudent.ViewHolder, position: Int) {
       holder.bind(listSongketMother[position])
    }

    override fun getItemCount(): Int = listSongketMother.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(listSongketMotherStudent: List<ListSongketMother>)
    {
        listSongketMother.clear()
        listSongketMother.addAll(listSongketMotherStudent)
        notifyDataSetChanged()
    }

}