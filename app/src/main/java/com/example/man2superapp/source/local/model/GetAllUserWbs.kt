package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.wbs.DataUser

data class GetAllUserWbs(
    var id: Int,
    var name: String,
)

fun List<DataUser>.toGenerateAllUserWbs(): MutableList<GetAllUserWbs>{
    val listAllUser = mutableListOf<GetAllUserWbs>()
    this.forEach { listAllUser.add(it.toDataUserWbs()) }
    return listAllUser
}
fun DataUser.toDataUserWbs(): GetAllUserWbs {
    return GetAllUserWbs(
        id = this.id?: 0,
        name = this.name?: "?"
    )
}