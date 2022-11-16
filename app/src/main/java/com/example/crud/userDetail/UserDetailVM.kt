package com.example.crud.userDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud.*
import com.example.domain.User
import com.example.domain.createUser.CreateUser
import com.example.domain.UserFailure
import com.example.domain.deleteUser.DeleteUser
import com.example.domain.editUser.EditUser
import com.example.domain.getUser.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailVM @Inject constructor(
    private val createUser: CreateUser,
    private val editUser: EditUser,
    private val getUser: GetUser,
    private val deleteUser: DeleteUser
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val createUserSuccess = MutableLiveData<Boolean>()
    val getCreateUserSuccess: LiveData<Boolean> = createUserSuccess

    private val editUserSuccess = MutableLiveData<Boolean>()
    val getEditUserSuccess: LiveData<Boolean> = editUserSuccess

    private val getUserSuccess = MutableLiveData<UserApp>()
    val getGetUserSuccess: LiveData<UserApp> = getUserSuccess

    private val deleteUserSuccess = MutableLiveData<Boolean>()
    val getDeleteUserSuccess: LiveData<Boolean> = deleteUserSuccess

    private val error = MutableLiveData<UserFailure>()
    val getError: LiveData<UserFailure> = error

    private val _uiState: MutableStateFlow<DetailScrenType> =
        MutableStateFlow(DetailScrenType.AddUser)
    val uiState: StateFlow<DetailScrenType> = _uiState.asStateFlow()

    lateinit var user: UserApp

    private val undoList = MutableLiveData<MutableList<UserApp>>().default(mutableListOf())
    val getUndoList: LiveData<MutableList<UserApp>> = undoList

    fun setUiState(uiState: DetailScrenType) = viewModelScope.launch {
        when (uiState) {
            is DetailScrenType.EditUser -> {
                user = uiState.user
                initUndoList()
            }
            is DetailScrenType.UserDetail -> user = uiState.user
            DetailScrenType.AddUser -> {} //do nothing
        }
        _uiState.value = uiState
    }

    fun onSaveButtonClicked(userName: String, birthDate: String) {
        if (uiState.value is DetailScrenType.AddUser) {
            createUser(userName, birthDate)
            return
        }

        editUser(userName, birthDate)
    }

    private fun createUser(userName: String, birthDate: String) {
        loading.value = true
        birthDate.dateToRequestFormat()?.let { birthdate ->
            createUser(User(userName, birthdate, null)) {
                it.fold(::handleCreateUserFailure, ::handleCreateUserSuccess)
            }
        } ?: run {
            handleCreateUserFailure(UserFailure.InvalidDateFormat)
        }
    }

    private fun handleCreateUserFailure(failure: UserFailure) {
        loading.value = false
        error.value = failure
    }

    private fun handleCreateUserSuccess(unit: Unit) {
        loading.value = false
        createUserSuccess.value = true
    }

    private fun editUser(userName: String, birthDate: String) {
        loading.value = true
        user = UserApp(userName, birthDate.dateToRequestFormat(), user.id ?: 0)
        undoList.value?.add(user)
        undoList.value = undoList.value
        editUser(user.toUser()) { it.fold(::handleEditUserFailure, ::handleEditUserSuccess) }
    }

    private fun handleEditUserSuccess(unit: Unit) {
        loading.value = false
        editUserSuccess.value = true
        _uiState.value = DetailScrenType.UserDetail(user)
    }

    private fun handleEditUserFailure(failure: UserFailure) {
        loading.value = false
        error.value = failure
    }

    fun getUser() {
        loading.value = true
        getUser(user.id ?: 0) { it.fold(::handleGetUserFailure, ::handleGetUserSuccess) }
    }

    private fun handleGetUserSuccess(user: User) {
        loading.value = false
        getUserSuccess.value = user.toUserApp()
    }

    private fun handleGetUserFailure(failure: UserFailure) {
        loading.value = false
        error.value = failure
    }

    fun onDeleteButtonClicked() {
        loading.value = true
        deleteUser(user.id ?: 0) { it.fold(::handleDeleteUserFailure, ::handleDeleteUserSuccess) }
    }

    private fun handleDeleteUserSuccess(unit: Unit) {
        loading.value = false
        deleteUserSuccess.value = true
    }

    private fun handleDeleteUserFailure(failure: UserFailure) {
        loading.value = false
        error.value = failure
    }

    private fun initUndoList() {
        undoList.value?.add(user)
    }

    fun onUndoButtonClicked() {
        undoList.value?.let {
            if (it.isNotEmpty()) {
                undoList.value?.removeAt(it.lastIndex)
                undoList.value = undoList.value
            }
        }
    }
}