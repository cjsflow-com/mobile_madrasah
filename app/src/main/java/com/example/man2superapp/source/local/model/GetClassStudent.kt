package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.student.classses.Data

data class GetClassStudent(
    var id: Int,
    var name_class: String
)

fun List<Data>.toGenerateClassStudent(): MutableList<GetClassStudent>{
    val listClassStudent = mutableListOf<GetClassStudent>()
    this.forEach { listClassStudent.add(it.toClassStudent()) }
    return listClassStudent
}

fun Data.toClassStudent(): GetClassStudent {
    return GetClassStudent(
        id = this.id?: 0,
        name_class = this.nameClass?: "?"
    )
}