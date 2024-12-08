package com.example.man2superapp.source.local.model

import android.os.Parcelable
import com.example.man2superapp.source.network.response.songket_emak.SongketEmak
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListSongketMother(
    var id: Int,
    var letterStatement: Int,
    var nameActivityCompletition: String,
    var nameClub: String,
    var nameExtracurricular : String,
    var organizerCompletition: String,
    var status: Int,
    var nameUniversity: String,
    var major: String,
    var ranking: String,
    var semester: String,
    var totalStudent: String,
    var averageValue: Double,
    var numberLetter: String
):Parcelable

fun List<SongketEmak>.toGenerateListSongketMother(): MutableList<ListSongketMother>{
    val listSongketMother = mutableListOf<ListSongketMother>()
    this.forEach { listSongketMother.add(it.toSongketMother()) }
    return listSongketMother
}

fun SongketEmak.toSongketMother(): ListSongketMother{
    return ListSongketMother(
        id = this.id?: 0,
        letterStatement = this.letterStatement?: 0,
        nameActivityCompletition = this.nameActivityCompletition?: "?",
        nameClub = this.nameClub?: "?",
        nameExtracurricular = this.nameExtracurricular?: "?",
        organizerCompletition = this.organizerCompletition?: "?",
        status = this.status?: 0,
        nameUniversity = this.nameUniversity?: "",
        major = this.major?: "",
        ranking = this.ranking?: "",
        semester = this.semester?: "",
        totalStudent = this.totalStudent?: "",
        averageValue = this.averageValue?: 0.0,
        numberLetter = this.numberLetter?: "?",
    )
}