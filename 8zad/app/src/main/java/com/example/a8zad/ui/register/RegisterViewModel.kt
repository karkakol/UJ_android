package com.example.a8zad.ui.register

import androidx.lifecycle.ViewModel

import com.example.a8zad.Api
import com.example.a8zad.RetrofitHelper
import com.example.a8zad.data.model.api.register.RegisterRequest
import com.example.a8zad.data.model.api.register.RegisterResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class RegisterViewModel() : ViewModel() {
    val api = RetrofitHelper.getInstance(null).create(Api::class.java)

    @OptIn(DelicateCoroutinesApi::class)
    fun register(login: String, password: String, repeatedPassword:String) : Response<RegisterResponse> {
        val response = runBlocking {
            GlobalScope.async {
                val requestData = RegisterRequest(
                    login, password, repeatedPassword
                )
                return@async api.register(requestData)
            }.await()
        }

        return response
    }

}