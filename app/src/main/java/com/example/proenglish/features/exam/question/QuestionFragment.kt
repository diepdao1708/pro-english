package com.example.proenglish.features.exam.question

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
import com.example.proenglish.R
import com.example.proenglish.databinding.FragmentQuestionBinding
import com.example.proenglish.domain.models.Answer
import com.example.proenglish.utils.LoadingUtils
import com.example.proenglish.utils.launchRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private val viewModel: QuestionViewModel by viewModels()
    private var dialog: Dialog? = null
    private val questionAdapter: QuestionAdapter by lazy {
        val listener = object : QuestionAdapter.OnClickListener {
            override fun onAnswerClick(answer: Answer, position: Int) {
                viewModel.selectAnswer(answer, position)
            }
        }
        QuestionAdapter(listener = listener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_question, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.questionViewPage.apply {
            adapter = questionAdapter
        }

        viewModel.getQuestions(arguments)
        viewLifecycleOwner.launchRepeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.uiState.collect {
                    questionAdapter.reloadData(it.questions)
                }
            }
            launch {
                viewModel.uiState.map { it.score }
                    .distinctUntilChanged()
                    .collect {
                        Toast.makeText(
                            context,
                            it.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
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