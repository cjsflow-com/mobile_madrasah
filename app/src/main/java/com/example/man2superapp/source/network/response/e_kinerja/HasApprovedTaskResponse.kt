package com.example.man2superapp.source.network.response.e_kinerja


import com.google.gson.annotations.SerializedName

data class HasApprovedTaskResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("hasApproved")
    val hasApproved: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)