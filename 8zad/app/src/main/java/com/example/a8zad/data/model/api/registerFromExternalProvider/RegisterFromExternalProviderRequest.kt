package com.example.a8zad.data.model.api.registerFromExternalProvider

data class RegisterFromExternalProviderRequest(
    val login: String,
    val password: String,
    val registerSource: String,
    val token: String,
)