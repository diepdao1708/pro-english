package com.example.proenglish.features.exam.question

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proenglish.R
import com.example.proenglish.databinding.ItemQuestionBinding
import com.example.proenglish.domain.models.Answer
import com.example.proenglish.utils.lifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class QuestionAdapter(
    private var questions: List<QuestionItemUiState> = emptyList(),
    private val listener: OnClickListener,
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val cachedItem = MutableStateFlow<QuestionItemUiState?>(null)

        init {
            binding.item = cachedItem
        }

        fun bind(questionItemUiState: QuestionItemUiState) {
            cachedItem.value = questionItemUiState
        }

        fun onAnswerClick() {
            binding.answers1Button.setOnClickListener {
                cachedItem.value?.selectedAnswer = when {
                    cachedItem.value?.selectedAnswer == null ||
                            cachedItem.value?.selectedAnswer != Answer.ANSWER1.value -> Answer.ANSWER1.value
                    else -> null
                }
                binding.answers1Button.setBackgroundResource(
                    if (cachedItem.value?.selectedAnswer != null)
                        R.drawable.background_selected_answer_button else R.drawable.background_answer_button
                )
                binding.answers2Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers3Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers4Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                listener.onAnswerClick(Answer.ANSWER1, adapterPosition)
            }

            binding.answers2Button.setOnClickListener {
                cachedItem.value?.selectedAnswer = when {
                    cachedItem.value?.selectedAnswer == null ||
                            cachedItem.value?.selectedAnswer != Answer.ANSWER2.value -> Answer.ANSWER2.value
                    else -> null
                }
                binding.answers1Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers2Button.setBackgroundResource(
                    if (cachedItem.value?.selectedAnswer != null)
                        R.drawable.background_selected_answer_button else R.drawable.background_answer_button
                )
                binding.answers3Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers4Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                listener.onAnswerClick(Answer.ANSWER2, adapterPosition)
            }
            binding.answers3Button.setOnClickListener {
                cachedItem.value?.selectedAnswer = when {
                    cachedItem.value?.selectedAnswer == null ||
                            cachedItem.value?.selectedAnswer != Answer.ANSWER3.value -> Answer.ANSWER3.value
                    else -> null
                }
                binding.answers1Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers2Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers3Button.setBackgroundResource(
                    if (cachedItem.value?.selectedAnswer != null)
                        R.drawable.background_selected_answer_button else R.drawable.background_answer_button
                )
                binding.answers4Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                listener.onAnswerClick(Answer.ANSWER3, adapterPosition)
            }
            binding.answers4Button.setOnClickListener {
                cachedItem.value?.selectedAnswer = when {
                    cachedItem.value?.selectedAnswer == null ||
                            cachedItem.value?.selectedAnswer != Answer.ANSWER4.value -> Answer.ANSWER4.value
                    else -> null
                }
                binding.answers1Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers2Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers3Button.setBackgroundResource(
                    R.drawable.background_answer_button
                )
                binding.answers4Button.setBackgroundResource(
                    if (cachedItem.value?.selectedAnswer != null)
                        R.drawable.background_selected_answer_button else R.drawable.background_answer_button
                )
                listener.onAnswerClick(Answer.ANSWER4, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding =
            ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.lifecycleOwner()
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.apply {
            bind(questions[position])
            onAnswerClick()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(questions: List<QuestionItemUiState>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onAnswerClick(answer: Answer, position: Int)
    }
}