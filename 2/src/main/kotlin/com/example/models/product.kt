package com.example.models

import com.example.models.Categories.references
import org.jetbrains.exposed.sql.*

data class Product(val id: Int,val categoryId: Int,  val name: String, val desc: String, val price: Double)


object Products : Table() {
    val id = integer("id").autoIncrement()
    val categoryId = reference("categoryId", Categories.id,ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val name = varchar("title", 128)
    val desc = varchar("desc", 1024)
    val price = double("price");
    override val primaryKey = PrimaryKey(id)


}