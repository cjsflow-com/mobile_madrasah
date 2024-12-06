package com.example.man2superapp.utils

object Constant {

    const val BASE_URL = "https://bedelau.m2mpekanbaru.sch.id/api/"
    const val BASE_URL_DEPLOYMENT = "http://192.168.100.8:5000/api/"
    const val AUTHORIZATION = "Authorization"
    const val WBS = "wbs"
    const val AUTH = "auth"
    const val SONGKET_MOTHER = "songket-mother"
    const val CREATE_WBS = "$WBS/create"
    const val GET_ALL_WBS = "$WBS/index"
    const val LETTER_STATEMENT = "letter_statement"
    const val GET_ALL_USER = "$WBS/get_all_user"
    const val LOGIN = "$AUTH/employee/login"
    const val LOGIN_STUDENT = "$AUTH/student/login"
    const val BEARER = "Bearer "
    const val STUDENT = "student"
    const val CREATE_SONGKET_MOTHER = "$SONGKET_MOTHER/store"
    const val LIST_BY_STATUS_SONGKET_MOTHER = "$SONGKET_MOTHER/filter_by_status"
    const val UPDATE_SONGKET_MOTHER = "$SONGKET_MOTHER/update_songket/{id}"
    const val STATUS_LETTER = "$SONGKET_MOTHER/count_status"
    const val LOGOUT = "auth/employee/logout"
    const val GET_ARTICLES = "api/news"
    const val LETTER_TYPE = "TYPE_LETTER"
    const val UPDATE_PASSWORD = "$STUDENT/update_password"

//    TAG FOR API
    const val TAG_CREATE_SONGKET = "CreateSongketActivity"
}