package com.example.man2superapp.source.network.response.e_kinerja


import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_task")
    val nameTask: String,
    @SerializedName("period")
    val period: Int,
)