package com.example.man2superapp.repository

import com.example.man2superapp.source.network.RemoteDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getAllUser() = remoteDataSource.getALlUser()
    fun loginEmployee(email: String, password: String) = remoteDataSource.loginEmployee(email, password)
    fun getAllWbs(token: String) = remoteDataSource.getALlWbs(token)
    fun createWbs(complainTopic: RequestBody, estimatedOfDateOfOccurrence: RequestBody, relatedOfficialsId: RequestBody,
                  workUnit: RequestBody, workLocation: RequestBody,
                  complainDescription: RequestBody, documentation: MultipartBody.Part?)
            = remoteDataSource.createWbs(complainTopic,estimatedOfDateOfOccurrence,
        relatedOfficialsId,workUnit,workLocation,complainDescription,documentation)
    fun logoutEmployee() = remoteDataSource.logoutEmployee()
}