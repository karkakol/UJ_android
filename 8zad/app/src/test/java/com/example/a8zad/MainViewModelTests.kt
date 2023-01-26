package com.example.a8zad
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.a8zad.data.model.User
import com.example.a8zad.data.model.api.order.AllOrdersResponse
import com.example.a8zad.data.model.api.products_response.ProductsResponse
import com.example.a8zad.data.model.order.Order
import com.example.a8zad.data.model.product.Product
import com.example.a8zad.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class MainViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        val api = mockk<Api>()
        val viewModel = MainViewModel()
        val products = listOf(
            Product(1, 1, "P1", "D1", 10.0),
            Product(2, 2, "P2", "D2", 20.0),
            Product(3, 3, "P3", "D3", 30.0)
        )
        val productResponse = ProductsResponse(products)

        val orders = listOf(
            Order(1,1,"1", 100.111),
            Order(2,2,"2", 200.222),
            Order(3,3,"3", 300.333),
        )

        val allOrdersResponse = AllOrdersResponse(orders)
    }

    @Before
    fun setup() {
        coEvery { api.getProducts() } coAnswers  { productResponse() }
        coEvery { api.getOrders(any()) } coAnswers  { ordersResponse() }
        coEvery { api.createOrder(any()) } coAnswers  { createOrderSuccessResponse() }
        viewModel.api = api
        viewModel.user = User(1,"test@test.pl","123456","13213123")
        viewModel.basketLiveList = MutableLiveData()
        viewModel.productsLiveList = MutableLiveData()
        viewModel.basketProducts = listOf()
    }

    suspend fun productResponse(): Response<ProductsResponse>{
        return Response.success(productResponse)
    }

    suspend fun ordersResponse(): Response<AllOrdersResponse>{
        return Response.success(allOrdersResponse)
    }

    suspend fun createOrderSuccessResponse(): Response<Order>{
        return Response.success(orders[0])
    }

    suspend fun createOrderFailureResponse(): Response<Order>{
        return Response.error(400, ResponseBody.create(null, ""))
    }

    @Test
    fun downloadProducts() = runTest{
        viewModel.downloadProducts()

        assertEquals(3, viewModel.productsLiveList.value?.size)
    }

    @Test
    fun addNewProduct() = runTest{
        viewModel.addProductToBasket(products[0])
        assertEquals(true,viewModel.basketProducts.isNotEmpty() &&  viewModel.basketProducts.get(0).product == products[0])
    }

    @Test
    fun addRepeatedProduct() = runTest{
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[0])
        assertEquals(true,viewModel.basketProducts.size ==1 &&  viewModel.basketProducts.get(0).product == products[0] && viewModel.basketProducts.get(0).quantity == 2 )
    }

    @Test
    fun addMultipleProducts() = runTest{
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[1])
        assertEquals(true,viewModel.basketProducts.size == 2 &&
            viewModel.basketProducts.get(0).quantity == 1 && viewModel.basketProducts.get(1).quantity == 1
        )
    }

    @Test
    fun removeOneSingleProduct() = runTest {
        viewModel.addProductToBasket(products[0])
        viewModel.removeProductFromBasket(products[0])
        assertEquals(0, viewModel.basketProducts.size)
    }

    @Test
    fun removeOneDuplicatedProduct() = runTest {
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[0])
        viewModel.removeProductFromBasket(products[0])
        assertEquals(1, viewModel.basketProducts.get(0).quantity)
    }

    @Test
    fun removeOneOfTwoProduct() = runTest {
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[1])
        viewModel.removeProductFromBasket(products[0])
        assertEquals(1, viewModel.basketProducts.size)
    }

    @Test
    fun clearBasketAfterOrder() = runTest {
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[1])
        viewModel.createOrder()
        assertEquals(0, viewModel.basketProducts.size)
    }

    @Test
    fun checkIfResponseIsSuccessful() = runTest {
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[1])
        val res = viewModel.createOrder()
        assertEquals("Udało sie złożyć zamówienie, sprawdź w zakładce zamówienia", res)
    }

    @Test
    fun checkIfResponseIsFailure() = runTest {
        coEvery { api.createOrder(any()) } coAnswers  { createOrderFailureResponse() }
        viewModel.addProductToBasket(products[0])
        viewModel.addProductToBasket(products[1])
        val res = viewModel.createOrder()
        assertEquals("Coś poszło nie tak", res)
    }


}