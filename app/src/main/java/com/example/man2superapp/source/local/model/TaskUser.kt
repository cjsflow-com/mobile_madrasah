package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.e_kinerja.Task

data class TaskUser(
    var id: Int,
    var nameTask: String,
    var period: Int
)

fun List<Task>.toGenerateAllTask(): MutableList<TaskUser>{
    val listTask = mutableListOf<TaskUser>()
    this.forEach { listTask.add(it.toTask()) }
    return listTask
}

fun Task.toTask(): TaskUser{
    return TaskUser(
        id = this.id?: 0,
        nameTask = this.nameTask?: "?",
        period = this.period?: 0,
    )
}