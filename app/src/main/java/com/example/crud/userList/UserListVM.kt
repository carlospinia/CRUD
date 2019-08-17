package com.example.crud.userList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Failure
import com.example.crud.base.BaseViewModel
import com.example.crud.default
import com.example.domain.User
import com.example.domain.getUserList.LoadUserList

class UserListVM(application: Application,
                 private val loadUserList: LoadUserList) : BaseViewModel(application) {

    private val userList = MutableLiveData<List<User>>().default(listOf())
    val getUserList : LiveData<List<User>> = userList

    fun loadUserList(){
        loading.value = true
        loadUserList(Unit) { it.fold(::handleUserListFailure, ::handleUserListSuccess) }
    }

    private fun handleUserListFailure(failure: Failure){
        loading.value = false
    }

    private fun handleUserListSuccess(newUserList: List<User>){
        loading.value = false
        userList.value = newUserList
    }
}