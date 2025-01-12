package com.example.man2superapp.source.network.service

import com.example.man2superapp.source.network.response.ArticleNewsResponse
import com.example.man2superapp.source.network.response.e_kinerja.GetTaskResponse
import com.example.man2superapp.source.network.response.e_kinerja.HasApprovedTaskResponse
import com.example.man2superapp.source.network.response.e_kinerja.IndexResponse
import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LoginStudentResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
import com.example.man2superapp.source.network.response.note_rejected_dispute.NoteRejectedResponse
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
import com.example.man2superapp.source.network.response.violation.CreateViolationResponse
import com.example.man2superapp.source.network.response.violation.SchoolViolationMasterResponse
import com.example.man2superapp.source.network.response.violation.SchoolViolationStudentResponse
import com.example.man2superapp.source.network.response.violation.StudentTotalPointResponse
import com.example.man2superapp.source.network.response.wbs.AllWbsResponse
import com.example.man2superapp.source.network.response.wbs.CreateWbsResponse
import com.example.man2superapp.source.network.response.wbs.GetAllUserResponse
import com.example.man2superapp.utils.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Fitur WBS
    @Multipart
    @POST(Constant.CREATE_WBS)
    suspend fun createWbs(
        @Part("complain_topic") complainTopic: RequestBody,
        @Part("estimated_date_of_occurrence") estimatedDateOfOccurrence: RequestBody,
        @Part("related_officials_id") relatedOfficialsId: RequestBody,
        @Part("work_unit") workUnit: RequestBody,
        @Part("work_location") workLocation: RequestBody,
        @Part("complain_description") complainDescription: RequestBody,
        @Part documentation: MultipartBody.Part?,
    ): Response<CreateWbsResponse>


    @GET(Constant.GET_ALL_WBS)
    suspend fun getAllWbs(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<AllWbsResponse>

    @GET(Constant.GET_ALL_USER)
    suspend fun getAllUser(): Response<GetAllUserResponse>

    @FormUrlEncoded
    @POST(Constant.LOGIN)
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST(Constant.LOGIN_STUDENT)
    suspend fun loginStudent(
        @Field("nisn") nisn: String,
        @Field("password") password: String,
    ): Response<LoginStudentResponse>

    @POST(Constant.LOGOUT)
    suspend fun logoutUser(): Response<LogoutResponse>

    @GET(Constant.LIST_BY_STATUS_SONGKET_MOTHER)
    suspend fun getListByStatusSongketMother(
        @Query("status") status: String,
        @Query("id") id: Int
    ): Response<ListSongketEmakResponse>

    @FormUrlEncoded
    @POST(Constant.CREATE_SONGKET_MOTHER)
    suspend fun createSongketMother(
        @Field("letter_statement") letterStatement: Int,
        @Field("name_activity_completition")nameActivityCompletition: String,
        @Field("organizer_completition") organizerCompletition: String,
        @Field("name_extracurricular") nameEksul: String,
        @Field("name_club") nameClub: String,
        @Field("student_id") studentId: Int,
        @Field("name_university") nameUniversity: String,
        @Field("major") major: String,
        @Field("ranking") ranking: String,
        @Field("semester") semester: String,
        @Field("total_student") totalStudent: String,
        @Field("average_value") averageValue: Double,
    ): Response<CreateSongketMother>


    @FormUrlEncoded
    @PUT(Constant.UPDATE_SONGKET_MOTHER)
    suspend fun updateSongketMother(
        @Path("id") id: Int,
        @Field("name_activity_completition") nameActivityCompletition: String,
        @Field("organizer_completition") organizerCompletition: String,
        @Field("name_extracurricular") nameEskul: String,
        @Field("name_club") nameClub: String,
        @Field("name_university") nameUniversity: String,
        @Field("major") major: String,
        @Field("ranking") ranking: String,
        @Field("semester") semester: String,
        @Field("total_student") totalStudent: String,
        @Field("average_value") averageValue: Double,
    ): Response<UpdateSongketMother>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_PASSWORD)
    suspend fun updatePassword(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("password") password: String
    ): Response<UpdatePasswordStudent>

    @GET(Constant.GET_STUDENT_CLASS)
    suspend fun getClass(): Response<GetAllClassStudentResponse>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_PASSWORD_EMPLOYEE)
    suspend fun updatePasswordUser(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("password") password: String
    ): Response<UpdatePasswordStudent>

    @GET(Constant.STATUS_LETTER)
    suspend fun getCountStatus(
        @Query("id") id: Int
    ): Response<StatusResponse>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_PROFILE_EMPLOYEE)
    suspend fun updateProfileEmployee(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("number_handphone") phoneNumber: String,
        @Field("gender") gender: Int,
        @Field("position") position: String,
    ): Response<UpdateProfileResponse>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_PROFILE_STUDENT)
    suspend fun updateProfileStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("place_birthday") placeBirthday: String,
        @Field("nisn") nisn: String,
        @Field("number_handphone") phoneNumber: String,
        @Field("class_student_id") classStudentId: Int,
        @Field("gender") gender: Int,
        @Field("name_father") nameFather: String,
        @Field("name_mother") nameMother: String,
        @Field("address") address: String,
        @Field("date_birthday") dateBirthday: String,
    ): Response<UpdateProfileResponse>

    @GET(Constant.COUNT_STATUS_SONGKET_MOTHER_GTK)
    suspend fun getCountStatus(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<CountStatusResponse>

    @GET(Constant.GET_BY_STATUS)
    suspend fun getAllListSongketMotherByStatus(
        @Header(Constant.AUTHORIZATION)token: String,
        @Query("status") status: String
    ): Response<AllSongketEmakByStatusResponse>

    @GET(Constant.ALL_LIST_SONGKET_MOTHER_GTK)
    suspend fun getALlListSongketMother(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<AllSongketEmakByStatusResponse>

    @FormUrlEncoded
    @POST(Constant.CREATE_SONGKET_MOTHER_GTK)
    suspend fun createSongketMotherGTK(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("letter_statement") letterStatement: Int,
        @Field("rank_or_grade") rankOrGrade: String,
        @Field("nip") nip: String,
        @Field("nuptk") nuptk: String,
        @Field("field_study") fieldStudy: String,
        @Field("have_your_ever_taught_subject") haveYourEverTaughtSubject: String,
        @Field("start_holiday") startHoliday: String,
        @Field("end_holiday") endHoliday: String,
        @Field("recommendation_title") recomendationTitle: String
    ): Response<CreateSongketMother>

    @GET(Constant.SHOW_PROFILE_EMPLOYEE)
    suspend fun getProfileEmployee(
        @Path(Constant.ID) id: Int
    ): Response<BiodataUserResponse>

    @GET(Constant.SHOW_PROFILE_STUDENT)
    suspend fun getProfileStudent(
        @Path(Constant.ID) id: Int
    ): Response<BiodataStudentResponse>


    @GET(Constant.GET_ARTICLE)
    suspend fun getArticle(): Response<ArticleNewsResponse>


    @FormUrlEncoded
    @PUT(Constant.UPDATE_SONGKET_MOTHER_GTK)
    suspend fun updateSongketMotherGtk(
        @Path("id") id: Int,
        @Field("rank_or_grade") rankOrGrade: String,
        @Field("nip") nip: String,
        @Field("nuptk") nuptk: String,
        @Field("field_study") fieldStudy: String,
        @Field("have_your_ever_taught_subject") haveYourEverTaughtSubject: String,
        @Field("start_holiday") startHoliday: String,
        @Field("end_holiday") endHoliday: String,
        @Field("recommendation_title") titleRecomendation: String
    ): Response<UpdateSongketMother>

    @GET(Constant.SHOW_ALL_EMPLOYEE_PERFORMANCE)
    suspend fun getAllEmployeePerformance(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<IndexResponse>

    @GET(Constant.HAS_APPROVED_TASK)
    suspend fun hasApprovedTask(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<HasApprovedTaskResponse>

    @FormUrlEncoded
    @PUT(Constant.CREATE_REALITATION)
    suspend fun createRealitation(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path("id") id: Int,
        @Field("name_data_support") nameDataSupport: String,
        @Field("link_google_drive") linkGoogleDrive: String,
    ): Response<UpdateSongketMother>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_EMPLOYEE_PERFORMANCE)
    suspend fun updateEmployeePerformance(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path("id") id: Int,
        @Field("performance_evaluation_plan") performanceEvaluationPlan: String,
        @Field("performance_target_evaluation") performanceTargetEvaluation: Int,
        @Field("name_data_support") nameDataSupport: String,
        @Field("link_google_drive") linkGoogleDrive: String,
    ): Response<UpdateSongketMother>

    @FormUrlEncoded
    @POST(Constant.CREATE_EMPLOYEE_PERFORMANCE)
    suspend fun createEmployeePerformance(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("task_id") taskId: Int,
        @Field("performance_evaluation_plan") performanceEvaluationPlain: String,
        @Field("performance_target_evaluation") performanceTargetEvaluation: Int,
    ): Response<UpdateSongketMother>

    @FormUrlEncoded
    @PUT(Constant.CREATE_SERVICE_SONGKET_MOTHER)
    suspend fun createServiceSongketMother(
        @Path("id") id: Int,
        @Field("employee_id") employeeService: Int,
        @Field("rating_service") ratingService: Int,
    ): Response<UpdateSongketMother>

    @FormUrlEncoded
    @PUT(Constant.CREATE_SERVICE_SONGKET_MOTHER_GTK)
    suspend fun createServiceSongketMotherGtk(
        @Path("id") id: Int,
        @Field("employee_id") employeeService: Int,
        @Field("rating_service") ratingService: Int,
    ): Response<UpdateSongketMother>

    @GET(Constant.GET_TASK)
    suspend fun getAllTaskEmployee(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<GetTaskResponse>

    @GET(Constant.OFFICER_SERVICE_SONGKET_MOTHER)
    suspend fun getOfficerServiceSongketMother(): Response<GetAllUserResponse>

    @GET(Constant.ALL_MASTER_VIOLATION)
    suspend fun getAllMasterViolation(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<SchoolViolationMasterResponse>

    @GET(Constant.ALL_VIOLATION_STUDENT)
    suspend fun getAllViolationStudent(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<SchoolViolationStudentResponse>

    @FormUrlEncoded
    @POST(Constant.CREATE_VIOLATION_STUDENT)
    suspend fun createViolationStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field("student_id") studentId: Int,
        @Field("school_violation_master_id") schoolViolationMasterId: Int
    ): Response<CreateViolationResponse>

    @FormUrlEncoded
    @PUT(Constant.CREATE_DISPUTE_VIOLATION)
    suspend fun createDisputeViolation(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path("id") id: Int,
        @Field("reason") reason: String,
    ): Response<CreateViolationResponse>

    @GET(Constant.NOTE_DISPUTE_VIOLATION)
    suspend fun getNoteDisputeViolation(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path("id") Id: Int,
    ): Response<NoteRejectedResponse>

    @GET(Constant.TOTAL_POINT_STUDENT)
    suspend fun getTotalPoint(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<StudentTotalPointResponse>

//    @GET(Constant.GET_ALL_SCHOOL_VIOLATION_MASTER)
//    suspend fun getAllSchoolViolationMaster(
//        @Header(Constant.AUTHORIZATION) token: String,
//    ): Response<>
}