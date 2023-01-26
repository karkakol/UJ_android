package com.example.a8zad.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.a8zad.Api
import com.example.a8zad.RetrofitHelper
import kotlinx.coroutines.runBlocking

class AppBroadcastReceiver: BroadcastReceiver() {
    private var api = RetrofitHelper.getInstance(null).create(Api::class.java)
    override fun onReceive(context: Context?, intent: Intent?) {
        val text = AppNotification.getMessage(intent ?: return).toString()

        Log.d("NotificationReceiver", text) // we will just log the user input for now

        var serverResponse = ""
        runBlocking {
            val response = api.getDiscount(text)
            if(response.isSuccessful){
                serverResponse = response.body()!!
            }else{
                serverResponse = "Błąd połączenia"
            }
            Log.d("NotificationReceiver", serverResponse) // we will just log the user input for now

            AppNotification.replyUser(context ?: return@runBlocking, serverResponse)
        }
    }
}