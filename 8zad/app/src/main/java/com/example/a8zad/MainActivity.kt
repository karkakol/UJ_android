package com.example.a8zad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.a8zad.ui.login.LoginViewModel
import com.example.a8zad.ui.main.MainFragment
import com.example.a8zad.ui.main.MainViewModel
import com.example.a8zad.ui.register.RegisterViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(LoginViewModel::class.java)

        ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(RegisterViewModel::class.java)

        ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
    }
}