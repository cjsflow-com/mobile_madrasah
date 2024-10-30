package com.example.man2superapp.source.local.model

import com.example.man2superapp.source.network.response.wbs.Data

data class  WhistleBlowingSystem(
    var complainTopic: String,
    var estimatedDateOfOccurrence: String,
    var relatedOfficialsId: Int,
    var workUnit: String,
    var workLocation: String,
    var complainDescription: String,
    var documentation: String,
)

fun List<Data>.toGenerateAllBlowingSystem(): MutableList<WhistleBlowingSystem>{
    val listAllWbs = mutableListOf<WhistleBlowingSystem>()
    this.forEach { listAllWbs.add(it.toDataWhistleBlowingSystem()) }
    return listAllWbs
}

fun Data.toDataWhistleBlowingSystem(): WhistleBlowingSystem {
    return WhistleBlowingSystem(
        complainTopic = this.complainTopic?: "?",
        estimatedDateOfOccurrence = this.estimatedDateOfOccurrence?: "?",
        relatedOfficialsId = this.relatedOfficialsId?: 0,
        workUnit = this.workUnit?: "?",
        workLocation = this.workLocation?: "?",
        complainDescription = this.complainDescription?: "?",
        documentation = this.documentation?: "?"
    )
}