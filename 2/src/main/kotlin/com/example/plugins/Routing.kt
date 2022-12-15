package com.example.plugins

import com.example.databse.category.daoCategory
import com.example.databse.product.daoProduct
import com.example.models.Category
import com.example.models.Product
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        //Products
        get("/products") {
            call.respond(mapOf("products" to daoProduct.allProducts()))
        }

        get("/product/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond(mapOf("article" to daoProduct.product(id)))
        }

        get("/productWithCategory/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond(mapOf("article" to daoProduct.productWithCategory(id)))
        }

        post("/product") {
            val product = call.receive<Product>()
            val name = product.name
            val colorId = product.categoryId
            val desc = product.desc
            val price = product.price
            val addedProduct = daoProduct.addNewProduct(colorId,name, desc, price)
            call.respond(mapOf("body" to "added product: $product"))
        }

        put("/product") {
            val newProduct = call.receive<Product>()
            val oldProduct = daoProduct.product(newProduct.id)

            daoProduct.editProduct(newProduct.id,newProduct.categoryId, newProduct.name, newProduct.desc, newProduct.price)
            call.respond("product before modification: ${oldProduct.toString()} \n product after modification: " +
                    newProduct.toString())

        }

        delete("/product/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            val product = daoProduct.product(id)
            daoProduct.deleteProduct(id)
            call.respond("Deleted product ${product.toString()}")
        }
//        ===========================
//=====================================================================================================================
        //Categories
        get("/categories") {
            call.respond(mapOf("categories" to daoCategory.allCategories()))
        }

        get("/category/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond(mapOf("category" to daoCategory.category(id)))
        }

        post("/category") {
            val category = call.receive<Category>()
            val name = category.name
            val desc = category.desc
            val color = category.color
            val addedCategory = daoCategory.addNewCategory(name, desc, color)
            call.respond("added category: ${addedCategory.toString()}")
        }

        put("/category") {
            val newCategory= call.receive<Category>()
            val oldCategory = daoCategory.category(newCategory.id)

            daoCategory.editCategory(newCategory.id, newCategory.name, newCategory.desc, newCategory.color)
            call.respond("category before modification: ${newCategory} \n category after modification: " +
                    oldCategory.toString())

        }

        delete("/category/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            val category = daoCategory.category(id)
            daoCategory.deleteCategory(id)
            call.respond("Deleted product ${category.toString()}")
        }
    }
}
