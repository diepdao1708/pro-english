package com.example.proenglish.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.proenglish.R
import com.example.proenglish.features.main.MainActivity
import com.example.proenglish.features.onboarding.OnBoardingActivity
import com.example.proenglish.features.splash.SplashViewModel.*
import com.example.proenglish.utils.setFullScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setFullScreen()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.events.collect {
                    when (it) {
                        Event.NavigateToHome -> navigateTo(MainActivity::class.java)
                        Event.NavigateToOnBoarding -> navigateTo(OnBoardingActivity::class.java)
                    }
                }
            }
        }

        viewModel.checkLogin()
    }

    private fun <T> navigateTo(cls: Class<T>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, cls)
            startActivity(intent)
            finish()
        }, 3000)
    }
}