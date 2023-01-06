package com.example.databse.orders_product

import com.example.models.*
import java.math.BigDecimal

interface DAOOrderProductsFacade {
    suspend fun allOrdersProduct(): List<OrderProduct>
    suspend fun ordersProductForOrder(orderId: Int): List<OrderProduct>
    suspend fun addProductsToOrder(orderId: Int, data: UserProducts): List<OrderProduct>
}