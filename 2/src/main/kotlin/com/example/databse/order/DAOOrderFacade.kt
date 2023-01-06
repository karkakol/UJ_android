package com.example.databse.order

import com.example.models.*
import java.math.BigDecimal

interface DAOOrderFacade {
    suspend fun allOrders(): List<Order>
    suspend fun ordersForUser(userId: Int): List<Order>
    suspend fun orderById(orderId: Int): Order?
    suspend fun createOrder(data: UserProducts): Order?
    suspend fun updateOrderPrice(price: Double, orderId: Int): Int?
}