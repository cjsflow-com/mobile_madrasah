package com.example.man2superapp.source.network.response.note_rejected_dispute


import com.google.gson.annotations.SerializedName

data class NoteRejectedResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)