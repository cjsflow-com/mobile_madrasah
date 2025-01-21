package com.example.man2superapp.utils

object Constant {

    const val BASE_URL = "https://bedelau.m2mpekanbaru.sch.id/api/"
    const val BASE_URL_DEPLOYMENT = "http://192.168.100.8:5000/api/"
    const val BASE_URL_DEVELOPMENT_WORK = "http://10.9.0.26:5000/api/"
    const val AUTHORIZATION = "Authorization"
    const val WBS = "wbs"
    const val AUTH = "auth"
    const val SONGKET_MOTHER = "songket-mother"
    const val E_KINERJA = "employee_performance"
    const val CREATE_WBS = "$WBS/create"
    const val GET_ALL_WBS = "$WBS/index"
    const val LETTER_STATEMENT = "letter_statement"
    const val GET_ALL_USER = "$WBS/get_all_user"
    const val LOGIN = "$AUTH/employee/login"
    const val LOGIN_STUDENT = "$AUTH/student/login"
    const val BEARER = "Bearer "
    const val STUDENT = "student"
    const val LOGOUT = "auth/employee/logout"
    const val TYPE = "type"
    const val TYPE_ROLE  = "role"
    const val TYPE_EKINERJA = "E-Kinerja"

    const val NAME = "name"
    const val PHONE_PARENT = "phone_parent"
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
    const val IS_FIRST_TIME = "is_first_time"
    const val ID = "id"
    const val IMAGE_URL_NEWS = "https://m2mpekanbaru.sch.id/storage/uploads/"

    const val LETTER_TYPE = "TYPE_LETTER"
    const val UPDATE_PASSWORD = "$STUDENT/update_password"
    const val UPDATE_PASSWORD_EMPLOYEE = "$AUTH/update_password"
    const val UPDATE_PROFILE_EMPLOYEE = "$AUTH/user/update"
    const val UPDATE_PROFILE_STUDENT = "$STUDENT/profile"
    const val LIST_SONGKET_MOTHER = "songket_mother"
    const val TAG_UPDATE_SONGKET_MOTHER = "update_songket_mother"
    const val GET_STUDENT_CLASS = "$STUDENT/get_all_class"
    const val SHOW_PROFILE_EMPLOYEE = "$AUTH/show_employee/{id}"
    const val SHOW_PROFILE_STUDENT = "$AUTH/show_student/{id}"
    const val UPDATE_NUMBER_PHONE_PARENT = "$STUDENT/update_number_phone_parent"

    // Songket Emak GTK/Karyawan
    const val CREATE_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/create"
    const val GET_BY_STATUS = "$SONGKET_MOTHER/gtk/get_by_status"
    const val COUNT_STATUS_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/count_status"
    const val UPDATE_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/update_songket/{id}"
    const val ALL_LIST_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/all_list_songket_mother_employee"
    const val CREATE_SERVICE_SONGKET_MOTHER_GTK = "$SONGKET_MOTHER/gtk/create_service_songket_mother_gtk/{id}"

    // Songket Emak Student
    const val CREATE_SONGKET_MOTHER = "$SONGKET_MOTHER/store"
    const val LIST_BY_STATUS_SONGKET_MOTHER = "$SONGKET_MOTHER/filter_by_status"
    const val UPDATE_SONGKET_MOTHER = "$SONGKET_MOTHER/update_songket/{id}"
    const val STATUS_LETTER = "$SONGKET_MOTHER/count_status"
    const val CREATE_SERVICE_SONGKET_MOTHER = "$SONGKET_MOTHER/create_service_songket_mother/{id}"
    const val OFFICER_SERVICE_SONGKET_MOTHER = "$SONGKET_MOTHER/service_officer"

    // Get All Article For Slider
    const val GET_ARTICLE = "$AUTH/get_article"

    // E-Kinerja
    const val SHOW_ALL_EMPLOYEE_PERFORMANCE = "$E_KINERJA/index"
    const val CREATE_EMPLOYEE_PERFORMANCE = "$E_KINERJA/create"
    const val UPDATE_EMPLOYEE_PERFORMANCE = "$E_KINERJA/update/{id}"
    const val CREATE_REALITATION = "$E_KINERJA/create_realitation/{id}"
    const val HAS_APPROVED_TASK = "$E_KINERJA/has-approved-task"
    const val GET_TASK = "$E_KINERJA/task"

    // E-Tatib
    // Violation
    private const val VIOLATION = "violation"
    const val ALL_MASTER_VIOLATION = "$VIOLATION/all_violation_master"
    const val ALL_VIOLATION_STUDENT = "$VIOLATION/all_violation_student"
    const val CREATE_VIOLATION_STUDENT = "$VIOLATION/create_violation_student"
    const val CREATE_DISPUTE_VIOLATION = "$VIOLATION/create_dispute_violation/{id}"
    const val NOTE_DISPUTE_VIOLATION = "$VIOLATION/note_dispute_violation/{id}"
    const val TOTAL_POINT_STUDENT = "$VIOLATION/student/total_points"
    const val GET_ALL_STUDENT = "$VIOLATION/get_all_student"
    const val GET_ALL_SCHOOL_VIOLATION_MASTER = "$VIOLATION/get_all_school_violation_master"
    const val GET_ALL_TARGET_VIOLATION = "$VIOLATION/get_target_violation"

    // Absensi
    private const val ATTENDANCE_STUDENT = "attendance_student"
    const val ADD_ATTENDANCE_STUDENT = "$ATTENDANCE_STUDENT/store"
    const val INDEX_ATTENDANCE_STUDENT = "$ATTENDANCE_STUDENT/index"
    const val FILTER_ATTENDANCE_STUDENT_BY_MONTH = "$ATTENDANCE_STUDENT/filter/"
    const val GET_LAT_AND_LONG = "$ATTENDANCE_STUDENT/get_lat_and_long"
    const val GET_ATTENDANCE_TODAY = "$ATTENDANCE_STUDENT/getAttendanceToday"
    const val IS_FACE_DETECTED = "isFaceDetected"

//    TAG FOR API
    const val TAG_CREATE_SONGKET = "CreateSongketActivity"
}