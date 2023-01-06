package com.example.plugins

import com.example.databse.category.daoCategory
import com.example.databse.order.daoOrder
import com.example.databse.orders_product.daoOrderProducts
import com.example.databse.product.daoProduct
import com.example.databse.user.daoUser
import com.example.models.*
import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.EphemeralKey
import com.stripe.model.PaymentIntent
import com.stripe.param.CustomerCreateParams
import com.stripe.param.EphemeralKeyCreateParams
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong


fun Application.configureRouting() {

    routing {
        ///Products
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
            val addedProduct = daoProduct.addNewProduct(colorId, name, desc, price)
            call.respond(mapOf("body" to "added product: $product"))
        }
        put("/product") {
            val newProduct = call.receive<Product>()
            val oldProduct = daoProduct.product(newProduct.id)

            daoProduct.editProduct(
                newProduct.id,
                newProduct.categoryId,
                newProduct.name,
                newProduct.desc,
                newProduct.price
            )
            call.respond(
                "product before modification: ${oldProduct.toString()} \n product after modification: " +
                        newProduct.toString()
            )

        }
        delete("/product/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            val product = daoProduct.product(id)
            daoProduct.deleteProduct(id)
            call.respond("Deleted product ${product.toString()}")
        }

        ///Categories
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
            val newCategory = call.receive<Category>()
            val oldCategory = daoCategory.category(newCategory.id)

            daoCategory.editCategory(newCategory.id, newCategory.name, newCategory.desc, newCategory.color)
            call.respond(
                "category before modification: ${newCategory} \n category after modification: " +
                        oldCategory.toString()
            )

        }
        delete("/category/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            val category = daoCategory.category(id)
            daoCategory.deleteCategory(id)
            call.respond("Deleted product ${category.toString()}")
        }

        ///Users
        get("/users") {
            val users = daoUser.allUsers()

            call.respond(mapOf("users" to users))
        }
        post("/user/login") {
            val requestData = call.receive<User>()
            val user = daoUser.login(requestData.login, requestData.password)
            if (user == null) {
                call.respondText(
                    text = "Nie ma takiego usera",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.Unauthorized
                )
                return@post
            }
            call.respond(mapOf("user" to user))
        }
        post("/user/fromExternalProvider") {
            val userData = call.receive<User>()
            val user = daoUser.registerUserFromOtherSource( userData)
            if (user == null) {
                call.respondText(
                    text = "Coś poszło nie tak",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest
                )
                return@post
            }
            call.respond(mapOf("user" to user))

        }
        post("/user/register") {
            val userData = call.receive<RegisteredUser>()

            if (userData.login.length < 6) {
                call.respondText(
                    text = "Za krótki login",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.NotAcceptable
                )

                return@post
            }

            if (userData.password != userData.repeatedPassword) {
                call.respondText(
                    text = "Podane hasła są różne",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.NotAcceptable
                )
                return@post
            }

            if (userData.password.length < 6) {
                call.respondText(
                    text = "Hasło musi mieć conajmniej 6 zaków",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.NotAcceptable
                )
                return@post
            }

            val userWithSameLogin = daoUser.userByLogin(userData.login)
            if (userWithSameLogin != null) {
                call.respondText(
                    text = "Istnieje użytkownik o takim loginie",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.NotAcceptable
                )
                return@post
            }
            val registeredUser = daoUser.register(userData.login, userData.password)

            if (registeredUser == null) {
                call.respondText(
                    text = "Coś sie popsuło i nie było mnie słychać",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest
                )
                return@post
            } else {
                call.respond(mapOf("user" to registeredUser))
            }


        }

        //

        get("/orders/{userId}"){
            val id = call.parameters.getOrFail<Int>("userId")
            call.respond(mapOf("orders" to daoOrder.ordersForUser(id)))
        }

        post("/order/create"){
            val requestData = call.receive<UserProducts>()

            val createdOrder = daoOrder.createOrder(requestData)

            if(createdOrder == null){
                call.respondText(
                    text = "Coś sie popsóło",
                    contentType = ContentType.Application.Json,
                    status = HttpStatusCode.BadRequest
                )
                return@post
            }
            val orderId = createdOrder.id

            val insertedProducts = daoOrderProducts.addProductsToOrder(orderId = orderId, requestData)
            var sum = 0.0
            insertedProducts.forEach{sum += it.productsValue}

            daoOrder.updateOrderPrice(sum, orderId)

            call.respond(mapOf("order" to daoOrder.orderById(orderId)))
        }

        post("/payment-sheet"){
            val requestData = call.receive<UserProducts>()
            var sum = 0.0
            requestData.countedProducts.forEach {
                sum += it.product.price * it.quantity
            }
            val roundedSum = (sum*100).roundToLong()
            Stripe.apiKey ="sk_test_51MMErnAfaFir4cbYAtTkfujXOw7eNmkSM4dFit7adhw7bBx0FgqqAPM6fKOFUYbmnUbYfdBMCZW1ou2WVVYBllYx00MiyrvU3J"
            val customerParams = CustomerCreateParams.builder().build()
            val customer = Customer.create(customerParams)

            val ephemeralKeyParams = EphemeralKeyCreateParams.builder()
                .setStripeVersion("2022-11-15")
                .setCustomer(customer.id)
                .build()

            val ephemeralKey = EphemeralKey.create(ephemeralKeyParams)

            val paymentMethodTypes: MutableList<String> = ArrayList()
            paymentMethodTypes.add("bancontact")
            paymentMethodTypes.add("card")
            paymentMethodTypes.add("ideal")
            paymentMethodTypes.add("sepa_debit")
            paymentMethodTypes.add("sofort")

            val paymentIntentParams = PaymentIntentCreateParams.builder()
                .setAmount(roundedSum)
                .setCurrency("eur")
                .setCustomer(customer.id)
                .addAllPaymentMethodType(paymentMethodTypes)
                .build()

            val paymentIntent = PaymentIntent.create(paymentIntentParams)

            val responseData: HashMap<String?, String?> = HashMap()
            responseData["paymentIntent"] = paymentIntent.clientSecret
            responseData["ephemeralKey"] = ephemeralKey.secret
            responseData["customer"] = customer.id
            responseData["publishableKey"] =
                "pk_test_51MMErnAfaFir4cbYJK8rOHFKwnjhhRd8GMn2Dl1frKBevM0JBA58kIat0ahIr3W3faMX9boMHmuXNzwF9RklkYEk00Y20m7pk3"

            call.respond(responseData)
        }
    }
}


