package com.example.man2superapp.source.network.response.setting


import com.google.gson.annotations.SerializedName

data class SettingResponse(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("success")
    val success: Boolean
)