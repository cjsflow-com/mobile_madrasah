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

    fun loginStudent(nisn: String, password: String) = remoteDataSource.loginStudent(nisn,password)

    fun listSongketMotherByStatus(studentId: Int,status: String) = remoteDataSource.listSongketMotherByStatus(status,studentId)

    fun createSongketMother(
        letterStatement: Int,
        nameActivityCompletition: String,
        organizerCompletition: String,
        nameEskul: String,
        nameClub: String,
        studentId: Int,
        nameUniversity: String,
        major: String,
        ranking: String,
        semester: String,
        totalStudent: String,
        averageValue: Double,
    ) = remoteDataSource.createSongketMother(letterStatement,
        nameActivityCompletition,
        organizerCompletition,
        nameEskul,
        nameClub,
        studentId,
        nameUniversity,
        major,
        ranking,
        semester,
        totalStudent,
        averageValue
    )

    fun updatePassword(token: String, password: String) = remoteDataSource.updatePasswordStudent(token,password)

    fun updateSongketMother(id: Int,nameActivityCompletition: String,
                            organizerCompletition: String,
                            nameEskul: String,
                            nameClub: String,
                            nameUniversity: String,
                            major: String,
                            ranking: String,
                            semester: String,
                            totalStudent: String,
                            averageValue: Double) =
        remoteDataSource.updateSonkgetMother(
                                id,
                                nameActivityCompletition,
                                organizerCompletition,
                                nameEskul,
                                nameClub,
                                nameUniversity,
                                major,
                                ranking,
                                semester,
                                totalStudent,
                                averageValue
                        )

    fun getCountStatus(id: Int) = remoteDataSource.getCountStatusSongketEmak(id)
}