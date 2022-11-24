package com.example.a4.ui.main

import android.app.Application
import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a4.models.AppDatabase
import com.example.a4.models.Product
import com.example.a4.models.ProductDao
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var database = AppDatabase.getInstance(application)
    val productDao = database!!.productDao()
    var displayedProducts = getProducts()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addProductsToDatabase() {


        if (displayedProducts.isEmpty()) {
            val p1 =
                Product("Kamień 1", "Kamień 1 jest gitówa", LocalDate.now().toString(), "Status Kamień 1")
            val p2 =
                Product("Kamień 2", "Kamień 2 jest gitówa", LocalDate.now().toString(), "Status Kamień 2")
            val p3 =
                Product("Kamień 3", "Kamień 3 jest gitówa", LocalDate.now().toString(), "Status Kamień 3")
            val p4 =
                Product("Kamień 4", "Kamień 4 jest gitówa", LocalDate.now().toString(), "Status Kamień 4")
            val p5 =
                Product("Kamień 5", "Kamień 5 jest gitówa", LocalDate.now().toString(), "Status Kamień 5")
            insertProduct(p1)
            insertProduct(p2)
            insertProduct(p3)
            insertProduct(p4)
            insertProduct(p5)


        }
    }

    fun insertProduct(p: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insert(p)
        }
    }

    fun getProducts(): ArrayList<Product> {
        val data = runBlocking {
            getProductss().await()
        }

        return ArrayList(data)

    }

    private fun getProductss(): Deferred<List<Product>> {
        return CoroutineScope(Dispatchers.IO).async {
            productDao.getAll()
        }
    }


    fun deleteProduct(product: Product) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).async {
                productDao.delete(product)
            }.await()
        }
        displayedProducts = getProducts()
    }
}
