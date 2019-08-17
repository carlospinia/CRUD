package com.example.crud

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun String.toDateFormatted(format: SimpleDateFormat): String {
    val cal = Calendar.getInstance()
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return format.format(cal?.time)
}