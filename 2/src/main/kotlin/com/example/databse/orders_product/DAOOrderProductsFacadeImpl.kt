package com.example.databse.orders_product

import com.example.databse.DatabaseFactory.dbQuery
import com.example.models.*
import org.jetbrains.exposed.sql.*

class DAOOrderProductsFacadeImpl : DAOOrderProductsFacade {
    private fun resultRowToProduct(row: ResultRow) = OrderProduct(
        id = row[OrderProducts.id],
        orderId = row[OrderProducts.orderId],
        productId = row[OrderProducts.productId],
        quantity = row[OrderProducts.quantity],
        productsValue = row[OrderProducts.productsValue]
    )

    override suspend fun allOrdersProduct(): List<OrderProduct> = dbQuery{
        OrderProducts.selectAll().map(::resultRowToProduct)
    }

    override suspend fun ordersProductForOrder(orderId: Int): List<OrderProduct>  = dbQuery{
        OrderProducts.select{OrderProducts.orderId eq orderId}.map(::resultRowToProduct)
    }

    override suspend fun addProductsToOrder(orderId: Int, data: UserProducts): List<OrderProduct> = dbQuery{
            OrderProducts.batchInsert(data = data.countedProducts){
                this[OrderProducts.orderId] =orderId
                this[OrderProducts.productId] =it.product.id
                this[OrderProducts.quantity] =it.quantity
                this[OrderProducts.productsValue] =it.product.price * it.quantity

            }.map(::resultRowToProduct)
    }
}

val daoOrderProducts: DAOOrderProductsFacade = DAOOrderProductsFacadeImpl()