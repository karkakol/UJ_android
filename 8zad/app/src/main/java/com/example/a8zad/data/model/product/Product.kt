package com.example.a8zad.data.model.product


data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val desc: String,
    val price: Double
)

data class BasketProduct(var quantity: Int, val product: Product){
    val allPrice get()= this.quantity * this.product.price
}

data class UserProducts(val userId: Int, val date: String, val countedProducts: List<BasketProduct>)
