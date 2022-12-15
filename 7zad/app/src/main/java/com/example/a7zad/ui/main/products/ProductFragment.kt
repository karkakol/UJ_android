package com.example.a7zad.ui.main.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7zad.R
import com.example.a7zad.ui.main.PageViewModel


class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        viewModel.getProducts()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product, container, false)

        val recycleView = rootView.findViewById(R.id.productsRV) as RecyclerView

        val adapter = ProductAdapter(viewModel.products.value!!)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        viewModel.products.observe(this.viewLifecycleOwner) {
            adapter.updateProducts(it)
        }

        return rootView
    }


}