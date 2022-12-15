package com.example.a5zad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.a5zad.databinding.ActivityDetailedProductBinding
import com.example.a5zad.model.Product
import com.google.gson.Gson

class DetailedProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_product)
        val bundle: Bundle = intent.extras!!
        val jsonProduct : String = bundle.get("product") as String
        val product = Gson().fromJson(jsonProduct, Product::class.java)
        val binding: ActivityDetailedProductBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_detailed_product)

        binding.product = product


        findViewById<Button>(R.id.detailedProductBack).setOnClickListener{
            this.finish()
        }
    }
}