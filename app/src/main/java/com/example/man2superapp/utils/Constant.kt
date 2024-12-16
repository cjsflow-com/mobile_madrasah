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
    const val TYPE = "type"
    const val TYPE_ROLE  = "role"

    const val NAME = "name"
    const val EMAIL = "email"
    const val NISN = "nisn"
    const val PHONE = "phone"
    const val GENDER = "gender"
    const val POSITION = "position"
    const val CLASS = "class"
    const val PLACE_BIRTHDAY = "place_birthday"
    const val NAME_FATHER = "name_father"
    const val NAME_MOTHER = "name_mother"
    const val ADDRESS = "address"
    const val DATE_BIRTHDAY = "date_birthday"

    const val LETTER_TYPE = "TYPE_LETTER"
    const val UPDATE_PASSWORD = "$STUDENT/update_password"
    const val UPDATE_PASSWORD_EMPLOYEE = "$AUTH/update_password"
    const val UPDATE_PROFILE_EMPLOYEE = "$AUTH/user/update"
    const val UPDATE_PROFILE_STUDENT = "$STUDENT/profile"
    const val LIST_SONGKET_MOTHER = "songket_mother"
    const val TAG_UPDATE_SONGKET_MOTHER = "update_songket_mother"
    const val GET_STUDENT_CLASS = "$STUDENT/get_all_class"

    // Songket Emak GTK/Karyawan
    const val CREATE_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/create"
    const val GET_BY_STATUS = "$SONGKET_MOTHER/gtk/get_by_status"
    const val COUNT_STATUS_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/count_status"
    const val UPDATE_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/update_songket/{id}"
    const val ALL_LIST_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/all_list_songket_mother_employee"

//    TAG FOR API
    const val TAG_CREATE_SONGKET = "CreateSongketActivity"

    // Pastikan URL diakhiri dengan '/'
    const val ARTICLE_BASE_URL = "https://m2mpekanbaru.sch.id/api/"
    const val GET_ARTICLES = "news"
}