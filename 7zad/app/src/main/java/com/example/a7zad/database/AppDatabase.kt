package com.example.a7zad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a7zad.model.category.Category
import com.example.a7zad.model.category.CategoryDao
import com.example.a7zad.model.product.Product
import com.example.a7zad.model.product.ProductDao

@Database(entities = [Product::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object{
        private var instance: AppDatabase ?= null

        fun getInstance(context: Context) : AppDatabase?{
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database_7"
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }

        fun deleteInstanceDataBase(){
            instance = null
        }
    }
}
