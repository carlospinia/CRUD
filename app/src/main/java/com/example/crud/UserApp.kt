package com.example.crud

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserApp (
    val name: String?,
    val birthdate: String?,
    val id: Int?
): Parcelable
