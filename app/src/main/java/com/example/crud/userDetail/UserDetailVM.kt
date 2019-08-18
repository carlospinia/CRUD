package com.example.crud.userDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Failure
import com.example.crud.UserApp
import com.example.crud.base.BaseViewModel
import com.example.crud.toDateRequest
import com.example.crud.toUserApp
import com.example.domain.User
import com.example.domain.createUser.CreateUser
import com.example.domain.deleteUser.DeleteUser
import com.example.domain.editUser.EditUser
import com.example.domain.getUser.GetUser

class UserDetailVM(application: Application,
                   private val createUser: CreateUser,
                   private val editUser: EditUser,
                   private val getUser: GetUser,
                   private val deleteUser: DeleteUser
) : BaseViewModel(application) {

    private val createUserSuccess = MutableLiveData<Boolean>()
    val getCreateUserSuccess : LiveData<Boolean> = createUserSuccess

    private val editUserSuccess = MutableLiveData<Boolean>()
    val getEditUserSuccess : LiveData<Boolean> = editUserSuccess

    private val getUserSuccess = MutableLiveData<UserApp>()
    val getGetUserSuccess : LiveData<UserApp> = getUserSuccess

    private val deleteUserSuccess = MutableLiveData<Boolean>()
    val getDeleteUserSuccess : LiveData<Boolean> = deleteUserSuccess

    private val error = MutableLiveData<Failure>()
    val getError : LiveData<Failure> = error

    fun createUser(userName: String, birthDate: Long) {
        loading.value = true
        createUser(User(userName, birthDate.toDateRequest(), null)) { it.fold(::handleCreateUserFailure, ::handleCreateUserSuccess)}
    }

    private fun handleCreateUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    private fun handleCreateUserSuccess(unit: Unit){
        loading.value = false
        createUserSuccess.value = true
    }

    fun editUser(userName: String, birthDate: Long, userId: Int) {
        loading.value = true
        editUser(User(userName, birthDate.toDateRequest(), userId)){ it.fold(::handleEditUserFailure, ::handleEditUserSuccess)}
    }

    private fun handleEditUserSuccess(unit: Unit){
        loading.value = false
        editUserSuccess.value = true
    }

    private fun handleEditUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    fun getUser(userId: Int) {
        loading.value = true
        getUser(userId) { it.fold(::handleGetUserFailure, ::handleGetUserSuccess) }
    }

    private fun handleGetUserSuccess(user: User){
        loading.value = false
        getUserSuccess.value = user.toUserApp()
    }

    private fun handleGetUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    fun deleteUser(userId: Int) {
        loading.value = true
        deleteUser(userId) { it.fold(::handleDeleteUserFailure, ::handleDeleteUserSuccess) }
    }

    private fun handleDeleteUserSuccess(unit: Unit){
        loading.value = false
        deleteUserSuccess.value = true
    }

    private fun handleDeleteUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }
}