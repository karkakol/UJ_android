package com.example.a7zad

import com.example.a7zad.model.api.categories_response.CategoriesResponse
import com.example.a7zad.model.api.products_response.ProductResponse
import com.example.a7zad.model.product.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/products")
    suspend fun getProducts() : Response<ProductResponse>

    @POST("/product")
    suspend fun createProduct(@Body product: Product) : Response<Any>

    @GET("/categories")
    suspend fun getCategories() : Response<CategoriesResponse>
}