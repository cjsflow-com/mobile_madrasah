package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.songket_emak.SongketEmak

data class ListSongketMother(
    var letterStatement: Int,
    var nameActivityCompletition: String,
    var nameClub: String,
    var nameExtracurricular : String,
    var organizerCompletition: String,
    var status: Int,
    var rankingSemester: String,
    var universityAndMajor: String,
    var numberLetter: String
)

fun List<SongketEmak>.toGenerateListSongketMother(): MutableList<ListSongketMother>{
    val listSongketMother = mutableListOf<ListSongketMother>()
    this.forEach { listSongketMother.add(it.toSongketMother()) }
    return listSongketMother
}

fun SongketEmak.toSongketMother(): ListSongketMother{
    return ListSongketMother(
        letterStatement = this.letterStatement?: 0,
        nameActivityCompletition = this.nameActivityCompletition?: "?",
        nameClub = this.nameClub?: "?",
        nameExtracurricular = this.nameExtracurricular?: "?",
        organizerCompletition = this.organizerCompletition?: "?",
        status = this.status?: 0,
        rankingSemester = this.rankingSemester?: "?",
        universityAndMajor = this.universityAndMajor?: "?",
        numberLetter = this.numberLetter?: "?",
    )
}