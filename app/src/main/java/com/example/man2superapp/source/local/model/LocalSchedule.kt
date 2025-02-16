package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.counseling.Shcedule

data class LocalSchedule(
    var counselorName: String,
    var dateCounseling: String,
    var id: Int,
    var sessionName: String,
    var status: String,
    var studentName: String,
)

fun List<Shcedule>.toGenerateSchedule(): MutableList<LocalSchedule>{
    val listSchedule = mutableListOf<LocalSchedule>()
    this.forEach { listSchedule.add(it.toSchedule()) }
    return listSchedule
}

fun Shcedule.toSchedule(): LocalSchedule{
    return LocalSchedule(
        counselorName = this.counselorName?: "?",
        dateCounseling = this.dateCounseling?: "?",
        id = this.id?: 0,
        sessionName = this.sessionName?: "?",
        status = this.status?: "?",
        studentName = this.studentName?: "?",
    )
}