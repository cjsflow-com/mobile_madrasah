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
import com.example.man2superapp.source.local.model.GetClassStudent
import com.example.man2superapp.source.local.model.LocalSchoolViolationStudent
import com.example.man2superapp.source.local.model.LocalStudent
import com.example.man2superapp.source.local.model.LocalViolationMaster
import com.example.man2superapp.source.local.model.NewsArticle
import com.example.man2superapp.source.local.model.ResutlEmployeePerformance
import com.example.man2superapp.source.local.model.TaskUser
import com.example.man2superapp.source.local.model.toGenerateAllListArticle
import com.example.man2superapp.source.local.model.toGenerateAllResult
import com.example.man2superapp.source.local.model.toGenerateAllStudent
import com.example.man2superapp.source.local.model.toGenerateAllTask
import com.example.man2superapp.source.local.model.toGenerateAllUserWbs
import com.example.man2superapp.source.local.model.toGenerateClassStudent
import com.example.man2superapp.source.local.model.toGenerateListViolationMaster
import com.example.man2superapp.source.local.model.toGenerateListViolationStudent
import com.example.man2superapp.source.local.model.toNoteRejected
import com.example.man2superapp.source.network.States
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
    private val _clasList = MutableLiveData<List<GetClassStudent>>()
    private val _article = MutableLiveData<List<NewsArticle>>()
    private val _loading = MutableLiveData<Boolean>()
    private val _textError = MutableLiveData<String>()
    private val _textSucces = MutableLiveData<String>()
    private val _listEkinerja = MutableLiveData<List<ResutlEmployeePerformance>>()
    private val _hasApprovedTask = MutableLiveData<Boolean>()
    private val _listTask = MutableLiveData<List<TaskUser>>()
    private val _noteRejected = MutableLiveData<String>()
    private val _allViolationMaster = MutableLiveData<List<LocalViolationMaster>>()
    private val _allViolationStudent = MutableLiveData<List<LocalSchoolViolationStudent>>()
    private val _totalPoint = MutableLiveData<Int>()
    private val _allStudent = MutableLiveData<List<LocalStudent>>()
    private val _timeIn = MutableLiveData<String>()
    private val _timeOut = MutableLiveData<String>()

    val userList: LiveData<List<GetAllUserWbs>> get() = _userList
    val classList: LiveData<List<GetClassStudent>> get() = _clasList
    val article: LiveData<List<NewsArticle>> get() = _article
    val loading: LiveData<Boolean> get() = _loading
    val hasApprovedTask: LiveData<Boolean> get() = _hasApprovedTask
    val listTask: LiveData<List<TaskUser>> get() = _listTask
    val listEkinerja: LiveData<List<ResutlEmployeePerformance>> get() = _listEkinerja
    val textError: LiveData<String> get() = _textError
    val textSuccess: LiveData<String> get() = _textSucces
    val totalPoint:LiveData<Int> get() = _totalPoint
    val noteRejected: LiveData<String> get() = _noteRejected
    val allViolationMaster: LiveData<List<LocalViolationMaster>> get() = _allViolationMaster
    val allViolationStudent: LiveData<List<LocalSchoolViolationStudent>> get() = _allViolationStudent
    val allStudent: LiveData<List<LocalStudent>> get() = _allStudent
    val timeIn: LiveData<String> get() = _timeIn
    val timeOut: LiveData<String> get() = _timeOut


    fun loginEmployee(email: String, password: String) = repository.loginEmployee(email, password).asLiveData()
    fun getAllWbs(token: String) = repository.getAllWbs(token).asLiveData()
