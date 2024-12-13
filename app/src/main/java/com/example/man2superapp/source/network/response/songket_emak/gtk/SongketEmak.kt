package com.example.man2superapp.source.network.response.songket_emak.gtk


import com.google.gson.annotations.SerializedName

data class SongketEmak(
    @SerializedName("end_holiday")
    val endHoliday: String,
    @SerializedName("field_study")
    val fieldStudy: String,
    @SerializedName("have_your_ever_taught_subject")
    val haveYourEverTaughtSubject: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("letter_statement")
    val letterStatement: Int,
    @SerializedName("nip")
    val nip: String,
    @SerializedName("number_letter")
    val numberLetter: String,
    @SerializedName("nuptk")
    val nuptk: String,
    @SerializedName("rank_or_grade")
    val rankOrGrade: String,
    @SerializedName("recommendation_title")
    val recommendationTitle: String,
    @SerializedName("start_holiday")
    val startHoliday: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user_id")
    val userId: Int
)