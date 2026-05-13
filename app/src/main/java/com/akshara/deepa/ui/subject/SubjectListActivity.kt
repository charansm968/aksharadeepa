package com.akshara.deepa.ui.subject

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.data.models.Subject
import com.akshara.deepa.data.models.SubjectStrength
import com.akshara.deepa.databinding.ActivitySubjectListBinding
import com.akshara.deepa.databinding.ItemSubjectListBinding
import com.akshara.deepa.ui.chapter.ChapterListActivity
import com.akshara.deepa.ui.medium.MediumSelectActivity
import com.akshara.deepa.utils.SubjectConstants
import kotlinx.coroutines.launch

class SubjectListActivity : AppCompatActivity() {

    private lateinit var b: ActivitySubjectListBinding
    private val app   by lazy { application as AksharaApp }
    private val repo  by lazy { app.repository }
    private val prefs by lazy { app.prefs }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySubjectListBinding.inflate(layoutInflater)
        setContentView(b.root)
        val isEn = prefs.isEnglish
        b.tvMediumBadge.text = if (isEn) "English Medium" else "Kannada Medium"
        b.btnBack.setOnClickListener { finish() }
        b.tvChangeMedium.setOnClickListener {
            prefs.isSetupDone = false
            startActivity(Intent(this, MediumSelectActivity::class.java))
            finishAffinity()
        }
        loadSubjects()
    }

    override fun onResume() { super.onResume(); loadSubjects() }

    private fun loadSubjects() {
        lifecycleScope.launch {
            val medium   = prefs.mediumCode
            val subjects = SubjectConstants.getSubjects(medium)
            val strengths= repo.getSubjectStrengths(subjects, medium)
            val avgPct   = if (strengths.isNotEmpty()) strengths.map { it.masteryPercent }.average().toInt() else 0
            b.tvOverallPct.text = "$avgPct%"
            ValueAnimator.ofInt(0, avgPct).apply {
                duration = 900; interpolator = DecelerateInterpolator()
                addUpdateListener { b.pbOverall.progress = it.animatedValue as Int }; start()
            }
            val adapter = SubjectListAdapter(subjects, strengths, prefs.isEnglish) { subject ->
                // Navigate directly to this subject's chapter list
                startActivity(Intent(this@SubjectListActivity, ChapterListActivity::class.java).apply {
                    putExtra(ChapterListActivity.EXTRA_SUBJECT_ID,   subject.id)
                    putExtra(ChapterListActivity.EXTRA_SUBJECT_NAME, subject.displayName(prefs.isEnglish))
                    putExtra(ChapterListActivity.EXTRA_MEDIUM,       medium)
                    putExtra(ChapterListActivity.EXTRA_ASSET_FOLDER, subject.assetFolder)
                })
            }
            b.rvSubjects.layoutManager = LinearLayoutManager(this@SubjectListActivity)
            b.rvSubjects.adapter = adapter
        }
    }
}

class SubjectListAdapter(
    private val subjects: List<Subject>,
    private val strengths: List<SubjectStrength>,
    private val isEnglish: Boolean,
    private val onClick: (Subject) -> Unit
) : RecyclerView.Adapter<SubjectListAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemSubjectListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = subjects.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val s  = subjects[pos]
        val st = strengths.find { it.subjectId == s.id }
        h.bind(s, st, isEnglish, onClick)
    }

    inner class VH(private val b: ItemSubjectListBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(s: Subject, st: SubjectStrength?, isEn: Boolean, onClick: (Subject) -> Unit) {
            b.llSubjectHeader.setBackgroundResource(s.backgroundRes)
            b.tvEmoji.text       = s.emoji
            b.tvSubjectName.text = s.displayName(isEn)
            val done  = st?.chaptersCompleted ?: 0
            val total = st?.totalChapters     ?: 0
            val pct   = st?.masteryPercent?.toInt() ?: 0
            b.tvChapterCount.text = "$total chapters"
            b.tvDoneLabel.text    = "$done / $total completed"
            b.tvPct.text          = "$pct%"
            ValueAnimator.ofInt(0, pct).apply {
                duration = 800; interpolator = DecelerateInterpolator()
                addUpdateListener { b.pbSubject.progress = it.animatedValue as Int }; start()
            }
            b.root.alpha = 0f; b.root.translationX = 50f
            b.root.animate().alpha(1f).translationX(0f).setDuration(280)
                .setStartDelay(adapterPosition * 55L).setInterpolator(DecelerateInterpolator()).start()

            b.btnViewChapters.setOnClickListener { onClick(s) }
            b.root.setOnClickListener { onClick(s) }
        }
    }
}
