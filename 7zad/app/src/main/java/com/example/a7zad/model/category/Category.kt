package com.example.a7zad.model.category

import androidx.room.*
import com.example.a7zad.model.product.Product

@Entity()
data class Category (
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "desc")val desc: String,
    @ColumnInfo(name = "color")val color: String){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    override fun toString(): String {
        return name
    }
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category ORDER BY Category.id")
    fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)
}

