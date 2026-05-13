package com.akshara.deepa.ui.strengthmap

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.databinding.FragmentStrengthMapBinding
import com.akshara.deepa.utils.SubjectConstants
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

class StrengthMapFragment : Fragment() {

    private var _b: FragmentStrengthMapBinding? = null
    private val b get() = _b!!
    private val prefs by lazy { (requireActivity().application as AksharaApp).prefs }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentStrengthMapBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadarChart()
        loadStrengths()
    }

    private fun setupRadarChart() {
        b.radarChart.apply {
            description.isEnabled = false
            setTouchEnabled(false)
            isRotationEnabled    = false
            webColor             = Color.parseColor("#BDBDBD")
            webColorInner        = Color.parseColor("#E0E0E0")
            webLineWidth         = 1.5f; webLineWidthInner = 1f; webAlpha = 180
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(
                    arrayOf("Science","Maths","Social","Kannada","English","Hindi","PE"))
                textSize = 10f; textColor = Color.parseColor("#212121")
            }
            yAxis.apply {
                setLabelCount(5, true); axisMinimum = 0f; axisMaximum = 100f; setDrawLabels(false)
            }
            legend.apply { isEnabled = true; textSize = 11f; form = Legend.LegendForm.CIRCLE }
        }
    }

    private fun loadStrengths() {
        lifecycleScope.launch {
            val repo      = (requireActivity().application as AksharaApp).repository
            val medium    = prefs.mediumCode
            val subjects  = SubjectConstants.getSubjects(medium)
            val strengths = repo.getSubjectStrengths(subjects, medium)

            val entries = strengths.take(7).map { RadarEntry(it.masteryPercent) }
            val dataSet = RadarDataSet(entries, "Mastery").apply {
                color                        = Color.parseColor("#BF360C")
                fillColor                    = Color.parseColor("#40BF360C")
                setDrawFilled(true); lineWidth = 2.5f
                valueTextSize                = 9f
                valueTextColor               = Color.parseColor("#BF360C")
                isDrawHighlightCircleEnabled = true
                setDrawHighlightIndicators(false)
            }
            b.radarChart.data = RadarData(dataSet)
            b.radarChart.invalidate()
            b.radarChart.animateXY(900, 900)

            val isEn  = prefs.isEnglish
            val sciId = if (isEn) "en_science"  else "kn_science"
            val matId = if (isEn) "en_maths"    else "kn_maths"
            val socId = if (isEn) "en_social"   else "kn_social"
            val kanId = if (isEn) "en_kannada1" else "kn_kannada1"
            val engId = if (isEn) "en_english1" else "kn_english2"

            strengths.find { it.subjectId == sciId }?.let { animBar(b.pbScience, b.tvScienceScore, it.masteryPercent) }
            strengths.find { it.subjectId == matId }?.let { animBar(b.pbMath,    b.tvMathScore,    it.masteryPercent) }
            strengths.find { it.subjectId == socId }?.let { animBar(b.pbSocial,  b.tvSocialScore,  it.masteryPercent) }
            strengths.find { it.subjectId == kanId }?.let { animBar(b.pbKannada, b.tvKannadaScore, it.masteryPercent) }
            strengths.find { it.subjectId == engId }?.let { animBar(b.pbEnglish, b.tvEnglishScore, it.masteryPercent) }

            val weak = strengths.filter { it.isWeak }
            b.tvGapAreas.text = when {
                weak.isEmpty() && strengths.all { it.masteryPercent == 0f } ->
                    "Read chapters and take quizzes to see your weak areas here."
                weak.isEmpty() ->
                    "Great work! No weak areas detected. Keep maintaining your progress."
                else -> weak.joinToString("\n") {
                    "\u2022 ${it.subjectNameEn}: ${it.masteryPercent.toInt()}% — needs more practice"
                }
            }
        }
    }

    private fun animBar(pb: ProgressBar, tv: TextView, pct: Float) {
        ValueAnimator.ofFloat(0f, pct).apply {
            duration = 1000; interpolator = DecelerateInterpolator()
            addUpdateListener {
                val v = (it.animatedValue as Float).toInt()
                pb.progress = v; tv.text = "$v%"
            }; start()
        }
    }

    override fun onResume()      { super.onResume(); loadStrengths() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
