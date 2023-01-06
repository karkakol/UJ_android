package com.example.models

import org.jetbrains.exposed.sql.*

data class User(val id: Int, val login: String, val password: String, val token: String = "", val registerSource:
String = "")
data class RegisteredUser(val login: String, val password: String, val repeatedPassword: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val login = varchar("login", 128)
    val password = varchar("password", 128).default("")
    val token = varchar("token", 2048).default("")
    val registerSource = varchar("registerSource", 128).default("")

    override val primaryKey = PrimaryKey(id)

}