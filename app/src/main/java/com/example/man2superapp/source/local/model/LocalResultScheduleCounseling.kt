package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.counseling.Result


data class LocalResultScheduleCounseling(
    var date: String,
    var dateIso: String,
    var status: Boolean
)

fun List<Result>.toGenerateScheduleCounselor(): MutableList<LocalResultScheduleCounseling>{
    val listGetScheduleCounseling = mutableListOf<LocalResultScheduleCounseling>()
    this.forEach { listGetScheduleCounseling.add(it.toGetScheduleCounseling()) }
    return listGetScheduleCounseling
}

fun Result.toGetScheduleCounseling(): LocalResultScheduleCounseling{
    return LocalResultScheduleCounseling(
        date = this.date?: "?",
        dateIso = this.dateIso?: "?",
        status = this.status?: false,
    )
}