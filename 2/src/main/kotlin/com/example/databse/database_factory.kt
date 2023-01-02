package com.example.databse

import com.example.databse.category.DAOCategoryFacadeImpl
import com.example.databse.product.DAOProductFacade
import com.example.databse.product.DAOProductFacadeImpl
import com.example.models.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)


        transaction(database) {
            SchemaUtils.create(Categories)
            SchemaUtils.create(Products)
            SchemaUtils.create(Users)
            runBlocking {
                createInitData()
            }
        }

    }

    suspend fun createInitData(){
        val d1 = DAOCategoryFacadeImpl();
        if(d1.allCategories().isEmpty()){
            d1.addNewCategory("AGD", "Sprzęty, mikrofale", "Czerwony")
        }

        val d2 = DAOProductFacadeImpl();
        if(d2.allProducts().isEmpty()){
            d2.addNewProduct(1,"The drive to develop!", "...it's what keeps me going.", 42.69)
        }

    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}