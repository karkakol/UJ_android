package com.example.databse.category

import com.example.databse.DatabaseFactory.dbQuery
import com.example.models.Categories
import com.example.models.Category
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*


class DAOCategoryFacadeImpl : DAOCategoryFacade {
    private fun resultRow(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name],
        desc = row[Categories.desc],
        color = row[Categories.color]
    )
    override suspend fun allCategories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRow)
    }

    override suspend fun category(id: Int): Category? = dbQuery {
        Categories
            .select { Categories.id eq id }
            .map(::resultRow)
            .singleOrNull()
    }

    override suspend fun addNewCategory(name: String, desc: String, color: String): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
            it[Categories.desc] = desc
            it[Categories.color] = color
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRow)
    }

    override suspend fun editCategory(id: Int, name: String, desc: String, color: String): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }) {
            it[Categories.name] = name
            it[Categories.desc] = desc
            it[Categories.color] = color
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean= dbQuery {
        Categories.deleteWhere { Categories.id eq id } > 0
    }
}

val daoCategory: DAOCategoryFacade = DAOCategoryFacadeImpl()