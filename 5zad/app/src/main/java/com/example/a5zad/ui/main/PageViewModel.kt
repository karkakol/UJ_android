package com.example.a5zad.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a5zad.model.BasketProduct
import com.example.a5zad.model.Product
import com.example.a5zad.model.Product.Companion.getProducts

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    val products = getProducts()

    var basketProducts = listOf<BasketProduct>()

    fun addProductToBasket(product: Product){

       val inBasket = basketProducts.find { el -> el.product.name == product.name }

        if(inBasket == null){
            basketProducts.plus(BasketProduct(1,product))
        }else{
            inBasket.amount +=1
        }
    }

    fun removeProductFromBasket(product: Product){

        val inBasket = basketProducts.find { el -> el.product.name == product.name }

        if(inBasket != null){
          if(inBasket.amount >1){
              inBasket.amount -= 1
          }else{
              basketProducts.minus(inBasket);
          }
        }
    }

}