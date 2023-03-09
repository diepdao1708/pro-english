package com.example.proenglish.features.home

import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proenglish.R
import com.example.proenglish.databinding.FragmentHomeBinding
import com.example.proenglish.features.home.HomeViewModel.Event.Navigate
import com.example.proenglish.utils.LoadingUtils
import com.example.proenglish.utils.launchRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var dialog: Dialog? = null
    private val postAdapter: PostAdapter by lazy {
        val listener = object : PostAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                viewModel.navigateToPostDetail(position)
            }
        }
        PostAdapter(listener = listener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.postRecyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.getPosts()

        viewLifecycleOwner.launchRepeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.map { it.posts }
                    .distinctUntilChanged()
                    .collect {
                        postAdapter.reloadData(it)
                    }
            }
            launch {
                viewModel.uiState.map { it.isLoading }
                    .distinctUntilChanged()
                    .collect { isLoading ->
                        dialog = when (isLoading) {
                            true -> LoadingUtils.showLoading(requireActivity())
                            else -> {
                                dialog?.dismiss()
                                null
                            }
                        }
                    }
            }
            launch {
                viewModel.uiState.map { it.messageResId }
                    .distinctUntilChanged()
                    .collect { messageResId ->
                        Toast.makeText(
                            context,
                            messageResId?.let { getString(it) },
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            launch {
                viewModel.events.collect { event ->
                    when (event) {
                        is Navigate -> try {
                            Navigation.findNavController(binding.root)
                                .navigate(event.direction, event.bundle)
                        } catch (e: Exception) {
                            Log.e(
                                ContentValues.TAG,
                                "Failed to handle Event.Navigate with error:",
                                e,
                            )
                        }
                    }
                }
            }
        }

        return binding.root
    }
}