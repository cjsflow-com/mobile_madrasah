package com.example.man2superapp.source.network.response.wbs


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("complain_description")
    val complainDescription: String,
    @SerializedName("complain_topic")
    val complainTopic: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("estimated_date_of_occurrence")
    val estimatedDateOfOccurrence: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("related_officials_id")
    val relatedOfficialsId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("work_location")
    val workLocation: String,
    @SerializedName("work_unit")
    val workUnit: String
)