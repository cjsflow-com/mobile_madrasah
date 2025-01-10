package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.violation.SchoolViolationStudent

data class LocalSchoolViolationStudent(
    var id: Int,
    var dateSchoolViolant: String,
    var nameSchoolViolation: String,
    var point: Int,
    var timeSchoolViolant: String
)

fun List<SchoolViolationStudent>.toGenerateListViolationStudent(): MutableList<LocalSchoolViolationStudent>
{
    val listViolationStudent = mutableListOf<LocalSchoolViolationStudent>()
    this.forEach { listViolationStudent.add(it.toViolationStudent()) }
    return listViolationStudent
}

fun SchoolViolationStudent.toViolationStudent(): LocalSchoolViolationStudent{
    return LocalSchoolViolationStudent(
        id = this.id?: 0,
        nameSchoolViolation = this.nameSchoolViolation?: "",
        point = this.point?: 0,
        dateSchoolViolant = this.dateSchoolViolant?: "",
        timeSchoolViolant = this.timeSchoolViolant?: "",
    )
}