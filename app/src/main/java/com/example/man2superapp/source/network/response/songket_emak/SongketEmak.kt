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
    @SerializedName("name_university")
    val nameUniversity: String,
    @SerializedName("major")
    val major: String,
    @SerializedName("ranking")
    val ranking: String,
    @SerializedName("semester")
    val semester: String,
    @SerializedName("total_student")
    val totalStudent: String,
    @SerializedName("average_value")
    val averageValue: Double,
    @SerializedName("number_letter")
    val numberLetter: String,
)