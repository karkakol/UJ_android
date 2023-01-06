package com.example.a8zad.ui.login

import androidx.lifecycle.ViewModel
import com.example.a8zad.Api
import com.example.a8zad.AuthGithubApi
import com.example.a8zad.GithubApi
import com.example.a8zad.RetrofitHelper
import com.example.a8zad.data.model.User
import com.example.a8zad.data.model.api.ApiUserWrapper
import com.example.a8zad.data.model.api.login.LoginRequest
import com.example.a8zad.data.model.api.registerFromExternalProvider.RegisterFromExternalProviderRequest
import com.example.a8zad.data.model.api.registerFromExternalProvider.RegisterFromExternalProviderResponse
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.create

class LoginViewModel() : ViewModel() {
    val api = RetrofitHelper.getInstance(null).create(Api::class.java)
    val githubApi = RetrofitHelper.getInstance("https://github.com/").create(GithubApi::class.java)
    val authGithubApi =
        RetrofitHelper.getInstance("https://api.github.com/").create(AuthGithubApi::class.java)

    @OptIn(DelicateCoroutinesApi::class)
    fun login(login: String, password: String): ApiUserWrapper {
        val response = runBlocking {
            GlobalScope.async {
                val requestData = LoginRequest(
                    login, password
                )
                val response = api.login(requestData)
                return@async response
            }.await()
        }
        if (response.isSuccessful) {
            return ApiUserWrapper(
                User(
                    id = response.body()!!.user.id,
                    login = response.body()!!.user.login,
                    password = response.body()!!.user.password,
                    registerSource = "APP"
                ), error = null

            )
        }
        return ApiUserWrapper(
            user = null,
            error = response.errorBody()?.string() ?: "Nieznajomy błąd"
        )
    }

    fun getGithubData(clientId: String, clientSecret: String, code: String): ApiUserWrapper {
        val token = getGithubToken(clientId, clientSecret, code)
        if (token == null) return ApiUserWrapper(error = "Coś poszło nie tak", user = null)

        val response = runBlocking {
            GlobalScope.async {
                authGithubApi.getGithubUser(
                    authHeader = "Bearer $token"
                )
            }.await()
        }

        val serverResponse =
            registerWithExternalProvider(response.body()!!.login, "", token, "GITHUB")

        if (serverResponse.isSuccessful) {
            return ApiUserWrapper(
                User(
                    id = serverResponse.body()!!.user.id,
                    login = serverResponse.body()!!.user.login,
                    password = "GITHUB NIE DAJE HASŁA",
                    registerSource = "GITHUB"
                ), error = null

            )
        }
        return ApiUserWrapper(
            user = null,
            error = response.errorBody()?.string() ?: "Nieznajomy błąd"
        )

    }


    fun getGithubToken(clientId: String, clientSecret: String, code: String): String? {
        val response = runBlocking {
            GlobalScope.async {
                githubApi.githubAccessToken(
                    clientId = clientId,
                    secret = clientSecret,
                    code = code
                )
            }.await()
        }

        return response.body()?.accessToken

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun registerWithExternalProvider(
        login: String,
        password: String,
        token: String,
        registerSource: String
    ): Response<RegisterFromExternalProviderResponse> {
        val response = runBlocking {
            GlobalScope.async {
                val requestData = RegisterFromExternalProviderRequest(
                    login, password, registerSource, token
                )
                val response = api.registerFromExternalProvider(requestData)
                return@async response
            }.await()
        }
        return response
    }
}