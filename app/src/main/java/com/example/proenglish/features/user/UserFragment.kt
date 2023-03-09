package com.example.proenglish.features.user

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proenglish.R
import com.example.proenglish.databinding.FragmentUserBinding
import com.example.proenglish.utils.LoadingUtils
import com.example.proenglish.utils.launchRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModels()
    private var dialog: Dialog? = null
    private val leaderboardAdapter: LeaderboardAdapter by lazy {
        LeaderboardAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_user, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.leaderboardRecyclerView.apply {
            adapter = leaderboardAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.getCurrentUser()
        viewModel.getLeaderboard()

        viewLifecycleOwner.launchRepeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.collect {
                    binding.userUiState = it
                    leaderboardAdapter.reloadData(it.users)
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
        }

        return binding.root
    }
}