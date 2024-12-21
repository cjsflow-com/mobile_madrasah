package com.example.man2superapp.source.network.response.e_kinerja


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link_google_drive")
    val linkGoogleDrive: String,
    @SerializedName("name_data_support")
    val nameDataSupport: String,
    @SerializedName("performance_evaluation_plan")
    val performanceEvaluationPlan: String,
    @SerializedName("performance_target_evaluation")
    val performanceTargetEvaluation: Int,
    @SerializedName("predicate_work")
    val predicateWork: String,
    @SerializedName("rating_evaluation_work")
    val ratingEvaluationWork: String,
    @SerializedName("task_name")
    val taskName: String,
    @SerializedName("triwulan")
    val triwulan: Int
)