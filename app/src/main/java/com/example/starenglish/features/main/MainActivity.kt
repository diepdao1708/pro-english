package com.example.starenglish.features.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.starenglish.R
import com.example.starenglish.databinding.ActivityMainBinding
import com.example.starenglish.features.login.LoginActivity
import com.example.starenglish.utils.AppEvent
import com.example.starenglish.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.viewModel = viewModel

        viewModel.event.observe(this) { event ->
            when (event) {
                is MainViewModel.Event.LogOut -> backToLogin()
            }
        }

        viewModel.message.observe(this) { message ->
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAppEvent(event: AppEvent) {
        if (event is AppEvent.LogOut) {
            viewModel.logOut()
        }
    }

    private fun backToLogin() {
        hideKeyboard()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}