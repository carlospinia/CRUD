package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crud.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CRUDActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

}
