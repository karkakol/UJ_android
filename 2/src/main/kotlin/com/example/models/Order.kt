package com.example.models

import com.example.models.Categories.references
import org.jetbrains.exposed.sql.*

data class Order(val id: Int,val userId: Int,  val date: String, val orderValue: Double)


object Orders : Table() {
    val id = integer("id").autoIncrement()
    val userId = reference("userId", Users.id,ReferenceOption.NO_ACTION, ReferenceOption.NO_ACTION)
    val date = varchar("date", 128)
    val orderValue = double("orderValue").default(0.0)

    override val primaryKey = PrimaryKey(id)
}