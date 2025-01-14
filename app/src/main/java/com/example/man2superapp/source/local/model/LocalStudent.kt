package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.violation.Student

data class LocalStudent(
    var id: Int,
    var name: String
)
fun List<Student>.toGenerateAllStudent(): MutableList<LocalStudent>{
    val listStudent = mutableListOf<LocalStudent>()
    this.forEach { listStudent.add(it.toDataStudent()) }
    return listStudent
}

fun Student.toDataStudent(): LocalStudent{
    return LocalStudent(
        id = this.id?: 0,
        name = this.name?: "?"
    )
}