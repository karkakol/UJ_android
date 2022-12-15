package com.example.a7zad.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.a7zad.Api
import com.example.a7zad.RetrofitHelper
import com.example.a7zad.database.AppDatabase
import com.example.a7zad.model.category.Category
import com.example.a7zad.model.product.Product
import kotlinx.coroutines.*

class PageViewModel(application: Application) : AndroidViewModel(application) {
    var database = AppDatabase.getInstance(application)
    var productDao = database!!.productDao()
    var categoryDao = database!!.categoryDao()
    val api = RetrofitHelper.getInstance().create(Api::class.java)

    var products: MutableLiveData<List<Product>> =MutableLiveData( listOf())
    var categories: MutableLiveData<List<Category>> =MutableLiveData( listOf())

    fun getProducts() {
        GlobalScope.launch {
            val response = api.getProducts()
            if (response.isSuccessful) {
                val newProducts = response.body()!!.products
                products.postValue(newProducts)
                insertProducts(newProducts)
            }else{
                products.postValue(getDatabaseProducts())
            }
        }
    }

    fun getDatabaseProducts(): List<Product> {
        val data = runBlocking{
            CoroutineScope(Dispatchers.IO).async {
                productDao.getAll()
            }.await()
        }

        return data
    }

    fun insertProducts(products: List<Product>) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insertAll(products)
        }
    }

    fun getCategories() {
        GlobalScope.launch {
            val response = api.getCategories()
            if (response.isSuccessful) {
                val newCategories = response.body()!!.categories
                categories.postValue(newCategories)
                insertCategories(newCategories)
            }else{
                categories.postValue(getDatabaseCategories())
            }
        }
    }

    fun getDatabaseCategories(): List<Category> {
        val data = runBlocking{
            CoroutineScope(Dispatchers.IO).async {
                categoryDao.getAll()
            }.await()
        }

        return data
    }

    fun insertCategories(categories: List<Category>) {
        CoroutineScope(Dispatchers.IO).launch {
            categoryDao.insertAll(categories)
        }
    }


}