package com.akshara.deepa.ui.home

import androidx.lifecycle.*
import com.akshara.deepa.data.models.SubjectStrength
import com.akshara.deepa.data.repository.AksharaRepository
import com.akshara.deepa.utils.SubjectConstants
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: AksharaRepository) : ViewModel() {
    private val _completedChapters = MutableLiveData(0)
    val completedChapters: LiveData<Int> = _completedChapters
    private val _quizCount = MutableLiveData(0)
    val quizCount: LiveData<Int> = _quizCount
    private val _streak = MutableLiveData(0)
    val streak: LiveData<Int> = _streak
    private val _overallMastery = MutableLiveData(0)
    val overallMastery: LiveData<Int> = _overallMastery
    private val _subjectStrengths = MutableLiveData<List<SubjectStrength>>(emptyList())
    val subjectStrengths: LiveData<List<SubjectStrength>> = _subjectStrengths
    private val _todayGoalMet = MutableLiveData(false)
    val todayGoalMet: LiveData<Boolean> = _todayGoalMet

    init {
        viewModelScope.launch { repo.getTotalQuizCount().collect { _quizCount.value = it } }
    }

    fun refreshStats(medium: String) {
        viewModelScope.launch {
            repo.getCompletedCount(medium).collect { _completedChapters.value = it }
        }
        viewModelScope.launch {
            _streak.value = repo.getStreakDays()
            val subjects  = SubjectConstants.getSubjects(medium)
            val strengths = repo.getSubjectStrengths(subjects, medium)
            _subjectStrengths.value = strengths
            _overallMastery.value   = if (strengths.isNotEmpty())
                strengths.map { it.masteryPercent }.average().toInt() else 0
            _todayGoalMet.value = repo.getTodayActivity().goalMet
        }
    }
}

class HomeViewModelFactory(private val repo: AksharaRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeViewModel(repo) as T
}
