package com.example.man2superapp.source.network.response.songket_emak


import com.google.gson.annotations.SerializedName

data class SongketEmak(
    @SerializedName("letter_statement")
    val letterStatement: Int,
    @SerializedName("name_activity_completition")
    val nameActivityCompletition: String,
    @SerializedName("name_club")
    val nameClub: String,
    @SerializedName("name_extracurricular")
    val nameExtracurricular: String,
    @SerializedName("organizer_completition")
    val organizerCompletition: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("ranking_semester")
    val rankingSemester: String,
    @SerializedName("university_and_major")
    val universityAndMajor: String,
    @SerializedName("number_letter")
    val numberLetter: String,
)