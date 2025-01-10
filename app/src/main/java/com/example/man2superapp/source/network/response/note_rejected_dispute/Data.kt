package com.example.man2superapp.source.network.response.note_rejected_dispute


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("school_violation_student_id")
    val schoolViolationStudentId: Int,
    @SerializedName("student_id")
    val studentId: Int
)