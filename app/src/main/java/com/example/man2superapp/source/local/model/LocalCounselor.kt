package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.counseling.Counselor

data class LocalCounselor(
    var id: Int,
    var name: String,
)

fun List<Counselor>.toGenerateAllCounselor(): MutableList<LocalCounselor>{
    val listCounselor = mutableListOf<LocalCounselor>()
    this.forEach { listCounselor.add(it.toCounselor()) }
    return listCounselor
}

fun Counselor.toCounselor(): LocalCounselor{
    return LocalCounselor(
        id = this.id?: 0,
        name = this.name?: "?"
    )
}