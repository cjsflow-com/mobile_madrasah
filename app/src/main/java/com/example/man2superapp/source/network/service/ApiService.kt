package com.example.man2superapp.source.network.service

import com.example.man2superapp.source.local.model.Article
import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LoginStudentResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
import com.example.man2superapp.source.network.response.songket_emak.CreateSongketMother
import com.example.man2superapp.source.network.response.songket_emak.ListSongketEmakResponse
import com.example.man2superapp.source.network.response.songket_emak.StatusResponse
import com.example.man2superapp.source.network.response.songket_emak.UpdateSongketMother
import com.example.man2superapp.source.network.response.student.UpdatePasswordStudent
import com.example.man2superapp.source.network.response.student.classses.GetAllClassStudentResponse
import com.example.man2superapp.source.network.response.users.UpdateProfileResponse
import com.example.man2superapp.source.network.response.wbs.AllWbsResponse
import com.example.man2superapp.source.network.response.wbs.CreateWbsResponse
import com.example.man2superapp.source.network.response.wbs.GetAllUserResponse
import com.example.man2superapp.ui.activity.LoginStudent
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

    @GET(Constant. GET_ARTICLES)
    suspend fun getArticles(): Response<List<Article>>

}