package com.example.man2superapp.source.network

import android.util.Log
import com.example.man2superapp.source.local.model.toGenerateListSongketMother
import com.example.man2superapp.source.network.response.ArticleNewsResponse
import com.example.man2superapp.source.network.response.attendance_student.AddAttendanceStudentResponse
import com.example.man2superapp.source.network.response.attendance_student.AttendanceTodayResponse
import com.example.man2superapp.source.network.response.attendance_student.IndexAttendanceResponse
import com.example.man2superapp.source.network.response.counseling.AllScheduleCounselingResponse
import com.example.man2superapp.source.network.response.counseling.CounselingResponse
import com.example.man2superapp.source.network.response.counseling.CounselorResponse
import com.example.man2superapp.source.network.response.counseling.CreateScheduleResponse
import com.example.man2superapp.source.network.response.counseling.GetScheduleResponse
import com.example.man2superapp.source.network.response.e_kinerja.GetTaskResponse
import com.example.man2superapp.source.network.response.e_kinerja.HasApprovedTaskResponse
import com.example.man2superapp.source.network.response.e_kinerja.IndexResponse
import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LoginStudentResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
import com.example.man2superapp.source.network.response.note_rejected_dispute.NoteRejectedResponse
import com.example.man2superapp.source.network.response.setting.SettingResponse
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.ListSongketEmakResponse
import com.example.man2superapp.source.network.response.songket_emak.StatusResponse
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.gtk.AllSongketEmakByStatusResponse
import com.example.man2superapp.source.network.response.songket_emak.gtk.CountStatusResponse
import com.example.man2superapp.source.network.response.student.BiodataStudentResponse
import com.example.man2superapp.source.network.response.student.UpdatePasswordStudent
import com.example.man2superapp.source.network.response.student.classses.GetAllClassStudentResponse
import com.example.man2superapp.source.network.response.users.BiodataUserResponse
import com.example.man2superapp.source.network.response.users.UpdateProfileResponse
import com.example.man2superapp.source.network.response.violation.AllStudentResponse
import com.example.man2superapp.source.network.response.violation.CreateViolationResponse
import com.example.man2superapp.source.network.response.violation.SchoolViolationMasterResponse
import com.example.man2superapp.source.network.response.violation.SchoolViolationStudentResponse
import com.example.man2superapp.source.network.response.violation.StudentTotalPointResponse
import com.example.man2superapp.source.network.response.violation.TargetViolationResponse
import com.example.man2superapp.source.network.response.violation.UpdatePhoneNumberResponse
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
import java.lang.Thread.State
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

    fun loginStudent(nisn: String, password: String,deviceId: String) = flow<States<LoginStudentResponse>> {
        emit(States.loading())
        apiService.loginStudent(nisn,password,deviceId).let {
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

    fun updateProfileStudent(token: String, name: String, email: String, placeBirthday: String, nisn: String, phoneNumber: String, classStudentId: Int, gender: Int, nameFather: String, nameMother: String, address: String, dateBirthday: String,
                             phoneNumberParent: String) = flow<States<String>>
    {
        emit(States.loading())
        apiService.updateProfileStudent(Constant.BEARER + token,
            name,email,placeBirthday,nisn,phoneNumber,classStudentId,gender,
            nameFather,nameMother,address,dateBirthday,phoneNumberParent).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.message))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updateProfileStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getCountStatusSongketMotherGtk(token: String) = flow<States<CountStatusResponse>>{
        emit(States.loading())
        apiService.getCountStatus(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getCountStatusSongketMotherGtk: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getByStatusSongketMotherGTK(status: String,token: String) = flow<States<AllSongketEmakByStatusResponse>> {
        emit(States.loading())
        apiService.getAllListSongketMotherByStatus(Constant.BEARER + token,status).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getByStatusSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllListSongketMotherGTK(token: String) = flow<States<AllSongketEmakByStatusResponse>> {
        emit(States.loading())
        apiService.getALlListSongketMother(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllListSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createSongketMotherGTK(token: String,letterStatement: Int,rankOrGrade: String,nip: String,
                               nuptk: String, fieldStudy: String, haveYourEverTaughtSubject: String,
                               startHoliday: String, endHoliday: String, recommendation_title: String)
    = flow<States<CreateSongketMother>> {
        emit(States.loading())
        apiService.createSongketMotherGTK(Constant.BEARER + token,letterStatement,rankOrGrade,nip,nuptk,fieldStudy,
            haveYourEverTaughtSubject,startHoliday,endHoliday,recommendation_title).let {
                if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createSongketMotherGTK: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateSongketMotherGTK(id: Int, rankOrGrade: String, nip: String,nuptk: String,
                               fieldStudy: String, haveYourEverTaughtSubject: String, startHoliday: String,
                               endHoliday: String,titleRecomendation: String) =
        flow<States<UpdateSongketMother>> {
            apiService.updateSongketMotherGtk(id,rankOrGrade,nip,nuptk,fieldStudy,haveYourEverTaughtSubject,startHoliday,
                endHoliday,titleRecomendation).let {
                    if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                    else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d(TAG, "updateSongketMotherGTK: ${it.message.toString()}")
            emit(States.failed(it.message.toString()))
        }.flowOn(Dispatchers.IO)

    fun showProfileStudent(id: Int) = flow<States<BiodataStudentResponse>>{
        apiService.getProfileStudent(id).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "showProfileStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun showProfileEmployee(id: Int) = flow<States<BiodataUserResponse>> {
        apiService.getProfileEmployee(id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "showProfileEmployee: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllArticle() = flow<States<ArticleNewsResponse>> {
        emit(States.loading())
        apiService.getArticle().let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllArticle: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllEmployeePerformance(token: String) = flow<States<IndexResponse>> {
        emit(States.loading())
        apiService.getAllEmployeePerformance(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllEmployeePerformance: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllTask(token: String) = flow<States<GetTaskResponse>> {
        emit(States.loading())
        apiService.getAllTaskEmployee(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getALlUser: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getApprovedTask(token: String) = flow<States<HasApprovedTaskResponse>> {
        emit(States.loading())
        apiService.hasApprovedTask(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getApprovedTask: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createRealitation(token: String,id: Int,nameDataSupport: String, linkGoogleDrive: String) = flow<States<UpdateSongketMother>> {
        emit(States.loading())
        apiService.createRealitation(Constant.BEARER + token,id,nameDataSupport,linkGoogleDrive).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createRealitation: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updateEKinerja(token: String, id: Int,performanceEvaluationPlan: String, performanceTargetEvaluation: Int,
                       nameDataSupport: String, linkGoogleDrive: String) = flow<States<UpdateSongketMother>>
    {
        emit(States.loading())
        apiService.updateEmployeePerformance(Constant.BEARER + token,id,performanceEvaluationPlan,performanceTargetEvaluation,nameDataSupport,linkGoogleDrive)
            .let {
                if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                else emit(States.failed(it.message().toString()))
            }
    }.catch {
        Log.d(TAG, "updateEKinerja: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createEmployeePerformance(token: String,taskId: Int, performanceEvaluationPlan: String,
                                  performanceTargetEvaluation: Int) = flow<States<UpdateSongketMother>>
    {
        emit(States.loading())
        apiService.createEmployeePerformance(Constant.BEARER + token,taskId,performanceEvaluationPlan,performanceTargetEvaluation)
            .let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
                else emit(States.failed(it.message().toString()))
            }
    }.catch {
        Log.d(TAG, "createEmployeePerformance: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createServiceSongketMother(id: Int,employeeId: Int, ratingService: Int) = flow<States<UpdateSongketMother>> {
        emit(States.loading())
        apiService.createServiceSongketMother(id,employeeId,ratingService).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createSongketMother: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createServiceSongketMotherGtk(id: Int, employeeId: Int, ratingService: Int) = flow<States<UpdateSongketMother>> {
        emit(States.loading())
        apiService.createServiceSongketMotherGtk(id,employeeId,ratingService).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createSongketMotherGtk: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getOfficerServiceSongketMother() = flow<States<GetAllUserResponse>> {
        emit(States.loading())
        apiService.getOfficerServiceSongketMother().let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getOfficerServiceSongketMother: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllMasterViolation(token: String) = flow<States<SchoolViolationMasterResponse>> {
        emit(States.loading())
        apiService.getAllMasterViolation(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createAllMasterViolation: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun allViolationStudent(token: String) = flow<States<SchoolViolationStudentResponse>> {
        emit(States.loading())
        apiService.getAllViolationStudent(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "allViolationStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createViolationStudent(token: String,studentId: Int?,schoolViolationMasterId: Int?) = flow<States<CreateViolationResponse>> {
        emit(States.loading())
        apiService.createViolationStudent(Constant.BEARER + token,studentId,schoolViolationMasterId).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createViolatioStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun createViolationDisputeStudent(token: String,id: Int,reason: String) = flow<States<CreateViolationResponse>>
    {
        emit(States.loading())
        apiService.createDisputeViolation(Constant.BEARER + token,id,reason).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createViolationDisputeStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getNoteDisputeViolation(token: String, id: Int) = flow<States<NoteRejectedResponse>> {
        emit(States.loading())
        apiService.getNoteDisputeViolation(Constant.BEARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getNoteDisputeViolation: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getTotalPointStudent(token: String) = flow<States<StudentTotalPointResponse>> {
        emit(States.loading())
        apiService.getTotalPoint(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getTotalPointStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllStudentViolation(token: String) = flow<States<AllStudentResponse>> {
        emit(States.loading())
        apiService.getAllStudentForViolation(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllStudentViolation: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun updatePhoneNumberParent(token: String,numberPhoneParent: String) = flow<States<UpdatePhoneNumberResponse>> {
        emit(States.loading())
        apiService.updateStudentPhoneParent(Constant.BEARER + token,numberPhoneParent).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "updatePhoneNumberParent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllTargetViolation(token: String) = flow<States<TargetViolationResponse>> {
        emit(States.loading())
        apiService.getAllTargetViolation(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllTargetViolation: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun addAttendanceToday(token: String) = flow<States<AddAttendanceStudentResponse>> {
        emit(States.loading())
        apiService.storeAttendanceStudent(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "addAttendanceToday: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAttendanceToday(token: String) = flow<States<AttendanceTodayResponse>> {
        emit(States.loading())
        apiService.getAttendanceToday(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAttendanceToday: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getLatAndLong(token: String) = flow<States<SettingResponse>> {
        emit(States.loading())
        apiService.getLatAndLong(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getLatAndLong: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAttendanceByFilterMonth(token: String) = flow<States<IndexAttendanceResponse>> {
        emit(States.loading())
        apiService.filterByMonthAttendance(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAttendanceByFilterMonth: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAlLCounselor(token: String) = flow<States<CounselorResponse>> {
        emit(States.loading())
        apiService.allCounselor(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAlLCounselor: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getAllCounselingSession(token: String) = flow<States<CounselingResponse>> {
        emit(States.loading())
        apiService.allCounselingSession(Constant.BEARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getAllCounselingSession: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getScheduleCounseling(token: String) = flow<States<AllScheduleCounselingResponse>> {
        emit(States.loading())
        apiService.allScheduleCounseling(Constant.BEARER + token).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getScheduleCounseling: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun createScheduleCounseling(token: String,counselingSessionId: Int,dateCounseling: String,counselorId: Int) = flow<States<CreateScheduleResponse>> {
        emit(States.loading())
        apiService.createScheduleCounseling(Constant.BEARER + token,counselingSessionId,dateCounseling,counselorId).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "createScheduleCounseling: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getScheduleCounselingPreview(counseorId: Int) = flow<States<GetScheduleResponse>> {
        emit(States.loading())
        apiService.getScheduleCounseling(counseorId).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d(TAG, "getScheduleCounselingPreview: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }
}