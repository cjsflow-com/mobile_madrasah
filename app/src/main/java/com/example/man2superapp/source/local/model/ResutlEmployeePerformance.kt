package com.example.man2superapp.source.local.model

import android.os.Parcelable
import com.example.man2superapp.source.network.response.e_kinerja.Result
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResutlEmployeePerformance(
    var id: Int,
    var linkGoogleDrive: String,
    var nameDataSupport: String,
    var performanceEvaluationPlan: String,
    var performanceTargetEvaluation: Int,
    var predicateWork: String,
    var ratingEvaluationWork: String,
    var taskName: String,
    var triwulan: Int
): Parcelable

fun List<Result>.toGenerateAllResult(): MutableList<ResutlEmployeePerformance>{
    val listAllResult = mutableListOf<ResutlEmployeePerformance>()
    this.forEach { listAllResult.add(it.toResult()) }
    return listAllResult
}

fun Result.toResult(): ResutlEmployeePerformance{
    return ResutlEmployeePerformance(
        id = this.id?: 0,
        linkGoogleDrive = this.linkGoogleDrive?: "",
        nameDataSupport = this.nameDataSupport?: "",
        performanceEvaluationPlan = this.performanceEvaluationPlan?: "",
        performanceTargetEvaluation = this.performanceTargetEvaluation?: 0,
        predicateWork = this.predicateWork?: "?",
        ratingEvaluationWork = this.ratingEvaluationWork?: "?",
        taskName = this.taskName?: "?",
        triwulan = this.triwulan?: 0,
    )
}
