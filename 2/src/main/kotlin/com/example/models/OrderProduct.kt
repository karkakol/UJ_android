package com.example.models

import com.example.models.Categories.references
import org.jetbrains.exposed.sql.*

data class OrderProduct(val id: Int,val orderId: Int,  val productId: Int, val quantity: Int, val productsValue: Double)


object OrderProducts : Table() {
    val id = integer("id").autoIncrement()
    val orderId = reference("orderId", Orders.id,ReferenceOption.NO_ACTION, ReferenceOption.NO_ACTION)
    val productId = reference("productId", Products.id,ReferenceOption.NO_ACTION, ReferenceOption.NO_ACTION)
    val quantity = integer("quantity")
    val productsValue = double("productsValue")

    override val primaryKey = PrimaryKey(id)
}