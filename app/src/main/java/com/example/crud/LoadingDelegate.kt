package com.example.crud

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/* 
    Created by Carlos Piña on 15/11/22.
    Copyright (c) 2022 ElConfidencial. All rights reserved.
*/

class LoadingDelegate: ReadWriteProperty<Fragment, Boolean> {

    private var isShown = false
    private var loadingDialog: AlertDialog? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean {
        return isShown
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: Boolean) {
        if (value && loadingDialog == null) {
            loadingDialog = thisRef.createLoading()
            loadingDialog?.show()
            return
        }

        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun Fragment.createLoading(): AlertDialog? {
        return this.activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(R.layout.loading_lay)
            builder.setCancelable(false)
            builder.create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }
}