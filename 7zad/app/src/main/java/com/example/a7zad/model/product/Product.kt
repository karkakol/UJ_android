package com.example.a7zad.model.product

import android.content.Context
import androidx.room.*

@Entity()
data class Product (
    @ColumnInfo(name = "categoryId") val categoryId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "price") val price: Double){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product ORDER BY Product.id")
    fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)
}

