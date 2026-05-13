package com.akshara.deepa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.akshara.deepa.R
import com.akshara.deepa.data.models.UserAnswer
import com.akshara.deepa.databinding.ItemQuizReviewBinding

class QuizReviewAdapter(private val answers: List<UserAnswer>) :
    RecyclerView.Adapter<QuizReviewAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemQuizReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = answers.size
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(answers[pos], pos + 1)

    inner class VH(private val b: ItemQuizReviewBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(ua: UserAnswer, num: Int) {
            val q = ua.question
            b.tvQNum.text = num.toString()
            b.tvReviewQuestion.text = q.questionText
            val selectedText = when (ua.selectedOption) {
                "A" -> q.optionA; "B" -> q.optionB
                "C" -> q.optionC; "D" -> q.optionD; else -> "Not answered"
            }
            val correctText = when (q.correctOption) {
                "A" -> q.optionA; "B" -> q.optionB
                "C" -> q.optionC; else -> q.optionD
            }
            b.tvYourAnswer.text  = "${ua.selectedOption}: $selectedText"
            b.tvCorrectAns.text  = "${q.correctOption}: $correctText"
            if (ua.isCorrect) {
                b.tvResultIcon.text  = "\u2705"
                b.tvResultLabel.text = "Correct"
                b.tvResultLabel.setTextColor(ContextCompat.getColor(b.root.context, R.color.success))
                b.tvYourAnswer.setTextColor(ContextCompat.getColor(b.root.context, R.color.success))
                b.root.setCardBackgroundColor(ContextCompat.getColor(b.root.context, R.color.success_light))
            } else {
                b.tvResultIcon.text  = "\u274C"
                b.tvResultLabel.text = "Wrong"
                b.tvResultLabel.setTextColor(ContextCompat.getColor(b.root.context, R.color.error))
                b.tvYourAnswer.setTextColor(ContextCompat.getColor(b.root.context, R.color.error))
                b.root.setCardBackgroundColor(ContextCompat.getColor(b.root.context, R.color.error_light))
            }
            b.tvCorrectAns.setTextColor(ContextCompat.getColor(b.root.context, R.color.success))
        }
    }
}
