package com.example.proenglish.features.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.proenglish.R
import com.example.proenglish.databinding.ActivityRegisterBinding
import com.example.proenglish.features.login.LoginActivity
import com.example.proenglish.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setContentView(binding.root)

        binding.viewModel = viewModel

        viewModel.event.observe(this) { event ->
            when (event) {
                is RegisterViewModel.Event.NavigateToLogin -> navigateToLogin()
            }
        }

        viewModel.message.observe(this) { message ->
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        hideKeyboard()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}