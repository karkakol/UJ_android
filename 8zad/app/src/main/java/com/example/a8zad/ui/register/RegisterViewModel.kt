package com.example.a8zad.ui.register

import androidx.lifecycle.ViewModel

import com.example.a8zad.Api
import com.example.a8zad.R
import com.example.a8zad.RetrofitHelper
import com.example.a8zad.data.model.api.register.RegisterRequest
import com.example.a8zad.data.model.api.register.RegisterResponse
import com.example.a8zad.utils.EmailValidator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class RegisterViewModel : ViewModel() {
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

    fun validateFields(email: String, password: String, repeatedPassword: String): Int?{
        if(isEmpty(email))
            return R.string.empty_login

        if(isEmpty(password))
            return R.string.empty_password

        if(isEmpty(repeatedPassword))
            return R.string.repeated_empty_password

        if(lengthLessThan5(email))
            return R.string.login_too_short

        if(lengthLessThan5(password))
            return R.string.password_too_short

        if(lengthLessThan5(repeatedPassword))
            return R.string.repeated_password_too_short

        if(password != repeatedPassword)
            return R.string.different_passwords

        if(isEmailInValid(email))
            return R.string.email_not_valid

        return null
    }

    private fun isEmpty(text: String): Boolean{
        return text.isEmpty()
    }

    private fun lengthLessThan5(text: String): Boolean{
        return text.length < 5
    }

    private fun isEmailInValid(email: String): Boolean {
        return !EmailValidator.validate(email)
    }

}