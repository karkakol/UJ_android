package com.example.a8zad.ui.login

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation


import com.example.a8zad.R
import com.example.a8zad.RetrofitHelper
import com.example.a8zad.data.model.User
import com.example.a8zad.ui.main.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class LoginFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var navigateToRegisterButton: Button
    private lateinit var loginButton: Button
    private lateinit var progressIndicator: ProgressBar
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var googleButton: ImageView
    private lateinit var githubButton: ImageView

    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    val githubClientId = "5f68e26b9df8dc1aca75"
    val githubClientSecret = "eefcc6b7c9785872d0f084d4b4e08bbe029ea0a2"
    val githubRedirectURI = "zad8android://callback"
    val githubURL = "https://github.com/login/oauth/authorize"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestIdToken("1045356584777-vknhrlr6rjd42sp9soi56eoonn4r49tk.apps.googleusercontent.com")
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _view = inflater.inflate(R.layout.fragment_login, container, false)

        navigateToRegisterButton = _view.findViewById(R.id.loginNavButton)
        loginButton = _view.findViewById(R.id.loginButton)
        loginEditText = _view.findViewById(R.id.loginEditText)
        passwordEditText = _view.findViewById(R.id.passwordEditText)
        progressIndicator = _view.findViewById(R.id.loading)
        googleButton = _view.findViewById(R.id.googleButton)
        githubButton = _view.findViewById(R.id.githubButton)

        navigateToRegisterButton.setOnClickListener {
            Navigation.findNavController(_view).navigate(R.id.navigateToRegister)
        }

        loginButton.setOnClickListener { loginViaServer() }

        googleButton.setOnClickListener {
            loginViaGoogle()
        }

        githubButton.setOnClickListener {
            loginViaGithub()
        }

        return _view
    }

    fun loginViaGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1000);
    }

    fun loginViaGithub() {
        val URL =
            githubURL + "?client_id=" + githubClientId + "&scope=user&redirect_uri=" + githubRedirectURI
        val githubIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL))
        startActivity(githubIntent)
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            val intent = activity.intent
            val uri = intent.data

            if (uri != null && uri.toString().startsWith(githubRedirectURI)) {
                val code = uri.getQueryParameter("code")

                if (code == null) {
                    displayError("brak kodu do uzyskanie tokena")
                    return
                }

                val userWrapper = loginViewModel.getGithubData(
                    clientId = githubClientId,
                    clientSecret = githubClientSecret,
                    code = code
                )

                if (userWrapper.error != null) {
                    displayError(userWrapper.error)
                    return
                }

                mainViewModel.user = userWrapper.user!!
                Navigation.findNavController(_view).navigate(R.id.navigateToMainScreen)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.result
                val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
                if (googleAccount != null) {
                    val login = googleAccount.email ?: "Nie ma"
                    val password = "Google nie daje hasła :("
                    val token = googleAccount.idToken!!

                    val response = loginViewModel.registerWithExternalProvider(login, password, token, "GOOGLE")

                    mainViewModel.user = response.body()!!.user
                    Navigation.findNavController(_view).navigate(R.id.navigateToMainScreen)
                }
            } catch (e: ApiException) {
                Toast.makeText(
                    context,
                    "Nie udało sie zalogować za pomoca googla",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Throwable) {
                Toast.makeText(
                    context,
                    "Nie udało sie zalogować za pomoca googla",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    fun loginViaServer() {
        val login = loginEditText.text.toString()
        val password = passwordEditText.text.toString()

        val errorId = loginViewModel.validateFields(email = login, password=password)

        if(errorId != null){
            displayError(getString(errorId))
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                progressIndicator.visibility = View.VISIBLE
            }


            val wrapped = loginViewModel.login(login, password)
            withContext(Dispatchers.Main) {
                if (wrapped.error != null) {
                    progressIndicator.visibility = View.GONE
                    Toast.makeText(context, wrapped.error, Toast.LENGTH_SHORT).show()
                } else {
                    mainViewModel.user = wrapped.user!!
                    Navigation.findNavController(_view).navigate(R.id.navigateToMainScreen)
                }
                progressIndicator.visibility = View.GONE
            }
        }
    }

    fun displayError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }
}