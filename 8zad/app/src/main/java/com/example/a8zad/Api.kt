package com.example.a8zad


import com.example.a8zad.data.model.AccessToken
import com.example.a8zad.data.model.api.GithubUser
import com.example.a8zad.data.model.api.login.LoginRequest
import com.example.a8zad.data.model.api.login.LoginResponse
import com.example.a8zad.data.model.api.order.AllOrdersResponse
import com.example.a8zad.data.model.api.product_response.ProductResponse
import com.example.a8zad.data.model.api.register.RegisterRequest
import com.example.a8zad.data.model.api.register.RegisterResponse
import com.example.a8zad.data.model.api.registerFromExternalProvider.RegisterFromExternalProviderRequest
import com.example.a8zad.data.model.api.registerFromExternalProvider.RegisterFromExternalProviderResponse
import com.example.a8zad.data.model.api.products_response.ProductsResponse
import com.example.a8zad.data.model.order.Order
import com.example.a8zad.data.model.product.UserProducts
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @HTTP(method = "POST", path ="/user/login", hasBody = true)
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @HTTP(method = "POST", path ="/user/register", hasBody = true)
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>

    @HTTP(method = "POST", path ="/user/fromExternalProvider", hasBody = true)
    suspend fun registerFromExternalProvider(@Body registerRequest: RegisterFromExternalProviderRequest) : Response<RegisterFromExternalProviderResponse>

    @GET("/products")
    suspend fun getProducts() : Response<ProductsResponse>

    @GET("/discount")
    suspend fun getDiscount(): Response<ProductResponse>

    @GET("/discount/{text}")
    suspend fun getDiscount(@Path("text") text: String): Response<String>

    @GET("/orders/{userId}")
    suspend fun getOrders(@Path("userId") userID: Int) : Response<AllOrdersResponse>

    @POST("/order/create")
    suspend fun createOrder(@Body userProduct: UserProducts): Response<Order>

    @POST("/stripe-payment-sheet")
    suspend fun setupPayment(@Body userProduct: UserProducts): Response<Map<String,String>>
}


interface  GithubApi{
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun githubAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") secret: String,
        @Field("code") code: String,
        ) : Response<AccessToken>


}

interface  AuthGithubApi{
    @Headers("Accept: application/json")
    @GET("user")
    suspend fun getGithubUser(
        @Header("Authorization") authHeader: String
    ) : Response<GithubUser>
}