package com.akshara.deepa.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Medium(val code: String, val displayName: String, val isEnglish: Boolean) {
    ENGLISH("en","English Medium",true),
    KANNADA("kn","Kannada Medium",false)
}

data class Subject(
    val id: String,
    val mediumCode: String,
    val nameEn: String,
    val nameKn: String,
    val emoji: String,
    val colorRes: Int,
    val backgroundRes: Int,
    val assetFolder: String = ""  // relative folder under pdfs/
) { fun displayName(isEnglish: Boolean) = if (isEnglish) nameEn else nameKn }

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey val id: String,
    val subjectId: String,
    val mediumCode: String,
    val number: Int,
    // Chapter name stays in its original language (Kannada stays Kannada, Hindi stays Hindi)
    val nameOriginal: String,
    // English label for UI labels only
    val nameEn: String,
    var isCompleted: Boolean = false,
    var quizScore: Int = -1,
    var quizAttempts: Int = 0,
    var readProgressPercent: Int = 0, // 0-100 from PDF scroll
    var lastAttemptDate: Long = 0L,
    val pdfFolder: String = ""  // relative folder: e.g. "en/maths" → Chapter-N.pdf
) {
    fun chapterPdfRelPath() = "$pdfFolder/Chapter-$number.pdf"
}

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: String,
    val chapterId: String,
    val subjectId: String,
    val mediumCode: String,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: String,
    val explanation: String = ""
)

@Entity(tableName = "quiz_sessions")
data class QuizSession(
    @PrimaryKey val id: String,
    val chapterId: String,
    val subjectId: String,
    val score: Int,
    val totalQuestions: Int,
    val timeTakenSeconds: Int,
    val date: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_activity")
data class DailyActivity(
    @PrimaryKey val dateKey: String,
    val chaptersStudied: Int = 0,
    val quizzesTaken: Int = 0,
    val goalMet: Boolean = false
)

data class UserAnswer(val question: Question, val selectedOption: String, val isCorrect: Boolean)

data class SubjectStrength(
    val subjectId: String,
    val subjectNameEn: String,
    val masteryPercent: Float,
    val chaptersCompleted: Int,
    val totalChapters: Int,
    val avgQuizScore: Float,
    val isWeak: Boolean
)

data class ActivityItem(val emoji: String, val title: String, val subtitle: String, val timeAgo: String, val colorRes: Int)
