package com.example.man2superapp.source.network.response.users


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("number_handphone")
    val numberHandphone: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("profile")
    val profile: Any,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)