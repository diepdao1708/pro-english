package com.example.proenglish.features.exam

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.example.proenglish.R
import com.example.proenglish.databinding.FragmentExamBinding
import com.example.proenglish.data.models.Level
import com.example.proenglish.features.exam.ExamViewModel.Event
import com.example.proenglish.utils.launchRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExamFragment : Fragment() {

    private lateinit var binding: FragmentExamBinding
    private val viewModel: ExamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_exam, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.easyCardView.setOnClickListener {
            viewModel.navigateToQuestion(Level.EASY)
        }
        binding.mediumCardView.setOnClickListener {
            viewModel.navigateToQuestion(Level.MEDIUM)
        }
        binding.hardCardView.setOnClickListener {
            viewModel.navigateToQuestion(Level.HARD)
        }

        viewLifecycleOwner.launchRepeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.events.collect { event ->
                    when (event) {
                        is Event.Navigate -> try {
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