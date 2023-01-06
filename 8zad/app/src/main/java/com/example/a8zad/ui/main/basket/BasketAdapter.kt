package com.example.a8zad.ui.main.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.a8zad.R
import com.example.a8zad.data.model.product.BasketProduct
import com.example.a8zad.databinding.BasketProductListTileBinding
import com.example.a8zad.ui.main.MainViewModel


class BasketAdapter(val viewModel: MainViewModel) :
    RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private var data :  List<BasketProduct> = listOf()

    class ViewHolder(private val binding: BasketProductListTileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product1: BasketProduct) {
            binding.apply {
                product = product1
            }

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = BasketProductListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = data[position]
        viewHolder.itemView.findViewById<Button>(R.id.butonPlus).setOnClickListener {
             viewModel.addProductToBasket(currItem.product)

        }

        viewHolder.itemView.findViewById<Button>(R.id.buttonMinus).setOnClickListener {
             viewModel.removeProductFromBasket(currItem.product)
        }
        viewHolder.bind(currItem)
    }

    override fun getItemCount()=data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateUserList(newData: List<BasketProduct>) {
        data = newData
        notifyDataSetChanged()
    }

}