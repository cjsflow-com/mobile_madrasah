package com.example.man2superapp.source.network.response.e_kinerja


import com.google.gson.annotations.SerializedName

data class IndexResponse(
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("success")
    val success: Boolean
)