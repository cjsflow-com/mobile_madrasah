package com.example.man2superapp.source.local.model

import android.os.Parcelable
import com.example.man2superapp.source.network.response.songket_emak.gtk.SongketEmak
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongketMotherGTK(
    var id: Int,
    var letterStatement: Int,
    var status: Int,
    var nip: String,
    var nuptk: String,
    var rankOrGrade: String,
    var recommendationTitle: String,
    var numberLetter: String,
    var fieldStudy: String,
    var haveYourEverTaughtSubject: String,
    var startHoliday: String,
    var endHoliday: String,
    var userId: Int
): Parcelable

fun List<SongketEmak>.toGenerateListSongketMotherGtk(): MutableList<SongketMotherGTK>{
    val listSongketMotherGtk = mutableListOf<SongketMotherGTK>()
    this.forEach { listSongketMotherGtk.add(it.toSongketMother()) }
    return listSongketMotherGtk
}

fun SongketEmak.toSongketMother(): SongketMotherGTK{
    return SongketMotherGTK(
        id = this.id?: 0,
        letterStatement = this.letterStatement?: 0,
        status = this.status?: 0,
        nip = this.nip?: "",
        nuptk = this.nuptk?: "",
        rankOrGrade = this.rankOrGrade?: "",
        recommendationTitle = this.recommendationTitle?: "",
        numberLetter = this.numberLetter?: "",
        fieldStudy = this.fieldStudy?: "",
        haveYourEverTaughtSubject = this.haveYourEverTaughtSubject?: "",
        startHoliday = this.startHoliday?: "",
        endHoliday = this.endHoliday?: "",
        userId = this.userId?: 0,
    )
}