package com.example.a5zad.ui.main.product

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.a5zad.DetailedProductActivity
import com.example.a5zad.R
import com.example.a5zad.databinding.ProductListTileBinding
import com.example.a5zad.model.Product
import com.example.a5zad.ui.main.PageViewModel
import com.google.gson.Gson


class ProductAdapter(private var dataSet: List<Product>, private val viewModel: PageViewModel) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(private val binding: ProductListTileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product1: Product) {
            binding.apply {
                product = product1
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        context = parent.getContext();
        val binding =
            ProductListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = dataSet[position]
        viewHolder.itemView.findViewById<Button>(R.id.addProduct).setOnClickListener {
            viewModel.addProductToBasket(currItem)
        }
        viewHolder.itemView.findViewById<LinearLayout>(R.id.productListTile).setOnClickListener {
            val intent = Intent(context, DetailedProductActivity::class.java)
            val productJson = Gson().toJson(currItem)
            intent.putExtra("product", productJson)
            startActivity(context!!, intent, null)
        }
        viewHolder.bind(currItem)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

