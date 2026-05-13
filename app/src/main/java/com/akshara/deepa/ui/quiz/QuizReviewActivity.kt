package com.akshara.deepa.ui.quiz

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.adapters.QuizReviewAdapter
import com.akshara.deepa.data.models.UserAnswer
import com.akshara.deepa.databinding.ActivityQuizReviewBinding
import kotlinx.coroutines.launch

class QuizReviewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE        = "score"
        const val EXTRA_TOTAL        = "total"
        const val EXTRA_CHAPTER_NAME = "chapter_name"
        const val EXTRA_SUBJECT_ID   = "subject_id"
        const val EXTRA_CHAPTER_ID   = "chapter_id"
        const val EXTRA_MEDIUM       = "medium"
    }

    private lateinit var b: ActivityQuizReviewBinding
    private val repo  by lazy { (application as AksharaApp).repository }
    private val prefs by lazy { (application as AksharaApp).prefs }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityQuizReviewBinding.inflate(layoutInflater)
        setContentView(b.root)

        val score       = intent.getIntExtra(EXTRA_SCORE, 0)
        val total       = intent.getIntExtra(EXTRA_TOTAL, 10)
        val chapterName = intent.getStringExtra(EXTRA_CHAPTER_NAME) ?: ""
        val chapterId   = intent.getStringExtra(EXTRA_CHAPTER_ID)   ?: ""
        val subjectId   = intent.getStringExtra(EXTRA_SUBJECT_ID)   ?: ""
        val medium      = intent.getStringExtra(EXTRA_MEDIUM)       ?: prefs.mediumCode
        val pct         = if (total > 0) (score * 100) / total else 0
        val wrong       = total - score

        b.tvSubjectChapter.text = chapterName
        b.tvCorrectCount.text   = score.toString()
        b.tvWrongCount.text     = wrong.toString()

        ValueAnimator.ofInt(0, pct).apply {
            duration = 1200; interpolator = DecelerateInterpolator()
            addUpdateListener {
                b.tvScoreNum.text     = "$score/$total"
                b.tvScorePercent.text = "${it.animatedValue as Int}%"
            }; start()
        }

        val (emoji, title) = when {
            pct >= 80 -> "\uD83C\uDFC6" to "Excellent Work!"
            pct >= 60 -> "\u2B50"       to "Good Job!"
            pct >= 40 -> "\uD83D\uDCDA" to "Keep Practising!"
            else      -> "\uD83D\uDCAA" to "Try Again!"
        }
        b.tvResultEmoji.text  = emoji
        b.tvResultTitle.text  = title

        val scX = ObjectAnimator.ofFloat(b.tvResultEmoji, "scaleX", 0f, 1.2f, 1f).apply { duration = 600 }
        val scY = ObjectAnimator.ofFloat(b.tvResultEmoji, "scaleY", 0f, 1.2f, 1f).apply { duration = 600 }
        AnimatorSet().apply { playTogether(scX, scY); start() }

        val qIds    = intent.getStringArrayListExtra("q_ids")    ?: arrayListOf()
        val selOpts = intent.getStringArrayListExtra("sel_opts") ?: arrayListOf()
        val corrects= intent.getBooleanArrayExtra("correct")     ?: BooleanArray(0)

        lifecycleScope.launch {
            val userAnswers = qIds.mapIndexedNotNull { i, id ->
                val q = repo.getQuestionById(id) ?: return@mapIndexedNotNull null
                UserAnswer(q, selOpts.getOrElse(i) { "" }, corrects.getOrElse(i) { false })
            }
            b.rvReview.layoutManager = LinearLayoutManager(this@QuizReviewActivity)
            b.rvReview.adapter       = QuizReviewAdapter(userAnswers)
        }

        b.btnTryAgain.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java).apply {
                putExtra(QuizActivity.EXTRA_CHAPTER_ID,   chapterId)
                putExtra(QuizActivity.EXTRA_CHAPTER_NAME, chapterName)
                putExtra(QuizActivity.EXTRA_SUBJECT_ID,   subjectId)
                putExtra(QuizActivity.EXTRA_MEDIUM,       medium)
            }); finish()
        }
        b.btnBackToSyllabus.setOnClickListener { finish() }
    }
}
