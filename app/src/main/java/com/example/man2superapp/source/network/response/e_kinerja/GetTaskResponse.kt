package com.example.man2superapp.source.network.response.e_kinerja


import com.google.gson.annotations.SerializedName

data class GetTaskResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("task")
    val task: List<Task>
)