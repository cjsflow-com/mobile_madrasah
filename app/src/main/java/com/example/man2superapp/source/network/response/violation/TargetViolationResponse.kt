package com.example.man2superapp.source.network.response.violation


import com.google.gson.annotations.SerializedName

data class TargetViolationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("target_violation")
    val targetViolation: List<TargetViolation>
)