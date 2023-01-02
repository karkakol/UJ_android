package com.example.databse.user

import com.example.databse.DatabaseFactory.dbQuery
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

class DAOUserFacadeImpl : DAOUserFacade {
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        login = row[Users.login],
        password = row[Users.password],
        token = row[Users.token],
        registerSource = row[Users.registerSource]

    )

    override suspend fun login(login: String, password: String): User? = dbQuery {
        Users
            .select { (Users.login eq login) and (Users.password eq password) }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun register(login: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.login] = login
            it[Users.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun registerUserFromOtherSource(user: User): User? = dbQuery {
        val databseUser = Users.select{
            (Users.login) eq user.login
        }.map(::resultRowToUser)
            .singleOrNull()


        if(databseUser != null){
             Users.update({ Users.id eq databseUser.id }) {
                it[registerSource] = user.registerSource
                it[token] = user.token
            }
            val updatedUser = Users.select{
                (Users.login) eq user.login
            }.map(::resultRowToUser).singleOrNull()

            return@dbQuery updatedUser
        }else{
            val insertedUser = Users.insert {
                it[login] = user.login
                it[registerSource] = user.registerSource
                it[token] = user.token
            }
            insertedUser.resultedValues?.singleOrNull()?.let(::resultRowToUser)
        }




    }

    override suspend fun userByLogin(login: String): User? = dbQuery {
        Users
            .select { (Users.login eq login) }
            .map(::resultRowToUser)
            .firstOrNull()
    }

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }


}

val daoUser: DAOUserFacade = DAOUserFacadeImpl()