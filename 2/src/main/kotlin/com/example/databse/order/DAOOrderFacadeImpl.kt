package com.example.databse.order

import com.example.databse.DatabaseFactory.dbQuery
import com.example.models.*
import org.jetbrains.exposed.sql.*

class DAOOrderFacadeImpl : DAOOrderFacade {
    private fun resultRowToProduct(row: ResultRow) = Order(
        id = row[Orders.id],
        userId = row[Orders.userId],
        date = row[Orders.date],
        orderValue = row[Orders.orderValue],
    )

    override suspend fun allOrders(): List<Order>  = dbQuery{
        Orders.selectAll().map ( ::resultRowToProduct )
    }

    override suspend fun ordersForUser(userId: Int): List<Order> = dbQuery{
        Orders.select{Orders.userId eq userId}.map ( ::resultRowToProduct )
    }

    override suspend fun orderById(orderId: Int): Order? = dbQuery {
        Orders.select{Orders.id eq orderId}.map ( ::resultRowToProduct ).firstOrNull()
    }

    override suspend fun createOrder(data: UserProducts): Order? = dbQuery {
        Orders.insert{
            it[userId] = data.userId
            it[date] = data.date
        }.resultedValues?.map(::resultRowToProduct)?.first()
    }

    override suspend fun updateOrderPrice(price: Double, orderId: Int): Int = dbQuery {
         Orders.update({Orders.id eq orderId}){
            it[orderValue] = price
        }
    }


}

val daoOrder: DAOOrderFacade = DAOOrderFacadeImpl()