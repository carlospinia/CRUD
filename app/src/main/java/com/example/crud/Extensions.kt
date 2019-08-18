package com.example.crud

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.fragment.findNavController
import com.example.domain.User

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun String.toDateFormatted(format: SimpleDateFormat): String {
    val cal = Calendar.getInstance()
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return format.format(cal.time)
}

fun String.timeInMillis(): Long {
    val cal = Calendar.getInstance()
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    cal.time = inSdf.parse(this)
    return cal.timeInMillis
}

fun Long.toDateRequest(): String {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    val inSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return inSdf.format(cal.time)
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

fun User.toUserApp() = UserApp(this.name, this.birthdate, this.id)
fun UserApp.toUser() = User(this.name, this.birthdate, this.id)