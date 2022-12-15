package com.example.a5zad.ui.main.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5zad.ui.main.product.CategoriesAdapter
import com.example.a7zad.R
import com.example.a7zad.ui.main.PageViewModel

class CategoriesFragment : Fragment() {


    companion object {
        fun newInstance() = CategoriesFragment()
    }

    lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        viewModel.getCategories()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_categories, container, false)

        val recycleView = root.findViewById(R.id.basketProductRV) as RecyclerView

        val adapter = CategoriesAdapter(viewModel.categories.value!!)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        viewModel.categories.observe(this.viewLifecycleOwner){
            adapter.updateCategories(it)
        }

        return root
    }


}