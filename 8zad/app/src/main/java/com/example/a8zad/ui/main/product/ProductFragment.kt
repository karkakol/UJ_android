package com.example.a8zad.ui.main.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a8zad.R
import com.example.a8zad.ui.main.MainViewModel


class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product, container, false)

        val recycleView = rootView.findViewById(R.id.productsRV) as RecyclerView

        val adapter = ProductAdapter(listOf(), viewModel)

        viewModel.productsLiveList.observe(viewLifecycleOwner) {
            adapter.updateUserList(it)
        }

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        return rootView
    }


}