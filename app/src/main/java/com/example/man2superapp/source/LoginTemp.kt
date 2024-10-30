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

    suspend fun getToken() = flow{
        val token = context.userStore.data.first()[TOKEN_KEY]?: ""
        val name = context.userStore.data.first()[NAME_KEY]?: ""
        val email = context.userStore.data.first()[EMAIL_KEY]?: ""
        val gender = context.userStore.data.first()[GENDER_KEY]?: 0
        val profile = context.userStore.data.first()[PROFILE_KEY]?: ""
        val role = context.userStore.data.first()[ROLE_KEY]?: ""
        emit(LoginModel(name, email, gender, token, profile, role))
    }

    suspend fun putToken(loginModel: LoginModel)
    {
        context.userStore.edit { preferences ->
            loginModel.token?.let { preferences[TOKEN_KEY] = it }
            loginModel.name?.let { preferences[NAME_KEY] = it }
            loginModel.email?.let { preferences[EMAIL_KEY] = it }
            loginModel.gender?.let { preferences[GENDER_KEY] = it }
            loginModel.profile?.let { preferences[PROFILE_KEY] = it }
            loginModel.role?.let { preferences[ROLE_KEY] = it }
        }
    }

}