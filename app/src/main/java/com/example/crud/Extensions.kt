package com.example.crud

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.fragment.findNavController
import com.example.domain.User

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun String.datePrettyFormat(): String {
    val cal = Calendar.getInstance()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return format.format(cal.time)
}

fun String.dateToRequestFormat(): String? =
    try {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        cal.time = sdf.parse(this)!!
        inSdf.format(cal.time)
    } catch (e: Exception){
        null
    }

fun String.timeInMillisFromRequestDate(): Long {
    val cal = Calendar.getInstance()
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return cal.timeInMillis
}

fun String.timeInMillisFromPretty(): Long {
    val cal = Calendar.getInstance()
    val inSdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return cal.timeInMillis
}

fun Long.toDateRequest(): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return inSdf.format(cal.time)
}

fun Long.toDatePretty(): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    val inSdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return inSdf.format(cal.time)
}

fun DatePicker.setDateFromMillis(timeMillis: Long){
    val cal = Calendar.getInstance()
    cal.timeInMillis = timeMillis
    this.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
}

fun DatePicker.getDateInMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(this.year, this.month, this.dayOfMonth)
    return calendar.timeInMillis
}

fun Fragment.navigateTo(@IdRes navActionResId: Int, bundle: Bundle, popUpTo: Int? = null, inclusive: Boolean = false) {
    try {
        if (popUpTo != null) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(popUpTo, inclusive)
                .setEnterAnim(R.anim.anim_enter_from_left)
                .setExitAnim(R.anim.anim_exit_to_left)
                .setPopEnterAnim(R.anim.anim_pop_from_right)
                .setPopExitAnim(R.anim.anim_pop_to_right)
                .build()

            findNavController().navigate(navActionResId, bundle, navOptions)
        } else {
            findNavController().navigate(navActionResId, bundle)
        }
    } catch (ignored: Exception) {
        // Navigation library has a bug which crash if same navActionResId is passed too quickly so we implemented this try / catch
    }
}

fun Fragment.showNetworkConnectionError(){
    Toast.makeText(context, getString(R.string.network_error_message), Toast.LENGTH_LONG).show()
}

fun Fragment.showServerError(){
    Toast.makeText(context, getString(R.string.server_error_message), Toast.LENGTH_LONG).show()
}

fun User.toUserApp() = UserApp(this.name, this.birthdate, this.id)
fun UserApp.toUser() = User(this.name, this.birthdate, this.id)