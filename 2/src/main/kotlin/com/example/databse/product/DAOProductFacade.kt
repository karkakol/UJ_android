package com.example.databse.product

import com.example.models.*
import java.math.BigDecimal

interface DAOProductFacade {
    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun productWithCategory(id: Int): Any?
    suspend fun addNewProduct(colorId: Int, name: String, desc: String, price: Double): Product?
    suspend fun editProduct(id: Int,colorId: Int, name: String, desc: String, price: Double): Boolean
    suspend fun deleteProduct(id: Int): Boolean
}