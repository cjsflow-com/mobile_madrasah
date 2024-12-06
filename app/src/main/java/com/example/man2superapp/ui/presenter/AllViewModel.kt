package com.example.man2superapp.ui.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.man2superapp.repository.Repository
import com.example.man2superapp.source.local.model.GetAllUserWbs
import com.example.man2superapp.source.local.model.toGenerateAllUserWbs
import com.example.man2superapp.source.network.States
import com.example.man2superapp.source.network.response.wbs.DataUser
import com.example.man2superapp.source.network.response.wbs.GetAllUserResponse
import com.example.man2superapp.utils.Help
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
@HiltViewModel
class AllViewModel @Inject constructor(private val repository: Repository): ViewModel()
{
    private val _userList = MutableLiveData<List<GetAllUserWbs>>()

    val userList: LiveData<List<GetAllUserWbs>> get() = _userList

    fun loginEmployee(email: String, password: String) = repository.loginEmployee(email, password).asLiveData()
    fun getAllWbs(token: String) = repository.getAllWbs(token).asLiveData()
//    fun getAllUser() = repository.getAllUser().asLiveData()
    fun createWbs(complainTopic: RequestBody, estimatedOfDateOfOccurrence: RequestBody, relatedOfficialsId: RequestBody,
                  workUnit: RequestBody, workLocation: RequestBody,
                  complainDescription: RequestBody, documentation: MultipartBody.Part?) =
        repository.createWbs(complainTopic,estimatedOfDateOfOccurrence,relatedOfficialsId,workUnit,
            workLocation,complainDescription,documentation).asLiveData()

    fun listSongketMotherByStatus(studentId: Int, status: String) = repository.listSongketMotherByStatus(studentId,status)
        .asLiveData()

    fun createSongketMother(letterStatement: Int, nameActivityCompletition: String,
                            organizerCompletition: String, nameEskul: String,
                            nameClub: String,
                            studentId: Int,nameUniversity: String,major: String,ranking: String,
                            semester: String,totalStudent: String,averageValue: Double ) =
        repository.createSongketMother(letterStatement, nameActivityCompletition, organizerCompletition, nameEskul, nameClub, studentId,
            nameUniversity,major, ranking, semester, totalStudent, averageValue)
            .asLiveData()

    fun loginStudent(nisn: String,password: String) = repository.loginStudent(nisn,password).asLiveData()

    fun getCountStatus(id: Int) = repository.getCountStatus(id).asLiveData()

    fun updatePasswordStudent(token: String, password: String) = repository.updatePassword(token,password).asLiveData()

    fun updateSongketMother(
        id: Int,
        nameActivityCompletition: String,
        organizerCompletition: String, nameEskul: String,
        nameClub: String,
        nameUniversity: String,major: String,ranking: String,
        semester: String,totalStudent: String,averageValue: Double
    ) = repository.updateSongketMother(id,nameActivityCompletition,organizerCompletition,nameEskul,nameClub,nameUniversity,major,ranking,semester,totalStudent,averageValue,)

    fun fetchAllUsers(context: Context)
    {
        viewModelScope.launch {
            repository.getAllUser().collect{ state ->
                when(state)
                {
                    is States.Loading -> {

                    }
                    is States.Success -> {
                        _userList.value = state.data.data.toGenerateAllUserWbs()
//                        _userList.postValue(state.data.data.toGenerateAllUserWbs())
//                        Log.d("TAG", "fetchAllUsers: ${state.data.data}")
                    }
                    is States.Failed -> {
                        Help.showToast(context,state.message)
                        Log.d("AllViewModel", "fetchAllUsers: ${state.message}")
                    }
                }
            }
        }
    }
}