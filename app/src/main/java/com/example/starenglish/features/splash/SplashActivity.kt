package com.example.starenglish.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.starenglish.features.main.MainActivity
import com.example.starenglish.R
import com.example.starenglish.features.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.event.observe(this) {
            when (it) {
                SplashViewModel.Event.NavigateToLogin -> navigateTo(LoginActivity::class.java)
                SplashViewModel.Event.NavigateToHome -> navigateTo(MainActivity::class.java)
            }
        }

        viewModel.checkLogin()
    }

    private fun <T> navigateTo(cls: Class<T>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, cls)
            startActivity(intent)
            finish()
        }, 1800)
    }
}