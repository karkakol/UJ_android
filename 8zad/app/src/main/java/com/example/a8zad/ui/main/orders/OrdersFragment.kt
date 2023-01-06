package com.example.a8zad.ui.main.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a8zad.R
import com.example.a8zad.ui.main.MainViewModel
import com.example.a8zad.ui.main.product.BasketAdapter


class OrdersFragment : Fragment() {


    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.getAllOrders()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        val recycleView = root.findViewById(R.id.ordersRV) as RecyclerView

        val adapter = OrdersAdapter(viewModel)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        viewModel.ordersLiveList.observe(viewLifecycleOwner, Observer {
            adapter.updateUserList(it)
        })

        return root
    }


    companion object {
        fun newInstance() = OrdersFragment()
    }
}