package com.example.a8zad.ui.main

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a8zad.Api
import com.example.a8zad.RetrofitHelper
import com.example.a8zad.data.model.User
import com.example.a8zad.data.model.order.Order
import com.example.a8zad.data.model.product.BasketProduct
import com.example.a8zad.data.model.product.Product
import com.example.a8zad.data.model.product.UserProducts
import retrofit2.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.NonDisposableHandle.parent
import java.time.LocalDateTime

class MainViewModel : ViewModel() {

    var api = RetrofitHelper.getInstance(null).create(Api::class.java)
    lateinit var user: User
    var productsLiveList: MutableLiveData<List<Product>> = MutableLiveData()
    var basketLiveList: MutableLiveData<List<BasketProduct>> = MutableLiveData()
    var ordersLiveList: MutableLiveData<List<Order>> = MutableLiveData()
    var basketProducts = listOf<BasketProduct>()

    @OptIn(DelicateCoroutinesApi::class)
    fun downloadProducts() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getProducts()

            if (response.isSuccessful) {
                val _products = response.body()!!.products
                productsLiveList.postValue(_products)
            }
        }

    }


    fun getAllOrders() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getOrders(user.id)

            if (response.isSuccessful) {
                val _orders = response.body()!!.orders
                ordersLiveList.postValue(_orders)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createOrder(): String? {
        val products = basketLiveList.value
        if (products == null || products.isEmpty()) {
            return null
        }
        val request = UserProducts(
            userId = user.id,
            date = LocalDateTime.now().toString(),
            countedProducts = products
        )
        var str = ""
        runBlocking {
            GlobalScope.async {
                val response = api.createOrder(request)
                if (response.isSuccessful) {
                    str = "Udało sie złożyć zamówienie, sprawdź w zakładce zamówienia"

                } else {
                    str = "Coś poszło nie tak"
                }
            }.await()
        }
        if(str == "Udało sie złożyć zamówienie, sprawdź w zakładce zamówienia"){
            basketLiveList.value = listOf()
            basketProducts = listOf()
        }

        return str
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupStripePayment(): Response<Map<String, String>>? {
        val products = basketLiveList.value
        if (products == null || products.isEmpty()) {
            return null
        }
        val request = UserProducts(
            userId = user.id,
            date = LocalDateTime.now().toString(),
            countedProducts = products
        )
        lateinit var  response: Response<Map<String, String>>
        runBlocking {
           return@runBlocking GlobalScope.async {
               response =  api.setupPayment(request)
            }.await()
        }

       return  response
    }


    fun addProductToBasket(product: Product) {
        val inBasket = basketProducts.find { el -> el.product.name == product.name }
        if (inBasket == null) {
            basketProducts = basketProducts.plus(BasketProduct(1, product))
            basketLiveList.postValue(basketProducts)
        } else {
            inBasket.quantity += 1
            basketLiveList.postValue(basketProducts)
        }
    }

    fun removeProductFromBasket(product: Product) {
        val inBasket = basketProducts.find { el -> el.product.name == product.name }
        if (inBasket != null) {
            if (inBasket.quantity > 1) {
                inBasket.quantity -= 1
                basketLiveList.postValue(basketProducts)
            } else {
                basketProducts = basketProducts.minus(inBasket)
                basketLiveList.postValue(basketProducts)
            }
        }
    }
}