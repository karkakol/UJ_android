package com.example.a4.models

import android.content.Context
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity()
data class Product(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "status") val status: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0;
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product ORDER BY Product.uid")
    fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Product)

    @Delete
    fun delete(user: Product)
}

@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object{
        private var instance: AppDatabase ?= null

        fun getInstance(context: Context) : AppDatabase?{
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name11"
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }

        fun deleteInstanceDataBase(){
            instance = null
        }
    }
}
