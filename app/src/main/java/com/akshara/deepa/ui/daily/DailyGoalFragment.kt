package com.akshara.deepa.ui.daily

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.R
import com.akshara.deepa.data.models.DailyActivity
import com.akshara.deepa.databinding.FragmentDailyGoalBinding
import com.akshara.deepa.utils.SubjectConstants
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DailyGoalFragment : Fragment() {

    private var _b: FragmentDailyGoalBinding? = null
    private val b get() = _b!!
    private val prefs by lazy { (requireActivity().application as AksharaApp).prefs }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentDailyGoalBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDailyData()
        setMotivationalQuote()
    }

    private fun loadDailyData() {
        lifecycleScope.launch {
            val repo      = (requireActivity().application as AksharaApp).repository
            val isEn      = prefs.isEnglish
            val streak    = repo.getStreakDays()
            val today     = repo.getTodayActivity()
            val lastSeven = repo.getLastSevenDays()

            ValueAnimator.ofInt(0, streak).apply {
                duration = 900; interpolator = DecelerateInterpolator()
                addUpdateListener { b.tvStreakNumber.text = it.animatedValue.toString() }; start()
            }
            b.tvStreakMessage.text = when {
                streak >= 7  -> if (isEn) "1-Week Streak! Amazing!" else "ಒಂದು ವಾರ ಸರಣಿ! ಅದ್ಭುತ!"
                streak >= 3  -> if (isEn) "$streak-Day Streak! Keep it up!" else "$streak ದಿನ ಸರಣಿ! ಮುಂದುವರಿಸಿ!"
                streak == 1  -> if (isEn) "Great Start! Keep Going!" else "ಒಳ್ಳೆಯ ಪ್ರಾರಂಭ!"
                else         -> if (isEn) "Start your streak today!" else "ಇಂದೇ ಕಲಿಕೆ ಪ್ರಾರಂಭಿಸಿ!"
            }

            b.tvTodayChapters.text = today.chaptersStudied.toString()
            b.tvTodayQuizzes.text  = today.quizzesTaken.toString()

            val goalPct = minOf(100, today.chaptersStudied * 100)
            ValueAnimator.ofInt(0, goalPct).apply {
                duration = 800; interpolator = DecelerateInterpolator()
                addUpdateListener { b.pbDailyGoal.progress = it.animatedValue as Int }; start()
            }
            b.tvGoalText.text = when {
                today.goalMet -> if (isEn) "Today's goal achieved! Well done!" else "ಇಂದಿನ ಗುರಿ ಸಾಧಿಸಲಾಗಿದೆ!"
                else          -> if (isEn) "Read and complete one chapter to reach today's goal!" else "ಒಂದು ಅಧ್ಯಾಯ ಓದಿ ಗುರಿ ತಲುಪಿ!"
            }
            buildWeekCalendar(lastSeven)
        }
    }

    private fun buildWeekCalendar(activities: List<DailyActivity>) {
        b.llWeekCalendar.removeAllViews()
        val sdf     = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today   = sdf.format(Date())
        val tempCal = Calendar.getInstance()
        val days    = (6 downTo 0).map { offset ->
            tempCal.time = Date(); tempCal.add(Calendar.DAY_OF_YEAR, -offset)
            val key    = sdf.format(tempCal.time)
            val dayIdx = (tempCal.get(Calendar.DAY_OF_WEEK) + 5) % 7
            key to listOf("Mo","Tu","We","Th","Fr","Sa","Su")[dayIdx]
        }
        days.forEachIndexed { index, (dateKey, dayLabel) ->
            val isMet   = activities.find { it.dateKey == dateKey }?.goalMet == true
            val isToday = dateKey == today
            val container = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                gravity     = android.view.Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                alpha = 0f; translationY = 20f
            }
            val dot = TextView(requireContext()).apply {
                text     = when { isMet -> "\u2705"; isToday -> "\uD83D\uDCCC"; else -> "\u26AA" }
                textSize = 22f; gravity = android.view.Gravity.CENTER
                alpha    = if (isMet || isToday) 1f else 0.4f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ).also { it.bottomMargin = 4 }
            }
            val label = TextView(requireContext()).apply {
                text     = dayLabel; textSize = 11f; gravity = android.view.Gravity.CENTER
                setTextColor(if (isToday) ContextCompat.getColor(requireContext(), R.color.primary)
                             else ContextCompat.getColor(requireContext(), R.color.text_secondary))
            }
            container.addView(dot); container.addView(label)
            container.animate().alpha(1f).translationY(0f).setDuration(280)
                .setStartDelay(index * 50L).setInterpolator(DecelerateInterpolator()).start()
            b.llWeekCalendar.addView(container)
        }
    }

    private fun setMotivationalQuote() {
        val quotes = SubjectConstants.quotes(prefs.isEnglish)
        b.tvMotivation.text = quotes[(System.currentTimeMillis() / 86400000).toInt() % quotes.size]
    }

    override fun onResume()      { super.onResume(); loadDailyData(); setMotivationalQuote() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
