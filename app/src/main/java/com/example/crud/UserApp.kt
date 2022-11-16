package com.example.crud

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserApp (
    val name: String?,
    val birthdate: String?,
    val id: Int?
): Parcelable
