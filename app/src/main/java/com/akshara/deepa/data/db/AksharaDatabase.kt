package com.akshara.deepa.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.akshara.deepa.data.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Chapter::class, Question::class, QuizSession::class, DailyActivity::class],
    version  = 1, exportSchema = false
)
abstract class AksharaDatabase : RoomDatabase() {
    abstract fun chapterDao():       ChapterDao
    abstract fun questionDao():      QuestionDao
    abstract fun quizSessionDao():   QuizSessionDao
    abstract fun dailyActivityDao(): DailyActivityDao

    companion object {
        @Volatile private var INSTANCE: AksharaDatabase? = null

        fun getInstance(ctx: Context): AksharaDatabase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: build(ctx).also { INSTANCE = it } }

        private fun build(ctx: Context): AksharaDatabase {
            var db: AksharaDatabase? = null
            db = Room.databaseBuilder(ctx.applicationContext, AksharaDatabase::class.java, "akshara_v5_db")
                .addCallback(object : Callback() {
                    override fun onCreate(s: SupportSQLiteDatabase) {
                        super.onCreate(s)
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.let { SeedData.seedAll(it.chapterDao(), it.questionDao()) }
                        }
                    }
                }).build()
            return db
        }
    }
}
