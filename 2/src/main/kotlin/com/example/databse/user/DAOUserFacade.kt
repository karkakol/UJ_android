package com.example.databse.user

import com.example.models.*
import java.math.BigDecimal

interface DAOUserFacade {

    suspend fun login(login: String, password: String): User?
    suspend fun register( login: String, password: String): User?
    suspend fun registerUserFromOtherSource( user: User): User?
    suspend fun userByLogin( login: String ): User?
    suspend fun allUsers( ): List<User>
}