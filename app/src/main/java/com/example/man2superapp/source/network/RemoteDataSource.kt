package com.example.man2superapp.source.network

import android.util.Log
import com.example.man2superapp.source.local.model.toGenerateListSongketMother
import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LoginStudentResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.ListSongketEmakResponse
import com.example.man2superapp.source.network.response.songket_emak.StatusResponse
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.gtk.AllSongketEmakByStatusResponse
import com.example.man2superapp.source.network.response.songket_emak.gtk.CountStatusResponse
import com.example.man2superapp.source.network.response.student.UpdatePasswordStudent
import com.example.man2superapp.source.network.response.student.classses.GetAllClassStudentResponse
import com.example.man2superapp.source.network.response.users.UpdateProfileResponse
import com.example.man2superapp.source.network.response.wbs.AllWbsResponse
import com.example.man2superapp.source.network.response.wbs.CreateWbsResponse
import com.example.man2superapp.source.network.response.wbs.GetAllUserResponse
import com.example.man2superapp.source.network.service.ApiService
import com.example.man2superapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
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

    fun updateSonkgetMother(id: Int,nameActivityCompletition: String,organizerCompletition: String,
                            nameEskul: String,nameClub: String,
                            nameUniversity: String, major: String, ranking: String, semester: String,
                            totalStudent: String,averageValue: Double) = flow<States<UpdateSongketMother>>
    {
        emit(States.loading())
        apiService.updateSongketMother(id,nameActivityCompletition,organizerCompletition,nameEskul,nameClub,
            nameUniversity,major,ranking,semester,totalStudent,averageValue).let {
                if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updateSonkgetMother: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun updatePasswordStudent(token: String,password: String) = flow<States<UpdatePasswordStudent>> {
        emit(States.loading())
        apiService.updatePassword(Constant.BEARER + token,password).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updatePasswordStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updatePasswordEmployee(token: String, password: String) = flow<States<String>> {
        emit(States.loading())
        apiService.updatePasswordUser(Constant.BEARER + token,password).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.message))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updatePasswordEmployee: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getClassAllStudent() = flow<States<GetAllClassStudentResponse>> {
        emit(States.loading())
        apiService.getClass().let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getClassAllStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateProfileEmployee(token: String,name: String,email: String,phoneNumber: String,gender: Int,position: String) = flow<States<String>> {
        emit(States.loading())
        apiService.updateProfileEmployee(Constant.BEARER + token,name,email,phoneNumber,gender,position).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.message))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updateProfileEmployee: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateProfileStudent(token: String, name: String, email: String, placeBirthday: String, nisn: String, phoneNumber: String, classStudentId: Int, gender: Int, nameFather: String, nameMother: String, address: String, dateBirthday: String) = flow<States<String>>
    {
        emit(States.loading())
        apiService.updateProfileStudent(Constant.BEARER + token,
            name,email,placeBirthday,nisn,phoneNumber,classStudentId,gender,
            nameFather,nameMother,address,dateBirthday).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.message))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updateProfileStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCountStatusSongketMotherGtk() = flow<States<CountStatusResponse>>{
        emit(States.loading())
        apiService.getCountStatus().let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getCountStatusSongketMotherGtk: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getByStatusSongketMotherGTK(status: String) = flow<States<AllSongketEmakByStatusResponse>> {
        emit(States.loading())
        apiService.getAllListSongketMotherByStatus(status).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getByStatusSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllListSongketMotherGTK() = flow<States<AllSongketEmakByStatusResponse>> {
        emit(States.loading())
        apiService.getALlListSongketMother().let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllListSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createSongketMotherGTK(letterStatement: Int,rankOrGrade: String,nip: String,
                               nuptk: String, fieldStudy: String, haveYourEverTaughtSubject: String,
                               startHoliday: String, endHoliday: String, recommendation_title: String)
    = flow<States<CreateSongketMother>> {
        emit(States.loading())
        apiService.createSongketMotherGTK(letterStatement,rankOrGrade,nip,nuptk,fieldStudy,
            haveYourEverTaughtSubject,startHoliday,endHoliday,recommendation_title).let {
                if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateSongketMotherGTK(id: Int, rankOrGrade: String, nip: String, status: Int,
                               fieldStudy: String, haveYourEverTaughtSubject: String, startHoliday: String,
                               endHoliday: String, numberLetter: String) =
        flow<States<UpdateSongketMother>> {
            apiService.updateSongketMotherGtk(id,rankOrGrade,nip,status,fieldStudy,haveYourEverTaughtSubject,startHoliday,
                endHoliday,numberLetter).let {
                    if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                    else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d(TAG, "updateSongketMotherGTK: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}