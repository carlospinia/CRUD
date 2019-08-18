package com.example.crud.userDetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Failure
import com.example.crud.*
import com.example.crud.base.BaseViewModel
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

    lateinit var user: UserApp

    private val undoList = MutableLiveData<MutableList<UserApp>>().default(mutableListOf())
    val getUndoList: LiveData<MutableList<UserApp>> = undoList

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

    fun editUser(userName: String, birthDate: Long) {
        loading.value = true
        user = UserApp(userName, birthDate.toDateRequest(), user.id ?: 0)
        undoList.value?.add(user)
        undoList.value = undoList.value
        editUser(user.toUser()){ it.fold(::handleEditUserFailure, ::handleEditUserSuccess)}
    }

    private fun handleEditUserSuccess(unit: Unit){
        loading.value = false
        editUserSuccess.value = true
    }

    private fun handleEditUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    fun getUser() {
        loading.value = true
        getUser(user.id ?: 0) { it.fold(::handleGetUserFailure, ::handleGetUserSuccess) }
    }

    private fun handleGetUserSuccess(user: User){
        loading.value = false
        getUserSuccess.value = user.toUserApp()
    }

    private fun handleGetUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    fun deleteUser() {
        loading.value = true
        deleteUser(user.id ?: 0) { it.fold(::handleDeleteUserFailure, ::handleDeleteUserSuccess) }
    }

    private fun handleDeleteUserSuccess(unit: Unit){
        loading.value = false
        deleteUserSuccess.value = true
    }

    private fun handleDeleteUserFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    fun initUndoList() {
        undoList.value?.add(user)
    }

    fun removeLastUndoItem() {
        undoList.value?.let{
            if (it.isNotEmpty()) {
                undoList.value?.removeAt(it.lastIndex)
                undoList.value = undoList.value
            }
        }
    }
}