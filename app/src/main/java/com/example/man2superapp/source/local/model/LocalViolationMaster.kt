package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.violation.ViolationMaster

data class LocalViolationMaster(
    var id: Int,
    var nameSchoolViolation: String,
    var point: Int,
)

fun List<ViolationMaster>.toGenerateListViolationMaster(): MutableList<LocalViolationMaster>{
    val listViolationMaster = mutableListOf<LocalViolationMaster>()
    this.forEach { listViolationMaster.add(it.toViolationMaster()) }
    return listViolationMaster
}

fun ViolationMaster.toViolationMaster(): LocalViolationMaster{
    return LocalViolationMaster(
        id = this.id?: 0,
        nameSchoolViolation = this.nameSchoolViolation?: "",
        point = this.point?: 0,
    )
}