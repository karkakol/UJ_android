package com.example.a8zad.data.model.api.registerFromExternalProvider

data class RegisterFromExternalProviderResponse (val user: RegisterFromExternalProviderResponseContent)
data class RegisterFromExternalProviderResponseContent (val login: String, val password: String,val registerSource: String, val token: String)