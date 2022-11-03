package com.example.databse.product

import com.example.databse.DatabaseFactory.dbQuery
import com.example.models.Categories
import com.example.models.Product
import com.example.models.ProductWithCategory
import com.example.models.Products
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

class DAOProductFacadeImpl : DAOProductFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        categoryId = row[Products.categoryId],
        name = row[Products.name],
        desc = row[Products.desc],
        price = row[Products.price]
    )

    private fun resultRowToProductCat(row: ResultRow) = ProductWithCategory(
        id = row[Products.id],
        categoryId = row[Products.categoryId],
        name = row[Products.name],
        desc = row[Products.desc],
        price = row[Products.price],
        categoryDesc = row[Categories.desc],
        categoryName = row[Categories.name],
        color = row[Categories.color]
    )

    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun productWithCategory(id: Int): Any? = dbQuery {
        Products.rightJoin(Categories)
            .select { Products.id eq id }
            .map(::resultRowToProductCat)
            .singleOrNull()

    }

    override suspend fun addNewProduct(categoryId: Int ,name: String, desc: String, price: Double): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.categoryId] = categoryId
            it[Products.name] = name
            it[Products.desc] = desc
            it[Products.price] = price
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(id: Int,categoryId: Int , name: String, desc: String, price: Double): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[Products.categoryId] = categoryId
            it[Products.name] = name
            it[Products.desc] = desc
            it[Products.price] = price
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

val daoProduct: DAOProductFacade = DAOProductFacadeImpl()