package com.example.proenglish.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.proenglish.R
import com.example.proenglish.databinding.ActivityMainBinding
import com.example.proenglish.features.login.LoginActivity
import com.example.proenglish.utils.AppEvent
import com.example.proenglish.utils.hideKeyboard
import com.example.proenglish.utils.setKeyboardVisibilityListener
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.viewModel = viewModel

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setKeyboardVisibilityListener { visibility ->
                binding.bottomNavigationView.isVisible = !visibility
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.dictionaryFragment, R.id.examFragment, R.id.userFragment -> showBottomNavigation()
                else -> hideBottomNavigation()
            }

        }

        viewModel.event.observe(this) { event ->
            when (event) {
                is MainViewModel.Event.LogOut -> backToLogin()
            }
        }

        viewModel.message.observe(this) { message ->
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE

    }

    private fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAppEvent(event: AppEvent) {
        if (event is AppEvent.LogOut) {
            viewModel.logOut()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun backToLogin() {
        hideKeyboard()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}