package com.example.databse.category



import com.example.models.Category

interface DAOCategoryFacade {
    suspend fun allCategories(): List<Category>
    suspend fun category(id: Int): Category?
    suspend fun addNewCategory(name: String, desc: String, color: String): Category?
    suspend fun editCategory(id: Int,name: String, desc: String, color: String): Boolean
    suspend fun deleteCategory(id: Int): Boolean
}