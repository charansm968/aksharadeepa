package com.akshara.deepa.ui.chapter

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.R
import com.akshara.deepa.data.models.Chapter
import com.akshara.deepa.databinding.ActivityChapterListBinding
import com.akshara.deepa.databinding.ItemChapterV2Binding
import com.akshara.deepa.ui.quiz.QuizActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChapterListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUBJECT_ID   = "subject_id"
        const val EXTRA_SUBJECT_NAME = "subject_name"
        const val EXTRA_MEDIUM       = "medium"
        const val EXTRA_ASSET_FOLDER = "asset_folder"
    }

    private lateinit var b: ActivityChapterListBinding
    private val app   by lazy { application as AksharaApp }
    private val repo  by lazy { app.repository }
    private val prefs by lazy { app.prefs }

    private lateinit var subjectId:   String
    private lateinit var subjectName: String
    private lateinit var medium:      String
    private lateinit var assetFolder: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChapterListBinding.inflate(layoutInflater)
        setContentView(b.root)

        subjectId   = intent.getStringExtra(EXTRA_SUBJECT_ID)   ?: ""
        subjectName = intent.getStringExtra(EXTRA_SUBJECT_NAME) ?: ""
        medium      = intent.getStringExtra(EXTRA_MEDIUM)       ?: prefs.mediumCode
        assetFolder = intent.getStringExtra(EXTRA_ASSET_FOLDER) ?: ""

        b.tvSubjectTitle.text = subjectName
        b.btnBack.setOnClickListener { finish() }

        // Textbook PDF button — only show if this subject has bundled PDFs
        loadChapters()
    }

    override fun onResume() { super.onResume(); loadChapters() }

    private fun loadChapters() {
        lifecycleScope.launch {
            repo.getChaptersBySubject(subjectId, medium).collectLatest { chapters ->
                updateHeader(chapters)
                b.rvChapters.layoutManager = LinearLayoutManager(this@ChapterListActivity)
                b.rvChapters.adapter = ChapterAdapter(
                    chapters    = chapters,
                    isEnglish   = prefs.isEnglish,
                    assetFolder = assetFolder,
                    onReadClick = { ch, title ->
                        startActivity(Intent(this@ChapterListActivity, ChapterReaderActivity::class.java).apply {
                            putExtra(ChapterReaderActivity.EXTRA_ASSET_FOLDER, assetFolder)
                            putExtra(ChapterReaderActivity.EXTRA_CHAPTER_NUM,  ch.number)
                            putExtra(ChapterReaderActivity.EXTRA_CHAPTER_ID,   ch.id)
                            putExtra(ChapterReaderActivity.EXTRA_CHAPTER_NAME, title)
                            putExtra(ChapterReaderActivity.EXTRA_IS_ENGLISH,   prefs.isEnglish)
                            putExtra(ChapterReaderActivity.EXTRA_READ_PROGRESS,ch.readProgressPercent)
                        })
                    },
                    onQuizClick = { ch ->
                        // Issue #7 — quiz only unlocks when chapter is completed
                        if (ch.isCompleted) {
                            lifecycleScope.launch {
                                if (repo.hasQuestions(ch.id)) {
                                    startActivity(Intent(this@ChapterListActivity, QuizActivity::class.java).apply {
                                        putExtra(QuizActivity.EXTRA_CHAPTER_ID,   ch.id)
                                        putExtra(QuizActivity.EXTRA_CHAPTER_NAME, ch.nameOriginal)
                                        putExtra(QuizActivity.EXTRA_SUBJECT_ID,   subjectId)
                                        putExtra(QuizActivity.EXTRA_MEDIUM,       medium)
                                    })
                                } else {
                                    android.widget.Toast.makeText(this@ChapterListActivity,
                                        "No quiz available for this chapter yet.", android.widget.Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun updateHeader(chapters: List<Chapter>) {
        val done  = chapters.count { it.isCompleted }
        val total = chapters.size
        val pct   = if (total > 0) done * 100 / total else 0
        b.tvChaptersDone.text = "$done / $total chapters completed"
        b.tvSubjectPct.text   = "$pct%"
        ValueAnimator.ofInt(0, pct).apply {
            duration = 700; interpolator = DecelerateInterpolator()
            addUpdateListener { b.pbSubject.progress = it.animatedValue as Int }; start()
        }
    }
}

class ChapterAdapter(
    private val chapters:    List<Chapter>,
    private val isEnglish:   Boolean,
    private val assetFolder: String,
    private val onReadClick: (Chapter, String) -> Unit,
    private val onQuizClick: (Chapter) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemChapterV2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = chapters.size
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(chapters[pos])

    inner class VH(private val b: ItemChapterV2Binding) : RecyclerView.ViewHolder(b.root) {
        fun bind(ch: Chapter) {
            b.tvChapterNum.text  = ch.number.toString()
            // Issue #5 — show original chapter name (Kannada stays Kannada, Hindi stays Hindi)
            val displayTitle = chapterDisplayTitle(assetFolder, ch)
            b.tvChapterName.text = displayTitle
            b.tvTopicsRead.text  = "Read progress: ${ch.readProgressPercent}%"

            // Quiz score or locked indicator
            if (ch.isCompleted) {
                if (ch.quizScore >= 0) {
                    val c = when {
                        ch.quizScore >= 80 -> R.color.success
                        ch.quizScore >= 60 -> R.color.warning
                        else               -> R.color.error
                    }
                    b.tvQuizBadge.text = "Quiz: ${ch.quizScore}%"
                    b.tvQuizBadge.setTextColor(ContextCompat.getColor(b.root.context, c))
                } else {
                    b.tvQuizBadge.text = "Quiz available"
                    b.tvQuizBadge.setTextColor(ContextCompat.getColor(b.root.context, R.color.success))
                }
            } else {
                b.tvQuizBadge.text = "Complete chapter to unlock quiz"
                b.tvQuizBadge.setTextColor(ContextCompat.getColor(b.root.context, R.color.text_hint))
            }

            // Progress bar: based on readProgressPercent + quiz bonus
            val readProg = ch.readProgressPercent
            val quizBonus = if (ch.quizScore >= 0) minOf(20, ch.quizScore / 5) else 0
            val displayPct = minOf(100, readProg + quizBonus)
            ValueAnimator.ofInt(0, displayPct).apply {
                duration = 600; interpolator = DecelerateInterpolator()
                addUpdateListener { b.pbChapter.progress = it.animatedValue as Int }; start()
            }

            // Issue #4 — NO checkbox; completion happens only via scrolling
            // Hide the old checkbox if layout still has it (use visibility)
            try { b.cbCompleted.visibility = android.view.View.GONE } catch (_: Exception) {}

            // Status badge
            b.tvChapterName.setTextColor(ContextCompat.getColor(b.root.context,
                if (ch.isCompleted) R.color.success else R.color.text_primary))

            // Syllabus/Read button - opens PDF reader
            b.btnSyllabus.text = if (assetFolder.isNotEmpty()) "Read Syllabus" else "Syllabus"
            b.btnSyllabus.setOnClickListener { onReadClick(ch, displayTitle) }

            // Quiz button — locked until completed
            b.btnQuiz.isEnabled = ch.isCompleted
            b.btnQuiz.alpha     = if (ch.isCompleted) 1f else 0.4f
            b.btnQuiz.text      = if (ch.isCompleted) "Take Quiz" else "Locked"
            b.btnQuiz.setOnClickListener { onQuizClick(ch) }

            // Entrance animation
            b.root.alpha = 0f; b.root.translationX = 40f
            b.root.animate().alpha(1f).translationX(0f).setDuration(260)
                .setStartDelay(adapterPosition * 35L).setInterpolator(DecelerateInterpolator()).start()
        }
    }

    private fun chapterDisplayTitle(assetFolder: String, ch: Chapter): String =
        correctedTitles[assetFolder]?.get(ch.number) ?: ch.nameOriginal
}

private val correctedTitles = mapOf(
    "en/maths" to mapOf(
        11 to "Areas Related to Circles",
        12 to "Surface Areas and Volumes",
        13 to "Statistics",
        14 to "Probability"
    ),
    "kn/maths" to mapOf(
        11 to "ವೃತ್ತಗಳಿಗೆ ಸಂಬಂಧಿಸಿದ ವಿಸ್ತೀರ್ಣಗಳು",
        12 to "ಮೇಲ್ಮೈ ವಿಸ್ತೀರ್ಣ ಮತ್ತು ಘನಫಲಗಳು",
        13 to "ಸಂಖ್ಯಾಶಾಸ್ತ್ರ",
        14 to "ಸಂಭವನೀಯತೆ"
    ),
    "en/pe" to mapOf(
        1 to "Modern Olympics and Asian Games",
        2 to "Volley Ball",
        3 to "Volleyball",
        4 to "Hockey",
        5 to "Hockey",
        6 to "Athletics",
        7 to "Hurdles Race",
        8 to "Walking Race",
        9 to "Walking Race",
        10 to "Aerobics",
        11 to "Community Health",
        12 to "Rhythmic Activities",
        13 to "First Aid",
        14 to "Lifestyle Diseases",
        15 to "Drill and Marching",
        16 to "Hand Ball",
        17 to "Handball",
        18 to "Basket Ball",
        19 to "Basket Ball",
        20 to "Badminton",
        21 to "Badminton",
        22 to "Discus Throw",
        23 to "Discus Throw",
        24 to "Meditation",
        25 to "Yogasana",
        26 to "Communicable Diseases",
        27 to "Self Defence Techniques",
        28 to "National Integration",
        29 to "National Integration",
        30 to "Recreational Games"
    ),
    "kn/pe" to mapOf(
        1 to "Modern Olympics and Asian Games",
        2 to "Volley Ball",
        3 to "Volleyball",
        4 to "Hockey",
        5 to "Hockey",
        6 to "Athletics",
        7 to "Hurdles Race",
        8 to "Walking Race",
        9 to "Walking Race",
        10 to "Aerobics",
        11 to "Community Health",
        12 to "Rhythmic Activities",
        13 to "First Aid",
        14 to "Lifestyle Diseases",
        15 to "Drill and Marching",
        16 to "Hand Ball",
        17 to "Handball",
        18 to "Basket Ball",
        19 to "Basket Ball",
        20 to "Badminton",
        21 to "Badminton",
        22 to "Discus Throw",
        23 to "Discus Throw",
        24 to "Meditation",
        25 to "Yogasana",
        26 to "Communicable Diseases",
        27 to "Self Defence Techniques",
        28 to "National Integration",
        29 to "National Integration",
        30 to "Recreational Games"
    )
)
