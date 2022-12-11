package com.example.a5zad.ui.main.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5zad.R
import com.example.a5zad.ui.main.PageViewModel


class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product, container, false)

        val recycleView = rootView.findViewById(R.id.productsRV) as RecyclerView

        val adapter = ProductAdapter(viewModel.products, viewModel)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        return rootView
    }


}