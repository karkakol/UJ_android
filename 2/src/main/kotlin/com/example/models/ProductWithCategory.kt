package com.example.models

data class ProductWithCategory(
    val id: Int, val categoryId: Int, val name: String, val desc: String,
    val price:
    Double,
    val categoryName: String, val categoryDesc: String, val color: String,
)