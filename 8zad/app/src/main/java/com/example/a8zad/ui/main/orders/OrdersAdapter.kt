package com.example.a8zad.ui.main.orders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.a8zad.R
import com.example.a8zad.data.model.order.Order

import com.example.a8zad.databinding.OrderListTileBinding

import com.example.a8zad.ui.main.MainViewModel


class OrdersAdapter( private val viewModel: MainViewModel) :

    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    private var dataSet: List<Order> = listOf()

    class ViewHolder(private val binding: OrderListTileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order1: Order) {
            binding.apply {
                order = order1
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding =
            OrderListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = dataSet[position]

        viewHolder.bind(currItem)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateUserList(newData: List<Order>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}
