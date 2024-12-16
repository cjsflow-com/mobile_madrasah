package com.example.man2superapp.source.network.response.student


import com.google.gson.annotations.SerializedName

data class Class(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_class")
    val nameClass: String,
    @SerializedName("updated_at")
    val updatedAt: String
)