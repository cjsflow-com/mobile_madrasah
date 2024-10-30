package com.example.man2superapp.ui.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.man2superapp.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AllViewModel @Inject constructor(private val repository: Repository): ViewModel()
{
    fun loginEmployee(email: String, password: String) = repository.loginEmployee(email, password).asLiveData()
    fun getAllWbs(token: String) = repository.getAllWbs(token).asLiveData()
    fun getAllUser() = repository.getAllUser().asLiveData()
    fun createWbs(complainTopic: RequestBody, estimatedOfDateOfOccurrence: RequestBody, relatedOfficialsId: RequestBody,
                  workUnit: RequestBody, workLocation: RequestBody,
                  complainDescription: RequestBody, documentation: MultipartBody.Part) =
        repository.createWbs(complainTopic,estimatedOfDateOfOccurrence,relatedOfficialsId,workUnit,
            workLocation,complainDescription,documentation).asLiveData()

    fun logoutEmployee() = repository.logoutEmployee().asLiveData()
}