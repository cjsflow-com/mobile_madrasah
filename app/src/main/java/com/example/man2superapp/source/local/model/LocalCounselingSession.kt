package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.counseling.SessionCounseling

data class LocalCounselingSession(
    var counselingName: String,
    var endTime: String,
    var id: Int,
    var startTime: String,
)

fun List<SessionCounseling>.toGenerateListCounselingSession(): MutableList<LocalCounselingSession>{
    val listCounselingSession = mutableListOf<LocalCounselingSession>()
    this.forEach { listCounselingSession.add(it.toSessionCounseling()) }
    return listCounselingSession
}

fun SessionCounseling.toSessionCounseling(): LocalCounselingSession{
    return LocalCounselingSession(
        counselingName = this.counselingName?: "?",
        endTime = this.endTime?: "?",
        id = this.id?: 0,
        startTime = this.startTime?: "?"
    )
}