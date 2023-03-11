package com.example.proenglish.features.dictionary

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proenglish.R
import com.example.proenglish.databinding.FragmentDictionaryBinding
import com.example.proenglish.utils.LoadingUtils
import com.example.proenglish.utils.launchRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    private val viewModel: DictionaryViewModel by viewModels()
    private var dialog: Dialog? = null
    private val meaningAdapter: MeaningAdapter by lazy {
        MeaningAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_dictionary, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.meaningRecyclerView.apply {
            adapter = meaningAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewLifecycleOwner.launchRepeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.collect {
                    binding.dictionary = it
                    meaningAdapter.reloadData(it.meanings)
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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchView.clearFocus()
                query.let { viewModel.takeMeaning(it) }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        return binding.root
    }
}