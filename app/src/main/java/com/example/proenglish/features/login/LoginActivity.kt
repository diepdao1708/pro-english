package com.example.proenglish.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import com.example.proenglish.features.main.MainActivity
import com.example.proenglish.R
import com.example.proenglish.databinding.ActivityLoginBinding
import com.example.proenglish.features.register.RegisterActivity
import com.example.proenglish.utils.LoadingUtils
import com.google.android.gms.auth.GoogleAuthUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.handleLoginResult(it.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setContentView(binding.root)

        binding.viewModel = viewModel

        viewModel.event.observe(this) { event ->
            when (event) {
                is LoginViewModel.Event.NavigateToHome -> navigateToHome()
                is LoginViewModel.Event.NavigateToRegister -> navigateToRegister()
            }
        }

        viewModel.signInIntent.observe(this) {
            googleSignInActivityResultLauncher.launch(it)
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, getString(error), Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.distinctUntilChanged().observe(this) { isLoading ->
            if (isLoading) LoadingUtils.showLoading(this) else LoadingUtils.hideLoading()
        }

        viewModel.googleToken.observe(this) {
            clearGoogleToken(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtils.hideLoading()
    }

    private fun clearGoogleToken(token: String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    GoogleAuthUtil.clearToken(this@LoginActivity, token)
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}