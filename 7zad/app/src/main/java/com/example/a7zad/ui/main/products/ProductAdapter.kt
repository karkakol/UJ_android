package com.example.a7zad.ui.main.products

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7zad.databinding.ProductListTileBinding
import com.example.a7zad.model.product.Product


class ProductAdapter(private var dataSet: List<Product>) :
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
        context = parent.context;
        val binding =
            ProductListTileBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = dataSet[position]

        viewHolder.bind(currItem)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {

        return dataSet.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newData: List<Product>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}

