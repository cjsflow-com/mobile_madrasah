package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class ViolationMaster(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_school_violation")
    val nameSchoolViolation: String,
    @SerializedName("point")
    val point: Int,
)