//    fun getAllUser() = repository.getAllUser().asLiveData()
    fun setTimeIn(time: String){
        _timeIn.value = time
    }
    fun setTimeOut(time: String){
        _timeOut.value = time
    }
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

    fun updatePasswordEmployee(token: String,password: String) = repository.updatePasswordEmployee(token,password).asLiveData()

    fun updateSongketMother(
        id: Int,
        nameActivityCompletition: String,
        organizerCompletition: String, nameEskul: String,
        nameClub: String,
        nameUniversity: String,major: String,ranking: String,
        semester: String,totalStudent: String,averageValue: Double
    ) = repository.updateSongketMother(id,nameActivityCompletition,organizerCompletition,nameEskul,nameClub,nameUniversity,major,ranking,semester,totalStudent,averageValue)
        .asLiveData()

    fun fetchAllClassStudent(context: Context){
        viewModelScope.launch {
            repository.getAllClassStudent().collect{ state ->
                when(state){
                    is States.Loading -> {}
                    is States.Success -> {
                        _clasList.value = state.data.data.toGenerateClassStudent()
                        Log.d("AllViewModel", "fetchAllClassStudent: ${state.data.message}")
                    }
                    is States.Failed -> {
                        Help.showToast(context,state.message)
                        Log.d("AllViewModel", "fetchAllClassStudent: ${state.message}")
                    }
                    else -> {}
                }
            }
        }
    }

    fun fetchTask(token: String,context: Context)
    {
        viewModelScope.launch {
            repository.getAllTaskEmployee(token).collect{
                when(it)
                {
                    is States.Loading -> {}
                    is States.Success -> {
                        _listTask.value = it.data.task.toGenerateAllTask()
                    }
                    is States.Failed -> {
                        Help.showToast(context,it.toString())
                    }
                    else -> {}
                }
            }
        }
    }

    fun fetchAllViolationMaster(token: String){
        viewModelScope.launch {
            repository.getAllMasterViolation(token).collect{
                when(it){
                    is States.Loading -> {_loading.value = true}
                    is States.Success -> {
                        _textSucces.value = it.data.message
                        _loading.value = false
                        _allViolationMaster.value = it.data.violationMaster.toGenerateListViolationMaster()
                    }
                    is States.Failed -> {
                        _loading.value = false
                        _textError.value = it.message.toString()
                    }
                    else -> {}
                }
            }
        }
    }

    fun fetchAllViolationSchoolStudent(token: String){
        viewModelScope.launch {
            repository.getAllViolationStudent(token).collect{
                when(it){
                    is States.Loading -> _loading.value = true
                    is States.Success -> {
                        _loading.value = false
                        _allViolationStudent.value = it.data.schoolViolationStudent.toGenerateListViolationStudent()
                    }
                    is States.Failed -> {
                        _loading.value = false
                        _textError.value = "Terjadi kesalahan pada siste => ${it.message}"
                    }
                    else -> {}
                }
            }
        }
    }

    fun fetchAllStudent(token: String){
        viewModelScope.launch {
            repository.getAllStudent(token).collect{
                when(it){
                    is States.Loading -> {}
                    is States.Success -> {
                        _allStudent.value = it.data.student.toGenerateAllStudent()
                    }
                    is States.Failed -> {_textError.value = it.message.toString()}
                }
            }
        }
    }

    fun createViolationStudent(token: String,studentId: Int?,schoolViolationStudentId: Int?) = repository.createViolationStudent(token,studentId,schoolViolationStudentId).asLiveData()
    fun createViolationDisputeStudent(token: String,id: Int,reason: String){
        viewModelScope.launch {
            repository.createViolationDisputeStudent(token,id,reason).collect{
                when(it){
                    is States.Loading -> {

                    }
                    is States.Success -> {
                        _textSucces.value = it.data.message
                    }
                    is States.Failed -> {
                        _textError.value = it.message.toString()
                    }

                    else -> {}
                }
            }
        }
    }
    fun getNoteDisputeViolation(token: String,id: Int){
        viewModelScope.launch {
            repository.getNoteDisputeViolation(token,id).collect{
                when(it){
                    is States.Loading -> {}
                    is States.Success -> {
                        val conversionNoteRejected = it.data.data.toNoteRejected()
                        _noteRejected.value = conversionNoteRejected.note
                    }
                    is States.Failed -> {
                        _textError.value = "Terjadi kesalahan => ${it.message}"
                    }
                    else -> {}
                }
            }
        }
    }

    fun getTargetViolation(token: String) = repository.getAllViolationTarget(token).asLiveData()

    fun getTotalPointStudent(token: String)
    {
        viewModelScope.launch {
            repository.getTotalPointStudent(token).collect{
                when(it){
                    is States.Loading -> {_loading.value = true}
                    is States.Success -> {
                        _loading.value = false
                        _totalPoint.value = it.data.totalPoints
                    }
                    is States.Failed -> {
                        _textError.value = it.message.toString()
                        _loading.value = false
                    }
                    else -> {}
                }
            }
        }
    }

    fun updatePhoneNumberParent(token: String,numberPhoneParent: String) = repository.updatePhoneNumberParent(token,numberPhoneParent).asLiveData()

    fun fetchApprovedTask(token: String,context: Context)
    {
        viewModelScope.launch {
            repository.hasApprovedTask(token).collect{
                when(it)
                {
                    is States.Loading -> {}
                    is States.Success -> {
                        _hasApprovedTask.value = it.data.hasApproved
                    }
                    is States.Failed -> {Help.showToast(context,it.toString())}
                    else -> {}
                }
            }
        }
    }

    fun fetchResultEmployeePerformance(token: String,context: Context)
    {
        viewModelScope.launch {
            repository.getAllEmployeePerformance(token).collect{state ->
                when(state)
                {
                    is States.Loading -> {
                        _loading.value = true
                    }
                    is States.Success -> {
                        _loading.value = false
                        _listEkinerja.value = state.data.result.toGenerateAllResult()
                    }
                    is States.Failed -> {
                        _loading.value = false
                        Help.showToast(context,state.message)
                    }
                    else -> {}
                }
            }
        }
    }

    fun createRealitation(token: String,id: Int, nameDataSupport: String, linkGoogleDrive: String)
        = repository.createRealitation(token,id,nameDataSupport,linkGoogleDrive).asLiveData()

    fun updateEmployeePerformance(token: String,id: Int,performanceEvaluationPlan: String, performanceTargetEvaluation: Int,
                                  nameDataSupport: String, linkGoogleDrive: String) = repository.updateEmployeePerformance(token,id,performanceEvaluationPlan,performanceTargetEvaluation,nameDataSupport,linkGoogleDrive).asLiveData()

    fun createEmployeePerformance(token: String, id: Int, performanceEvaluationPlan: String, performanceTargetEvaluation: Int)
     = repository.createEmployeePerformance(token,id,performanceEvaluationPlan,performanceTargetEvaluation).asLiveData()

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
                    else -> {}
                }
            }
        }
    }

    fun fetchAllOfficerService(context: Context){
        viewModelScope.launch {
            repository.getOfficerServiceSongketMother().collect{ state ->
                when(state)
                {
                    is States.Loading -> {}
                    is States.Success -> {
                        _userList.value = state.data.data.toGenerateAllUserWbs()
                    }
                    is States.Failed -> {
                        Help.showToast(context,state.message)
                        Log.d("AllViewModel", "fetchAllOfficerService: ${state.message}")
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateProfileStudent(token: String, name: String, email: String, placeBirthday: String, nisn: String, phoneNumber: String, classStudentId: Int, gender: Int, nameFather: String, nameMother: String, address: String, dateBirthday: String,phoneNumberParent: String)
        = repository.updateProfileStudent(token,name,email,placeBirthday,nisn,phoneNumber,classStudentId,gender,nameFather,nameMother,address,dateBirthday,phoneNumberParent).asLiveData()

    fun updateProfileEmployee(token: String,name: String,email: String,phoneNumber: String,gender: Int,position: String)
        = repository.updateProfileEmployee(token, name, email, phoneNumber, gender, position).asLiveData()

    fun getCountStatusSongketMotherGtk(token: String) = repository.getCountStatusSongketMotherGtk(token).asLiveData()

    fun getByStatusSongketMotherGtk(token: String,status: String) = repository.getByStatusSongketMotherGtk(token,status).asLiveData()

    fun getAllListSongketMotherGTK(token: String) = repository.getAllListSongketMotherGTK(token).asLiveData()

    fun createSongketMotherGtk(token: String,letterStatement: Int, rankOrGrade: String,nip: String, nuptk: String,
                               fielStudy: String, haveYourEverTaughtSubject: String,startHoliday: String,
                               endHoliday: String, recommendationTitle: String) =
        repository.createSongketMotherGtk(token,letterStatement,rankOrGrade,nip,nuptk,fielStudy,haveYourEverTaughtSubject,
            startHoliday,endHoliday,recommendationTitle).asLiveData()

    fun updateSongketMotherGtk(id: Int, rankOrGrade: String, nip: String, nuptk: String,
                               fieldStudy: String, haveYourEverTaughtSubject: String,startHoliday: String,endHoliday: String,titleRecomendation: String)
    = repository.updateSongketMotherGtk(id, rankOrGrade, nip, nuptk,fieldStudy, haveYourEverTaughtSubject, startHoliday, endHoliday,titleRecomendation)
        .asLiveData()

    fun showProfileEmployee(id: Int) = repository.showProfileEmployee(id).asLiveData()

    fun showProfileStudent(id: Int) = repository.showProfileStudent(id).asLiveData()

    fun getAllArticle(){
        viewModelScope.launch {
            repository.getAllArticle().collect{ state ->
                when(state){
                    is States.Loading -> {
                        _loading.value = true
                    }
                    is States.Success -> {
                        _loading.value = false
                        _article.value = state.data.article.toGenerateAllListArticle()
                    }
                    is States.Failed -> {
                        _loading.value = false
                        _textError.value = "Terjadi kesalahan pada sistem => ${state.message}"
                    }
                    else -> {}
                }
            }
        }
    }

    fun createServiceSongketMother(id: Int, employeeId: Int, ratingService: Int) =
        repository.createServiceSongketMother(id,employeeId,ratingService).asLiveData()

    fun createServiceSongketMotherGtk(id: Int, employeeId: Int, ratingService: Int) =
        repository.createServiceSongketMotherGtk(id,employeeId,ratingService).asLiveData()

    fun addAttendanceToday(token: String) = repository.addAttendanceToday(token).asLiveData()

    fun getAttendanceToday(token: String) = repository.getAttendanceToday(token).asLiveData()

    fun getLatAndLong(token: String) = repository.getLatAndLong(token).asLiveData()

    fun indexAllAttendanceToday(token: String) = repository.indexAttendanceToday(token).asLiveData()
}