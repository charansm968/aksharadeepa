package com.akshara.deepa.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.R
import com.akshara.deepa.data.models.Question
import com.akshara.deepa.data.models.QuizSession
import com.akshara.deepa.data.models.UserAnswer
import com.akshara.deepa.databinding.ActivityQuizBinding
import kotlinx.coroutines.launch
import java.util.UUID

class QuizActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CHAPTER_ID   = "chapter_id"
        const val EXTRA_CHAPTER_NAME = "chapter_name"
        const val EXTRA_SUBJECT_ID   = "subject_id"
        const val EXTRA_MEDIUM       = "medium"
        private const val TOTAL_MS   = 15 * 60 * 1000L
    }

    private lateinit var b: ActivityQuizBinding
    private val app   by lazy { application as AksharaApp }
    private val repo  by lazy { app.repository }
    private val prefs by lazy { app.prefs }

    private var questions   = listOf<Question>()
    private var currentIdx  = 0
    private var selectedOpt = ""
    private var score       = 0
    private val answers     = mutableListOf<UserAnswer>()
    private var timer: CountDownTimer? = null
    private var remainingMs = TOTAL_MS

    private lateinit var chapterId:   String
    private lateinit var chapterName: String
    private lateinit var subjectId:   String
    private lateinit var medium:      String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(b.root)

        chapterId   = intent.getStringExtra(EXTRA_CHAPTER_ID)   ?: ""
        chapterName = intent.getStringExtra(EXTRA_CHAPTER_NAME) ?: ""
        subjectId   = intent.getStringExtra(EXTRA_SUBJECT_ID)   ?: ""
        medium      = intent.getStringExtra(EXTRA_MEDIUM)       ?: prefs.mediumCode

        b.tvChapterTitle.text = chapterName
        b.btnBack.setOnClickListener { timer?.cancel(); finish() }
        loadQuestions()
    }

    private fun loadQuestions() {
        lifecycleScope.launch {
            questions = repo.getQuestionsForChapter(chapterId, medium, 10)
            if (questions.isEmpty()) {
                android.widget.Toast.makeText(this@QuizActivity,
                    "No questions available for this chapter.", android.widget.Toast.LENGTH_SHORT).show()
                finish(); return@launch
            }
            startTimer(); showQuestion()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(TOTAL_MS, 1000) {
            override fun onTick(ms: Long) {
                remainingMs = ms
                val mins = ms / 60000; val secs = (ms % 60000) / 1000
                b.tvTimer.text = String.format("%02d:%02d", mins, secs)
                if (ms < 60_000) b.tvTimer.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.error))
            }
            override fun onFinish() { autoSubmitAll() }
        }.start()
    }

    private fun showQuestion() {
        if (currentIdx >= questions.size) { finishQuiz(); return }
        val q = questions[currentIdx]
        selectedOpt = ""

        b.tvQuestionNum.text      = "Q ${currentIdx + 1} / ${questions.size}"
        b.tvScore.text            = "Score: $score"
        b.pbQuizProgress.max      = questions.size
        b.pbQuizProgress.progress = currentIdx + 1
        b.btnSubmit.text          = "Submit Answer"
        b.cvFeedback.visibility   = View.GONE

        resetCard(b.cvOption1); resetCard(b.cvOption2)
        resetCard(b.cvOption3); resetCard(b.cvOption4)

        b.scrollView.animate().alpha(0f).setDuration(100).withEndAction {
            b.tvQuestion.text = q.questionText
            b.tvOption1.text  = q.optionA; b.tvOption2.text = q.optionB
            b.tvOption3.text  = q.optionC; b.tvOption4.text = q.optionD
            b.scrollView.animate().alpha(1f).setDuration(200).start()
        }.start()

        b.cvOption1.setOnClickListener { selectOpt("A", b.cvOption1) }
        b.cvOption2.setOnClickListener { selectOpt("B", b.cvOption2) }
        b.cvOption3.setOnClickListener { selectOpt("C", b.cvOption3) }
        b.cvOption4.setOnClickListener { selectOpt("D", b.cvOption4) }
        b.btnSubmit.setOnClickListener { if (selectedOpt.isNotEmpty()) submitAnswer() }
    }

    private fun selectOpt(opt: String, card: CardView) {
        if (b.cvFeedback.visibility == View.VISIBLE) return
        selectedOpt = opt
        resetCard(b.cvOption1); resetCard(b.cvOption2)
        resetCard(b.cvOption3); resetCard(b.cvOption4)
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary_light))
        card.cardElevation = 8f
        card.animate().scaleX(1.02f).scaleY(1.02f).setDuration(80).withEndAction {
            card.animate().scaleX(1f).scaleY(1f).setDuration(60).start()
        }.start()
    }

    private fun resetCard(card: CardView) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        card.cardElevation = 2f; card.scaleX = 1f; card.scaleY = 1f
    }

    private fun submitAnswer() {
        val q = questions[currentIdx]
        val correct = selectedOpt == q.correctOption
        if (correct) score++
        highlight(selectedOpt, correct)
        if (!correct) highlight(q.correctOption, true)
        answers.add(UserAnswer(q, selectedOpt, correct))

        b.cvFeedback.visibility   = View.VISIBLE
        b.tvFeedbackTitle.text    = if (correct) "Correct!" else "Incorrect!"
        b.tvFeedbackTitle.setTextColor(ContextCompat.getColor(this,
            if (correct) R.color.success else R.color.error))
        b.tvCorrectAnswerInfo.text = "Correct: ${optText(q.correctOption, q)}"
        if (q.explanation.isNotEmpty()) b.tvCorrectAnswerInfo.append("\n${q.explanation}")
        b.tvScore.text = "Score: $score"

        b.cvFeedback.scaleX = 0.9f; b.cvFeedback.alpha = 0f
        b.cvFeedback.animate().scaleX(1f).alpha(1f).setDuration(220).start()

        if (currentIdx == questions.size - 1) {
            b.btnSubmit.text = "See Results"
            b.btnSubmit.setOnClickListener { finishQuiz() }
        } else {
            b.btnSubmit.text = "Next Question"
            b.btnSubmit.setOnClickListener { currentIdx++; showQuestion() }
        }
    }

    private fun highlight(opt: String, correct: Boolean) {
        val card = when(opt) { "A"->b.cvOption1; "B"->b.cvOption2; "C"->b.cvOption3; else->b.cvOption4 }
        card.setCardBackgroundColor(ContextCompat.getColor(this,
            if (correct) R.color.success_light else R.color.error_light))
        card.cardElevation = 5f
    }

    private fun optText(opt: String, q: Question) =
        when(opt) { "A"->q.optionA; "B"->q.optionB; "C"->q.optionC; else->q.optionD }

    private fun autoSubmitAll() {
        while (currentIdx < questions.size) {
            answers.add(UserAnswer(questions[currentIdx], selectedOpt, false)); currentIdx++
        }
        finishQuiz()
    }

    private fun finishQuiz() {
        timer?.cancel()
        val timeTaken = ((TOTAL_MS - remainingMs) / 1000).toInt()
        val scorePct  = if (questions.isNotEmpty()) (score * 100) / questions.size else 0
        lifecycleScope.launch {
            repo.updateQuizScore(chapterId, scorePct)
            repo.saveQuizSession(QuizSession(UUID.randomUUID().toString(),
                chapterId, subjectId, scorePct, questions.size, timeTaken))
        }
        startActivity(Intent(this, QuizReviewActivity::class.java).apply {
            putExtra(QuizReviewActivity.EXTRA_SCORE,        score)
            putExtra(QuizReviewActivity.EXTRA_TOTAL,        questions.size)
            putExtra(QuizReviewActivity.EXTRA_CHAPTER_NAME, chapterName)
            putExtra(QuizReviewActivity.EXTRA_SUBJECT_ID,   subjectId)
            putExtra(QuizReviewActivity.EXTRA_CHAPTER_ID,   chapterId)
            putExtra(QuizReviewActivity.EXTRA_MEDIUM,       medium)
            putStringArrayListExtra("q_ids",   ArrayList(answers.map { it.question.id }))
            putStringArrayListExtra("sel_opts",ArrayList(answers.map { it.selectedOption }))
            putExtra("correct", answers.map { it.isCorrect }.toBooleanArray())
        })
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onDestroy() { super.onDestroy(); timer?.cancel() }
}
