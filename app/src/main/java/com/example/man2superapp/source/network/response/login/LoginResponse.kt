package com.example.man2superapp.source.network.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("id")
    val id:Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("messages")
    val messages: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("number_phone")
    val numberPhone: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("token_type")
    val tokenType: String
)