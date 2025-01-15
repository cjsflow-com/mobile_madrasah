package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.violation.TargetViolation

data class LocalTargetViolation(
    var note: String,
    var targetPoints: Int,
)

fun List<TargetViolation>.toGenerateAllTargetViolation(): MutableList<LocalTargetViolation>{
    val listTargetViolation = mutableListOf<LocalTargetViolation>()
    this.forEach { listTargetViolation.add(it.toTargetViolation()) }
    return listTargetViolation
}

fun TargetViolation.toTargetViolation(): LocalTargetViolation{
    return LocalTargetViolation(
        note = this.note?: "",
        targetPoints = this.targetPoints?: 0,
    )
}