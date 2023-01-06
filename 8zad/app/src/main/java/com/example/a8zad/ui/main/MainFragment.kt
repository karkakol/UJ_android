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
    private lateinit var binding: ActivityMainBinding
//    private lateinit var logoutButton: Button
//    private lateinit var loginType: TextView
//    private lateinit var login: TextView
//    private lateinit var password: TextView
//    private lateinit var googleSignInOptions : GoogleSignInOptions
//    private lateinit var  googleSignInClient  : GoogleSignInClient
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(),googleSignInOptions)
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
//
//        logoutButton = _view.findViewById(R.id.logoutButton)
//        loginType = _view.findViewById(R.id.loginType)
//        login = _view.findViewById(R.id.login)
//        password = _view.findViewById(R.id.password)
//
//        loginType.text = viewModel.user.registerSource
//        login.text = viewModel.user.login
//        password.text = viewModel.user.password
//
//        logoutButton.setOnClickListener {
//            logout()
//            afterLogout()
//        }
//
        return _view
    }
//
//    fun logout(){
//      if(viewModel.user.registerSource == "GOOGLE")  {
//          try{
//              googleSignInClient.signOut()
//          }catch(_: Throwable){
//
//          }
//
//      }
//    }
//
//    fun afterLogout(){
//        Navigation.findNavController(_view).navigate(R.id.navigateToLoginFromMain)
//    }



}