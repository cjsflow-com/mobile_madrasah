package com.example.man2superapp.source.network

import android.util.Log
import com.example.man2superapp.source.local.model.toGenerateListSongketMother
import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LoginStudentResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.ListSongketEmakResponse
import com.example.man2superapp.source.network.response.songket_emak.StatusResponse
import com.example.man2superapp.source.network.response.wbs.AllWbsResponse
import com.example.man2superapp.source.network.response.wbs.CreateWbsResponse
import com.example.man2superapp.source.network.response.wbs.GetAllUserResponse
import com.example.man2superapp.source.network.service.ApiService
import com.example.man2superapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
){
    companion object{
        const val TAG = "RemoteDataSource"
    }

    fun getALlUser() = flow<States<GetAllUserResponse>> {
        emit(States.loading())
        apiService.getAllUser().let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "getALlUser: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun loginEmployee(email: String, password: String) = flow<States<LoginResponse>> {
        emit(States.loading())
        apiService.loginUser(email, password).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "loginEmployee: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun loginStudent(nisn: String, password: String) = flow<States<LoginStudentResponse>> {
        emit(States.loading())
        apiService.loginStudent(nisn,password).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "loginStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getALlWbs(token: String) = flow<States<AllWbsResponse>> {
        emit(States.loading())
        apiService.getAllWbs(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "getALlWbs: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createWbs(complainTopic: RequestBody, estimatedOfDateOfOccurrence: RequestBody, relatedOfficialsId: RequestBody,
                  workUnit: RequestBody, workLocation: RequestBody,
                  complainDescription: RequestBody, documentation: MultipartBody.Part?) = flow<States<CreateWbsResponse>>
    {
        emit(States.loading())
        apiService.createWbs(complainTopic,estimatedOfDateOfOccurrence,relatedOfficialsId,workUnit,
            workLocation,complainDescription,documentation).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "createWbs: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun logoutEmployee() = flow<States<LogoutResponse>> {
        emit(States.loading())
        apiService.logoutUser().let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "logoutEmployee: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createSongketMother(letterStatement: Int,nameActivityCompletition: String,organizerCompletition: String,
                            nameEskul: String,nameClub: String, studentId: Int,
                            nameUniversity: String, major: String, ranking: String, semester: String,
                            totalStudent: String,averageValue: Double) = flow<States<CreateSongketMother>> {
        emit(States.loading())
        apiService.createSongketMother(letterStatement,nameActivityCompletition,organizerCompletition,nameEskul,nameClub,studentId,
            nameUniversity,major,ranking,semester,totalStudent,averageValue)
            .let { if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))}
    }.catch {
        Log.d(TAG, "createSongketMother: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun listSongketMotherByStatus(status: String,studentId: Int) = flow<States<ListSongketEmakResponse>> {
        emit(States.loading())
        apiService.getListByStatusSongketMother(status,studentId).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message()))
        }
    }.catch {
        Log.d(TAG, "listSongketMotherByStatus: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCountStatusSongketEmak(studentId: Int) = flow<States<StatusResponse>> {
        emit(States.loading())
        apiService.getCountStatus(studentId).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getCountStatusSongketEmak: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}