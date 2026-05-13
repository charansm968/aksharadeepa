package com.akshara.deepa.data.db

import androidx.room.*
import com.akshara.deepa.data.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters WHERE mediumCode=:m ORDER BY subjectId,number")
    fun getAllChapters(m: String): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE subjectId=:sid AND mediumCode=:m ORDER BY number")
    fun getBySubject(sid: String, m: String): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE id=:id")
    suspend fun getById(id: String): Chapter?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<Chapter>)

    @Query("UPDATE chapters SET isCompleted=:c WHERE id=:id")
    suspend fun setCompleted(id: String, c: Boolean)

    @Query("UPDATE chapters SET readProgressPercent=:p WHERE id=:id")
    suspend fun setReadProgress(id: String, p: Int)

    @Query("UPDATE chapters SET quizScore=:s,quizAttempts=quizAttempts+1,lastAttemptDate=:d WHERE id=:id")
    suspend fun updateQuizScore(id: String, s: Int, d: Long)

    @Query("SELECT COUNT(*) FROM chapters WHERE isCompleted=1 AND mediumCode=:m")
    fun getCompletedCount(m: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId=:sid AND mediumCode=:m AND isCompleted=1")
    suspend fun getCompletedForSubject(sid: String, m: String): Int

    @Query("SELECT COUNT(*) FROM chapters WHERE subjectId=:sid AND mediumCode=:m")
    suspend fun getTotalForSubject(sid: String, m: String): Int

    @Query("SELECT AVG(quizScore) FROM chapters WHERE subjectId=:sid AND mediumCode=:m AND quizScore>=0")
    suspend fun getAvgQuiz(sid: String, m: String): Float?

    @Query("SELECT AVG(readProgressPercent) FROM chapters WHERE subjectId=:sid AND mediumCode=:m")
    suspend fun getAvgReadProgress(sid: String, m: String): Float?
}

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE chapterId=:cid AND mediumCode=:m ORDER BY RANDOM() LIMIT :n")
    suspend fun getForChapter(cid: String, m: String, n: Int = 10): List<Question>

    @Query("SELECT * FROM questions WHERE id=:id")
    suspend fun getById(id: String): Question?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(q: List<Question>)

    @Query("SELECT COUNT(*) FROM questions WHERE chapterId=:cid")
    suspend fun countForChapter(cid: String): Int
}

@Dao
interface QuizSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(s: QuizSession)

    @Query("SELECT COUNT(*) FROM quiz_sessions")
    fun getTotalCount(): Flow<Int>
}

@Dao
interface DailyActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(a: DailyActivity)

    @Query("SELECT * FROM daily_activity WHERE dateKey=:k")
    suspend fun getForDate(k: String): DailyActivity?

    @Query("SELECT * FROM daily_activity ORDER BY dateKey DESC LIMIT 7")
    suspend fun getLast7(): List<DailyActivity>

    @Query("UPDATE daily_activity SET chaptersStudied=chaptersStudied+1,goalMet=1 WHERE dateKey=:k")
    suspend fun incChapters(k: String)

    @Query("UPDATE daily_activity SET quizzesTaken=quizzesTaken+1 WHERE dateKey=:k")
    suspend fun incQuizzes(k: String)
}
