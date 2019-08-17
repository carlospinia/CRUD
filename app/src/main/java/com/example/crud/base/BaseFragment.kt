package com.example.crud.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.crud.R
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected val viewModel: VM by viewModelByClass(this.getViewModel())

    abstract fun getLayout(): Int
    abstract fun getViewModel(): KClass<VM>

    private var progress: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loading.observe(this, Observer { showLoading ->
            if (showLoading) showLoading()
            else dismissLoading()
        })
    }

    private fun showLoading() {
        if (progress == null) {
            progress = createLoading()
            progress?.show()
        }
    }

    private fun dismissLoading() {
        progress?.dismiss()
        progress = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
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

    fun showNetworkConnectionError(){
        Toast.makeText(context, getString(R.string.network_error_message), Toast.LENGTH_LONG).show()
    }

    fun showServerError(){
        Toast.makeText(context, getString(R.string.server_error_message), Toast.LENGTH_LONG).show()
    }
}
