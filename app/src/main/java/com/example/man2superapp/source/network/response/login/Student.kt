package com.example.man2superapp.source.network.response.login


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("address")
    val address: String,
    @SerializedName("class_name")
    val className: String,
    @SerializedName("date_birthday")
    val dateBirthday: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_father")
    val nameFather: String,
    @SerializedName("name_mother")
    val nameMother: String,
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("number_handphone")
    val numberHandphone: String,
    @SerializedName("profile")
    val profile: String
)