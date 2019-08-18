package com.example.crud.userList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Failure
import com.example.crud.UserApp
import com.example.crud.base.BaseViewModel
import com.example.crud.default
import com.example.crud.toUserApp
import com.example.domain.User
import com.example.domain.loadUserList.LoadUserList
import java.util.*

class UserListVM(application: Application,
                 private val loadUserList: LoadUserList) : BaseViewModel(application) {

    private val userList = MutableLiveData<List<UserApp>>().default(listOf())
    val getUserList : LiveData<List<UserApp>> = userList

    private val error = MutableLiveData<Failure>()
    val getError : LiveData<Failure> = error

    fun loadUserList(){
        loading.value = true
        loadUserList(Unit) { it.fold(::handleUserListFailure, ::handleUserListSuccess) }
    }

    private fun handleUserListFailure(failure: Failure){
        loading.value = false
        error.value = failure
    }

    private fun handleUserListSuccess(newUserList: List<User>){
        loading.value = false
        val mutableListTemp = mutableListOf<UserApp>()
        for (user in newUserList) { mutableListTemp.add(user.toUserApp()) }
        userList.value = mutableListTemp
    }

    fun filterUsers(newText: String): List<UserApp> {
        val text = newText.toLowerCase(Locale.getDefault())
        return userList.value?.filter { (name, birthdate) ->
            name?.toLowerCase(Locale.getDefault())?.contains(text) ?: false
                    || birthdate?.toLowerCase(Locale.getDefault())?.contains(text) ?: false
        } ?: listOf()
    }
}