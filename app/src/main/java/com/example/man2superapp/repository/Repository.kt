package com.example.man2superapp.repository

import androidx.lifecycle.asLiveData
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

    fun updatePasswordEmployee(token: String,password: String) = remoteDataSource.updatePasswordEmployee(token,password)

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

    fun getAllClassStudent() = remoteDataSource.getClassAllStudent()

    fun updateProfileEmployee(token: String,name: String,email: String,phoneNumber: String,gender: Int,position: String) = remoteDataSource.updateProfileEmployee(token,name,email,phoneNumber,gender,position)

    fun updateProfileStudent(token: String, name: String, email: String, placeBirthday: String, nisn: String, phoneNumber: String, classStudentId: Int, gender: Int, nameFather: String, nameMother: String, address: String, dateBirthday: String) =
        remoteDataSource.updateProfileStudent(token,name,email,placeBirthday,nisn,phoneNumber,classStudentId,gender,nameFather,nameMother,address,dateBirthday)

    fun getCountStatusSongketMotherGtk(token: String) = remoteDataSource.getCountStatusSongketMotherGtk(token)

    fun getByStatusSongketMotherGtk(token: String,status: String) = remoteDataSource.getByStatusSongketMotherGTK(status,token)

    fun getAllListSongketMotherGTK(token: String) = remoteDataSource.getAllListSongketMotherGTK(token)

    fun createSongketMotherGtk(token: String,letterStatement: Int, rankOrGrade: String, nip: String, nuptk: String,
                               fielStudy: String, haveYourEverTaughtSubject: String,startHoliday: String,
                               endHoliday: String, recommendationTitle: String) =
        remoteDataSource.createSongketMotherGTK(token,letterStatement,rankOrGrade,nip,nuptk,fielStudy,haveYourEverTaughtSubject,
            startHoliday,endHoliday,recommendationTitle)
    fun updateSongketMotherGtk(id: Int,rankOrGrade: String, nip: String, nuptk: String,
                               fieldStudy: String, haveYourEverTaughtSubject: String,startHoliday: String,endHoliday: String,titleRecomendation: String)
    = remoteDataSource.updateSongketMotherGTK(id,rankOrGrade,nip,nuptk,fieldStudy,haveYourEverTaughtSubject,startHoliday,endHoliday,titleRecomendation)

    fun showProfileEmployee(id: Int) = remoteDataSource.showProfileEmployee(id)

    fun showProfileStudent(id: Int) = remoteDataSource.showProfileStudent(id)

    fun getAllArticle() = remoteDataSource.getAllArticle()

}