package com.example.man2superapp.source.network.response.student


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("class")
    val classX: Class,
    @SerializedName("date_birthday")
    val dateBirthday: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("father")
    val father: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mother")
    val mother: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("total_point")
    val totalPoint: Int,
    @SerializedName("number_handphone_parent")
    val numberHandphoneParent: String,
    @SerializedName("nisn")
    val nisn: String,
    @SerializedName("number_handphone")
    val numberHandphone: String,
    @SerializedName("place_birthday")
    val placeBirthday: String,
    @SerializedName("profile")
    val profile: Any
)