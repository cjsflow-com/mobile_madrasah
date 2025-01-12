package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.note_rejected_dispute.Data

data class DataNoteRejected(
    var id: Int,
    var note: String,
    var schoolViolationId:Int,
    var studentId:Int,
)

fun Data.toNoteRejected(): DataNoteRejected{
    return DataNoteRejected(
        id = this.id?: 0,
        note = this.note?: "",
        schoolViolationId = this.schoolViolationStudentId?: 0,
        studentId = this.studentId?: 0
    )
}