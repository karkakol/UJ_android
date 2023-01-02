package com.example.a8zad.data.model.api.login

data class LoginResponse (val user: LoginResponseContent)
data class LoginResponseContent (val login: String, val password: String)