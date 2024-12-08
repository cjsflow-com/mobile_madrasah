package com.example.man2superapp.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.man2superapp.source.local.model.LoginModel
import com.example.man2superapp.source.network.di.userStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class LoginTemp constructor(private val context: Context) {
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val NAME_KEY = stringPreferencesKey("name")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val GENDER_KEY = intPreferencesKey("gender")
    private val PROFILE_KEY = stringPreferencesKey("profile")
    private val ROLE_KEY = stringPreferencesKey("role")
    private val NISN = stringPreferencesKey("nisn")
    private val ClASS_NAME = stringPreferencesKey("class_name")
    private val NUMBER_PHONE = stringPreferencesKey("number_phone")
    private val POSITION = stringPreferencesKey("position")
    private val FATHER = stringPreferencesKey("father")
    private val MOTHER = stringPreferencesKey("mother")
    private val ID = intPreferencesKey("id")
    private val ADDRESS = stringPreferencesKey("address")
    private val DATE_BIRHTDAY = stringPreferencesKey("date_birthday")
    private val PLACE_BIRTHDAY = stringPreferencesKey("place_birthday")

    suspend fun getToken() = flow{
        val token = context.userStore.data.first()[TOKEN_KEY]?: ""
        val name = context.userStore.data.first()[NAME_KEY]?: ""
        val email = context.userStore.data.first()[EMAIL_KEY]?: ""
        val gender = context.userStore.data.first()[GENDER_KEY]?: 0
        val profile = context.userStore.data.first()[PROFILE_KEY]?: ""
        val role = context.userStore.data.first()[ROLE_KEY]?: ""
        val nisn = context.userStore.data.first()[NISN]?: ""
        val id = context.userStore.data.first()[ID]?: 0
        val class_name = context.userStore.data.first()[ClASS_NAME]?: ""
        val nameMother = context.userStore.data.first()[MOTHER]?: ""
        val nameFather = context.userStore.data.first()[FATHER]?: ""
        val address = context.userStore.data.first()[ADDRESS]?: ""
        val dateBirthday = context.userStore.data.first()[DATE_BIRHTDAY]?: ""
        val placeBirthday = context.userStore.data.first()[PLACE_BIRTHDAY]?: ""
        val numberPhone = context.userStore.data.first()[NUMBER_PHONE]?: ""
        val position = context.userStore.data.first()[POSITION]?: ""
        emit(LoginModel(name,id, email,nisn,class_name,gender, token, profile, nameMother,nameFather,numberPhone,position,address,dateBirthday,placeBirthday, role))
    }

    suspend fun putToken(loginModel: LoginModel)
    {
        context.userStore.edit { preferences ->
            loginModel.token?.let { preferences[TOKEN_KEY] = it }
            loginModel.name?.let { preferences[NAME_KEY] = it }
            loginModel.id?.let { preferences[ID] = it}
            loginModel.email?.let { preferences[EMAIL_KEY] = it }
            loginModel.nisn?.let { preferences[NISN] = it }
            loginModel.class_name?.let { preferences[ClASS_NAME] = it }
            loginModel.gender?.let { preferences[GENDER_KEY] = it }
            loginModel.profile?.let { preferences[PROFILE_KEY] = it }
            loginModel.mother?.let { preferences[MOTHER] = it }
            loginModel.father?.let { preferences[FATHER] = it }
            loginModel.number_phone?.let { preferences[NUMBER_PHONE] = it }
            loginModel.posititon?.let { preferences[POSITION] = it }
            loginModel.address?.let { preferences[ADDRESS] = it }
            loginModel.dateBirthday?.let { preferences[DATE_BIRHTDAY] = it}
            loginModel.placeBirthday?.let { preferences[PLACE_BIRTHDAY] = it}
            loginModel.role?.let { preferences[ROLE_KEY] = it }
        }
    }

}