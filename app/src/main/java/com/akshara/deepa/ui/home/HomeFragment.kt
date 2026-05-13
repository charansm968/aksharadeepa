package com.akshara.deepa.ui.home

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.R
import com.akshara.deepa.adapters.RecentActivityAdapter
import com.akshara.deepa.adapters.SubjectCardAdapter
import com.akshara.deepa.data.models.ActivityItem
import com.akshara.deepa.data.models.Subject
import com.akshara.deepa.databinding.FragmentHomeBinding
import com.akshara.deepa.ui.chapter.ChapterListActivity
import com.akshara.deepa.ui.subject.SubjectListActivity
import com.akshara.deepa.utils.SubjectConstants
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val vm: HomeViewModel by viewModels {
        HomeViewModelFactory((requireActivity().application as AksharaApp).repository)
    }
    private val prefs by lazy { (requireActivity().application as AksharaApp).prefs }
    private lateinit var subjectAdapter: SubjectCardAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHomeBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGrid()
        setupRecent()
        observeVm()
        setGreeting()
        animateIn()

        // Issue #3 — "View All" navigates to SubjectListActivity
        b.btnViewAllSubjects.setOnClickListener {
            startActivity(Intent(requireContext(), SubjectListActivity::class.java))
        }
    }

    private fun setupGrid() {
        val subjects = SubjectConstants.getSubjects(prefs.mediumCode)
        subjectAdapter = SubjectCardAdapter(subjects) { subject ->
            // Issue #2 — clicking a subject card goes directly to that subject's chapters
            launchChapterList(subject)
        }
        b.rvSubjects.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = subjectAdapter
        }
    }

    private fun launchChapterList(subject: Subject) {
        startActivity(Intent(requireContext(), ChapterListActivity::class.java).apply {
            putExtra(ChapterListActivity.EXTRA_SUBJECT_ID,   subject.id)
            putExtra(ChapterListActivity.EXTRA_SUBJECT_NAME, subject.displayName(prefs.isEnglish))
            putExtra(ChapterListActivity.EXTRA_MEDIUM,       prefs.mediumCode)
            putExtra(ChapterListActivity.EXTRA_ASSET_FOLDER, subject.assetFolder)
        })
    }

    private fun setupRecent() {
        val isEn = prefs.isEnglish
        val tips = listOf(
            ActivityItem("Sci", if(isEn) "Science" else "ವಿಜ್ಞಾನ",
                if(isEn) "Start from Chapter 1" else "ಅಧ್ಯಾಯ 1 ಪ್ರಾರಂಭಿಸಿ",
                if(isEn) "Today" else "ಇಂದು", R.color.science_color),
            ActivityItem("Mat", if(isEn) "Mathematics" else "ಗಣಿತ",
                if(isEn) "Real Numbers ready" else "ನೈಜ ಸಂಖ್ಯೆಗಳು",
                if(isEn) "Today" else "ಇಂದು", R.color.math_color),
            ActivityItem("Goal", if(isEn) "Daily Goal" else "ದೈನಂದಿನ ಗುರಿ",
                if(isEn) "Complete 1 chapter daily" else "ಪ್ರತಿದಿನ 1 ಅಧ್ಯಾಯ",
                if(isEn) "Today" else "ಇಂದು", R.color.primary)
        )
        b.rvRecentActivity.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RecentActivityAdapter(tips)
        }
    }

    private fun observeVm() {
        vm.completedChapters.observe(viewLifecycleOwner) { animateCount(b.tvChaptersCount, it) }
        vm.quizCount.observe(viewLifecycleOwner)        { animateCount(b.tvQuizCount, it) }
        vm.streak.observe(viewLifecycleOwner)           { b.tvStreakCount.text = "$it" }
        vm.overallMastery.observe(viewLifecycleOwner) { m ->
            animatePb(b.pbOverallMastery, m)
            b.tvMasteryPercent.text = "$m%"
            val isEn = prefs.isEnglish
            b.tvMasteryLabel.text = when {
                m >= 80 -> if(isEn) "Outstanding! Keep it up!" else "ಅಪ್ರತಿಮ ಪ್ರಗತಿ!"
                m >= 60 -> if(isEn) "Great Progress!"          else "ಉತ್ತಮ ಪ್ರಗತಿ!"
                m >= 40 -> if(isEn) "Keep Going!"              else "ಮುಂದುವರಿಸಿ!"
                m > 0   -> if(isEn) "Good Start!"              else "ಒಳ್ಳೆಯ ಪ್ರಾರಂಭ!"
                else    -> if(isEn) "Start Learning Now!"      else "ಈಗ ಕಲಿಕೆ ಆರಂಭಿಸಿ!"
            }
        }
        vm.subjectStrengths.observe(viewLifecycleOwner) { subjectAdapter.updateStrengths(it) }
        vm.todayGoalMet.observe(viewLifecycleOwner) { b.tvGoalStatus.text = if(it) "\u2705" else "\u23F3" }
    }

    private fun setGreeting() {
        val h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val isEn = prefs.isEnglish
        b.tvGreeting.text = when {
            h < 12 -> if(isEn) "Good Morning! \u2600\uFE0F" else "ಶುಭ ಬೆಳಿಗ್ಗೆ! \u2600\uFE0F"
            h < 17 -> if(isEn) "Good Afternoon!" else "ಶುಭ ಮಧ್ಯಾಹ್ನ!"
            else   -> if(isEn) "Good Evening! \uD83C\uDF19" else "ಶುಭ ಸಂಜೆ! \uD83C\uDF19"
        }
        b.tvAppTitle.text  = if(isEn) "Akshara Deepa" else "\u0C85\u0C95\u0CCD\u0CB7\u0CB0 \u0CA6\u0CC0\u0CAA"
        b.tvSubtitle.text  = if(isEn) "SSLC Class 10 \u2022 English Medium"
                             else "SSLC 10\u0CA8\u0CC7 \u0CA4\u0CB0\u0C97\u0CA4\u0CBF"
    }

    private fun animateIn() {
        listOf(b.headerLayout, b.cvOverallMastery, b.cvDailyGoal).forEachIndexed { i, v ->
            v.alpha = 0f; v.translationY = 40f
            v.animate().alpha(1f).translationY(0f).setDuration(400)
                .setStartDelay(i * 100L).setInterpolator(DecelerateInterpolator()).start()
        }
    }

    private fun animateCount(tv: android.widget.TextView, to: Int) {
        ValueAnimator.ofInt(0, to).apply {
            duration = 800; interpolator = DecelerateInterpolator()
            addUpdateListener { tv.text = it.animatedValue.toString() }; start()
        }
    }

    private fun animatePb(pb: android.widget.ProgressBar, to: Int) {
        ValueAnimator.ofInt(0, to).apply {
            duration = 1000; interpolator = DecelerateInterpolator()
            addUpdateListener { pb.progress = it.animatedValue as Int }; start()
        }
    }

    override fun onResume() { super.onResume(); vm.refreshStats(prefs.mediumCode) }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
