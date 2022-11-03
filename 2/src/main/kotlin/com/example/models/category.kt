package com.example.models

import org.jetbrains.exposed.sql.*

data class Category(val id: Int, val name: String, val desc: String, val color: String)

object Categories : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val desc = varchar("desc", 1024)
    val color = varchar("color", 128)

    override val primaryKey = PrimaryKey(id)
}