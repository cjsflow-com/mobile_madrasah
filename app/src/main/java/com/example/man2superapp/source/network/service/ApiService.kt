package com.example.man2superapp.source.network.service

import com.example.man2superapp.source.network.response.login.LoginResponse
import com.example.man2superapp.source.network.response.login.LogoutResponse
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
import retrofit2.http.Part

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
        @Part documentation: MultipartBody.Part,
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

    @POST(Constant.LOGOUT)
    suspend fun logoutUser(): Response<LogoutResponse>
}