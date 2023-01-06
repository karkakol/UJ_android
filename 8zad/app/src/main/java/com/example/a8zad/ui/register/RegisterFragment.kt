package com.example.a8zad.ui.register

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation


import com.example.a8zad.R
import com.example.a8zad.data.model.User
import com.example.a8zad.ui.main.MainViewModel


class RegisterFragment : Fragment() {
    private lateinit var _view: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navigateToLogin : Button
    private lateinit var registerButton : Button
    private lateinit var progressIndicator : ProgressBar
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatedPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerViewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _view = inflater.inflate(R.layout.fragment_register, container, false)

        navigateToLogin = _view.findViewById(R.id.registerNavButton)
        registerButton = _view.findViewById(R.id.registerButton)
        loginEditText = _view.findViewById(R.id.username)
        passwordEditText = _view.findViewById(R.id.password)
        repeatedPasswordEditText = _view.findViewById(R.id.repeatedPasswordEditText)
        progressIndicator = _view.findViewById(R.id.loading)

        navigateToLogin.setOnClickListener{
            Navigation.findNavController(_view).navigate(R.id.navigateToLogin)
        }

        registerButton.setOnClickListener { register() }

        return _view
    }

    fun register(){
        progressIndicator.visibility = View.VISIBLE
        val login = loginEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repeatedPassword = repeatedPasswordEditText.text.toString()

        val response = registerViewModel.register(login, password, repeatedPassword)


        if(!response.isSuccessful){
            progressIndicator.visibility = View.GONE
            Toast.makeText(context, "Coś sie nie udało", Toast.LENGTH_SHORT).show()
        }else{
            mainViewModel.user = response.body()!!.user
            Navigation.findNavController(_view).navigate(R.id.navigateToMainScreen)
        }

        progressIndicator.visibility = View.GONE
    }

}