package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class TargetViolation(
    @SerializedName("note")
    val note: String,
    @SerializedName("target_points")
    val targetPoints: Int,
)