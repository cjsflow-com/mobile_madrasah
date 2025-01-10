package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class SchoolViolationStudent(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date_school_violant")
    val dateSchoolViolant: String,
    @SerializedName("name_school_violation")
    val nameSchoolViolation: String,
    @SerializedName("point")
    val point: Int,
    @SerializedName("time_school_violant")
    val timeSchoolViolant: String
)