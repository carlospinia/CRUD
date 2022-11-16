package com.example.crud.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crud.UserApp
import com.example.crud.default
import com.example.crud.toUserApp
import com.example.domain.User
import com.example.domain.UserListFailure
import com.example.domain.loadUserList.LoadUserList
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserListVM @Inject constructor(
    private val loadUserList: LoadUserList
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    private val userList = MutableLiveData<List<UserApp>>().default(listOf())
    val getUserList: LiveData<List<UserApp>> = userList

    private val error = MutableLiveData<UserListFailure>()
    val getError: LiveData<UserListFailure> = error

    fun loadUserList() {
        loading.value = true
        loadUserList(Unit) { it.fold(::handleUserListFailure, ::handleUserListSuccess) }
    }

    private fun handleUserListFailure(failure: UserListFailure) {
        loading.value = false
        error.value = failure
    }

    private fun handleUserListSuccess(newUserList: List<User>) {
        loading.value = false
        val mutableListTemp = mutableListOf<UserApp>()
        for (user in newUserList) {
            mutableListTemp.add(user.toUserApp())
        }
        userList.value = mutableListTemp
    }

    fun filterUsers(newText: String): List<UserApp> {
        val text = newText.lowercase(Locale.getDefault())
        return userList.value?.filter { (name, birthdate) ->
            name?.lowercase(Locale.getDefault())?.contains(text) ?: false
                    || birthdate?.lowercase(Locale.getDefault())?.contains(text) ?: false
        } ?: listOf()
    }
}