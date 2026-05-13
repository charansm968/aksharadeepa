package com.akshara.deepa.data.repository

import com.akshara.deepa.data.db.*
import com.akshara.deepa.data.models.*
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class AksharaRepository(
    private val chapterDao:       ChapterDao,
    private val questionDao:      QuestionDao,
    private val quizSessionDao:   QuizSessionDao,
    private val dailyActivityDao: DailyActivityDao
) {
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getChaptersBySubject(sid: String, m: String): Flow<List<Chapter>> = chapterDao.getBySubject(sid, m)
    fun getCompletedCount(m: String): Flow<Int> = chapterDao.getCompletedCount(m)
    fun getTotalQuizCount(): Flow<Int> = quizSessionDao.getTotalCount()
    suspend fun getChapterById(id: String): Chapter? = chapterDao.getById(id)

    /** Update read-scroll progress (0-100). Auto-completes chapter at 100%. */
    suspend fun updateReadProgress(chapterId: String, percent: Int) {
        chapterDao.setReadProgress(chapterId, percent)
        if (percent >= 100) {
            val ch = chapterDao.getById(chapterId)
            if (ch != null && !ch.isCompleted) {
                chapterDao.setCompleted(chapterId, true)
                ensureToday(); dailyActivityDao.incChapters(today())
            }
        }
    }

    suspend fun updateQuizScore(chapterId: String, score: Int) {
        chapterDao.updateQuizScore(chapterId, score, System.currentTimeMillis())
        ensureToday(); dailyActivityDao.incQuizzes(today())
    }

    suspend fun getQuestionsForChapter(cid: String, m: String, n: Int = 10): List<Question> =
        questionDao.getForChapter(cid, m, n)
    suspend fun getQuestionById(id: String): Question? = questionDao.getById(id)
    suspend fun hasQuestions(cid: String): Boolean = questionDao.countForChapter(cid) > 0

    suspend fun saveQuizSession(s: QuizSession) = quizSessionDao.insert(s)

    suspend fun getSubjectStrengths(subjects: List<Subject>, m: String): List<SubjectStrength> =
        subjects.map { s ->
            val done  = chapterDao.getCompletedForSubject(s.id, m)
            val total = chapterDao.getTotalForSubject(s.id, m)
            val quiz  = chapterDao.getAvgQuiz(s.id, m) ?: 0f
            val read  = chapterDao.getAvgReadProgress(s.id, m) ?: 0f
            val comp  = if (total > 0) done.toFloat() / total * 100f else 0f
            val mastery = (comp * 0.4f + quiz * 0.4f + read * 0.2f).coerceAtMost(100f)
            SubjectStrength(s.id, s.nameEn, mastery, done, total, quiz, mastery < 40f && done > 0)
        }

    suspend fun getTodayActivity(): DailyActivity =
        dailyActivityDao.getForDate(today()) ?: DailyActivity(today())
    suspend fun getLastSevenDays(): List<DailyActivity> = dailyActivityDao.getLast7()
    suspend fun getStreakDays(): Int {
        val days = dailyActivityDao.getLast7()
        val cal  = Calendar.getInstance(); var streak = 0
        for (i in 0..6) {
            val k = sdf.format(cal.time)
            if (days.find { it.dateKey == k && it.goalMet } != null) streak++
            else if (i > 0) break
            cal.add(Calendar.DAY_OF_YEAR, -1)
        }
        return streak
    }

    private fun today() = sdf.format(Date())
    private suspend fun ensureToday() {
        val k = today()
        if (dailyActivityDao.getForDate(k) == null) dailyActivityDao.upsert(DailyActivity(k))
    }
}
