package com.example.a8zad.ui.main.basket

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a8zad.R
import com.example.a8zad.ui.main.MainViewModel
import com.example.a8zad.ui.main.product.BasketAdapter
import com.example.a8zad.utils.HashGenerationUtils
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.checkoutpro.PayUCheckoutPro
import com.payu.checkoutpro.utils.PayUCheckoutProConstants
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME
import com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING
import com.payu.ui.model.listeners.PayUCheckoutProListener
import com.payu.ui.model.listeners.PayUHashGenerationListener
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class BasketFragment : Fragment() {

    companion object {
        fun newInstance() = BasketFragment()
    }

    lateinit var viewModel: MainViewModel
    lateinit var serverButton: Button
    lateinit var stripeButton: Button
    lateinit var payUButton: Button
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPayment() {

        val response = viewModel.setupStripePayment()
        if (response == null) return
        if (response.isSuccessful) {
            val responseBody = response.body()!!
            paymentIntentClientSecret = responseBody["paymentIntent"]!!
            customerConfig = PaymentSheet.CustomerConfiguration(
                responseBody["customer"]!!,
                responseBody["ephemeralKey"]!!
            )
            val publishableKey = responseBody["publishableKey"]!!
            PaymentConfiguration.init(requireContext(), publishableKey)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun presentPaymentSheet() {
        val products = viewModel.basketLiveList.value
        if (products == null || products.isEmpty()) {
            return
        }
        setupPayment()
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                showText("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                showText("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                viewModel.createOrder()
                viewModel.getAllOrders()
                showText("Completed")
            }
        }


    }

    fun payByPayU(){

        val additionalParamsMap: HashMap<String, Any?> = HashMap()
        additionalParamsMap[PayUCheckoutProConstants.CP_UDF1] = "udf1"
        additionalParamsMap[PayUCheckoutProConstants.CP_UDF2] = "udf2"
        additionalParamsMap[PayUCheckoutProConstants.CP_UDF3] = "udf3"
        additionalParamsMap[PayUCheckoutProConstants.CP_UDF4] = "udf4"
        additionalParamsMap[PayUCheckoutProConstants.CP_UDF5] = "udf5"
        additionalParamsMap[PayUCheckoutProConstants.SODEXO_SOURCE_ID] = "srcid123"

        val payUPaymentParams = PayUPaymentParams.Builder()
            .setAmount("1.0")
            .setIsProduction(false)
            .setKey("gtKFFx")
            .setProductInfo("Macbook Pro")
            .setTransactionId(System.currentTimeMillis().toString())
            .setFirstName("John")
            .setEmail("john@yopmail.com")
            .setPhone("7903376251")
            .setSurl("https://webhook.site/b86a8f18-d902-4f12-aa25-09a7fe598744")
                .setFurl("https://payuresponse.firebaseapp.com/failure")
                .build()

        PayUCheckoutPro.open(
            requireActivity(), payUPaymentParams,
            object : PayUCheckoutProListener {


                override fun onPaymentSuccess(response: Any) {
                    response as HashMap<*, *>
                    val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                    val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                    Toast.makeText(requireContext(), "Płatność powiodła się", Toast.LENGTH_LONG).show()

                }


                override fun onPaymentFailure(response: Any) {
                    response as HashMap<*, *>
                    val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                    val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                    Toast.makeText(requireContext(), "Płatność nie powiodła się", Toast.LENGTH_LONG).show()

                }


                override fun onPaymentCancel(isTxnInitiated:Boolean) {
                    Toast.makeText(requireContext(), "Płatność anulowana", Toast.LENGTH_LONG).show()

                }


                override fun onError(errorResponse: ErrorResponse) {
                    val errorMessage: String
                    if (errorResponse != null && errorResponse.errorMessage != null && errorResponse.errorMessage!!.isNotEmpty())
                        errorMessage = errorResponse.errorMessage!!
                    else
                        errorMessage ="errorrr"

                    Log.d("PAYU",errorMessage)

                }

                override fun setWebViewProperties(webView: WebView?, bank: Any?) {
                    //For setting webview properties, if any. Check Customized Integration section for more details on this
                }

                override fun generateHash(
                    valueMap: HashMap<String, String?>,
                    hashGenerationListener: PayUHashGenerationListener
                ) {

                    if ( valueMap.containsKey(CP_HASH_STRING)
                        && valueMap.containsKey(CP_HASH_STRING) != null
                        && valueMap.containsKey(CP_HASH_NAME)
                        && valueMap.containsKey(CP_HASH_NAME) != null) {


                        val hashData = valueMap[CP_HASH_STRING]
                        val hashName = valueMap[CP_HASH_NAME]

                        //Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
                        val hash: String? = HashGenerationUtils.generateHashFromSDK(
                            hashData.toString(), "4R38IvwiV57FwVpsgOvTXBdLE4tHUXFW"
                        )
                        if (!TextUtils.isEmpty(hash)) {
                            val dataMap: HashMap<String, String?> = HashMap()
                            dataMap[hashName!!] = hash!!
                            hashGenerationListener.onHashGenerated(dataMap)
                        }
                    }


                }
            })




    }

    fun showText(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_basket, container, false)

        val recycleView = root.findViewById(R.id.basketProductRV) as RecyclerView
        serverButton = root.findViewById(R.id.serverButton)
        stripeButton = root.findViewById(R.id.stripeButton)
        payUButton = root.findViewById(R.id.payUButton)
        val adapter = BasketAdapter(viewModel)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        viewModel.basketLiveList.observe(viewLifecycleOwner, Observer {
            adapter.updateUserList(it)
        })

        stripeButton.setOnClickListener {
            presentPaymentSheet()
        }

        payUButton.setOnClickListener {
            payByPayU()
        }

        serverButton.setOnClickListener {
            val _products = viewModel.basketLiveList.value
            if (_products != null && _products.isNotEmpty()) {
                val response = viewModel.createOrder()
                if (response != null)
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            }
            viewModel.getAllOrders()
        }

        return root
    }


}