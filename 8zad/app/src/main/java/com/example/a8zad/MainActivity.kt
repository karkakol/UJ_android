package com.example.a8zad

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.a8zad.notification.AppNotification
import com.example.a8zad.ui.login.LoginViewModel
import com.example.a8zad.ui.main.MainFragment
import com.example.a8zad.ui.main.MainViewModel
import com.example.a8zad.ui.register.RegisterViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppNotification.createImportantNotificationChannel(this)
        AppNotification.createNotificationChannel(this)
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