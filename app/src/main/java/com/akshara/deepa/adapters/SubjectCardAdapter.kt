package com.akshara.deepa.adapters

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.akshara.deepa.data.models.Subject
import com.akshara.deepa.data.models.SubjectStrength
import com.akshara.deepa.databinding.ItemSubjectCardBinding

class SubjectCardAdapter(
    private val subjects: List<Subject>,
    private val onClick:  (Subject) -> Unit
) : RecyclerView.Adapter<SubjectCardAdapter.VH>() {

    private val strengthMap = mutableMapOf<String, SubjectStrength>()

    fun updateStrengths(strengths: List<SubjectStrength>) {
        strengthMap.clear()
        strengths.forEach { strengthMap[it.subjectId] = it }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemSubjectCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = subjects.size
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(subjects[pos], strengthMap[subjects[pos].id])

    inner class VH(private val b: ItemSubjectCardBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(subject: Subject, st: SubjectStrength?) {
            b.clSubjectBg.setBackgroundResource(subject.backgroundRes)
            b.tvSubjectEmoji.text    = subject.emoji
            b.tvSubjectName.text     = subject.nameEn
            val done  = st?.chaptersCompleted ?: 0
            val total = st?.totalChapters     ?: 0
            val pct   = st?.masteryPercent?.toInt() ?: 0
            b.tvChapterInfo.text     = "$done/$total chapters"
            b.tvProgressPercent.text = "$pct%"
            ValueAnimator.ofInt(0, pct).apply {
                duration = 800; interpolator = DecelerateInterpolator()
                addUpdateListener { b.pbSubject.progress = it.animatedValue as Int }; start()
            }
            b.root.scaleX = 0.88f; b.root.scaleY = 0.88f; b.root.alpha = 0f
            b.root.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(320).setStartDelay(adapterPosition * 55L)
                .setInterpolator(DecelerateInterpolator()).start()
            b.root.setOnClickListener {
                it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(70).withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(70).start()
                }.start()
                onClick(subject)
            }
        }
    }
}
