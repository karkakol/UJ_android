package com.example.a8zad.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.a8zad.R
import com.example.a8zad.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.downloadProducts()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _view = inflater.inflate(R.layout.fragment_main_screen, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        val viewPager: ViewPager = _view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = _view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        return _view
    }

}