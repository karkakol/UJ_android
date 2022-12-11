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

    var basketLiveList: MutableLiveData<List<BasketProduct>> = MutableLiveData()
    var basketProducts = listOf<BasketProduct>()

    init {
        basketLiveList.postValue(basketProducts)
    }


    fun addProductToBasket(product: Product){

       val inBasket = basketProducts.find { el -> el.product.name == product.name }

        if(inBasket == null){
            basketProducts = basketProducts.plus(BasketProduct(1,product))
            basketLiveList.postValue(basketProducts)
        }else{

            inBasket.amount +=1
            basketLiveList.postValue(basketProducts)
        }
    }

    //retur true if item was removed
    fun removeProductFromBasket(product: Product): Boolean{

        val inBasket = basketProducts.find { el -> el.product.name == product.name }

        if(inBasket != null){
          if(inBasket.amount >1){
              inBasket.amount -= 1
              basketLiveList.postValue(basketProducts)
              return false
          }else{
              basketProducts = basketProducts.minus(inBasket)
              basketLiveList.postValue(basketProducts)
              return true
          }
        }
        return false
    }

}