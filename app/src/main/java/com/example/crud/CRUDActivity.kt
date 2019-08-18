package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class CRUDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showToolbar(show: Boolean) {
        if (show) {
            toolbar_detail.visibility = View.VISIBLE
            setSupportActionBar(toolbar_detail)
            toolbar_detail.setNavigationOnClickListener { onBackPressed() }
        } else {
            toolbar_detail.visibility = View.GONE
            supportActionBar?.hide()
        }
    }

    fun setTitle(title: String){ supportActionBar?.title = title }

}
