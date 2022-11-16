package com.example.crud.userDetail

import android.os.Parcelable
import com.example.crud.UserApp
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface DetailScrenType: Parcelable {
    @Parcelize
    object AddUser: DetailScrenType
    @Parcelize
    class UserDetail(val user: UserApp): DetailScrenType
    @Parcelize
    class EditUser(val user: UserApp): DetailScrenType
}