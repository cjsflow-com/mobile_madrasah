package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.attendance_student.Attendance

data class LocalAttendance(
    var id: Int,
    var timeIn: String,
    var timeOut: String,
    var statusIn: String,
    var statusOut: String
)

fun List<Attendance>.toGenerateAttendance(): MutableList<LocalAttendance>{
    val listAttendance = mutableListOf<LocalAttendance>()
    this.forEach { listAttendance.add(it.toAttendance()) }
    return listAttendance
}

fun Attendance.toAttendance(): LocalAttendance{
    return LocalAttendance(
        id = this.id?: 0,
        timeIn = this.timeIn?: "?",
        timeOut = this.timeOut?: "?",
        statusIn = this.statusIn?: "?",
        statusOut = this.statusOut?: "?",
    )
}
