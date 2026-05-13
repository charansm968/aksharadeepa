package com.akshara.deepa

import android.app.Application
import com.akshara.deepa.data.db.AksharaDatabase
import com.akshara.deepa.data.repository.AksharaRepository
import com.akshara.deepa.utils.PrefsManager

class AksharaApp : Application() {
    val database   by lazy { AksharaDatabase.getInstance(this) }
    val prefs      by lazy { PrefsManager(this) }
    val repository by lazy {
        AksharaRepository(
            database.chapterDao(), database.questionDao(),
            database.quizSessionDao(), database.dailyActivityDao()
        )
    }
}
