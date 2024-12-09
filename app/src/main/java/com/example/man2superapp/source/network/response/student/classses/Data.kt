package com.example.man2superapp.source.network.response.student.classses


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_class")
    val nameClass: String
)