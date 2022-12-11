package com.example.a5zad.ui.main.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5zad.R
import com.example.a5zad.ui.main.PageViewModel
import com.example.a5zad.ui.main.product.BasketAdapter

class BasketFragment : Fragment() {


    companion object {
        fun newInstance() = BasketFragment()
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

        val root = inflater.inflate(R.layout.fragment_basket, container, false)

        val recycleView = root.findViewById(R.id.basketProductRV) as RecyclerView

        val adapter = BasketAdapter(viewModel)

        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.adapter = adapter

        viewModel.basketLiveList.observe(viewLifecycleOwner, Observer {
           adapter.updateUserList(it)
        })

        return root
    }


}