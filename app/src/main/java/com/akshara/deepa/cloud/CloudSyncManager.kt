package com.akshara.deepa.cloud

import android.content.Context
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.data.db.AksharaDatabase
import com.akshara.deepa.data.models.Chapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first

/**
 * Manages Firebase Auth (email/password) and Firestore-based progress sync.
 *
 * Firestore structure:
 *   users/{uid}/chapters/{chapterId}  →  {isCompleted, quizScore, quizAttempts, readProgress, lastAttemptDate}
 *   users/{uid}/settings              →  {selectedMedium}
 */
class CloudSyncManager(private val context: Context) {

    private val auth: FirebaseAuth         = FirebaseAuth.getInstance()
    private val db:   FirebaseFirestore    = FirebaseFirestore.getInstance()

    // ── Auth ───────────────────────────────────────────────────────────────────

    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun currentUserEmail(): String? = auth.currentUser?.email

    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    suspend fun createAccount(email: String, password: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    suspend fun sendPasswordReset(email: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            auth.sendPasswordResetEmail(email).await()
        }
    }

    fun signOut() = auth.signOut()

    // ── Sync ───────────────────────────────────────────────────────────────────

    /**
     * Upload local Room DB progress to Firestore.
     * Returns number of chapters uploaded.
     */
    suspend fun uploadProgress(): Result<Int> = runCatching {

        val uid = auth.currentUser?.uid ?: error("Not logged in")

        val roomDb = AksharaDatabase.getInstance(context)

        val medium = (context.applicationContext as AksharaApp)
            .prefs.selectedMedium

        val chapters = roomDb.chapterDao()
            .getAllChapters(medium)
            .first()

        val batch = db.batch()

        var count = 0

        chapters.forEach { ch ->

            val ref = db.collection("users")
                .document(uid)
                .collection("chapters")
                .document(ch.id)

            val data = mapOf(
                "isCompleted" to ch.isCompleted,
                "quizScore" to ch.quizScore,
                "quizAttempts" to ch.quizAttempts,
                "readProgressPercent" to ch.readProgressPercent,
                "lastAttemptDate" to ch.lastAttemptDate
            )

            batch.set(ref, data, SetOptions.merge())

            count++
        }

        val settingsRef = db.collection("users")
            .document(uid)
            .collection("settings")
            .document("prefs")

        batch.set(
            settingsRef,
            mapOf("selectedMedium" to medium),
            SetOptions.merge()
        )

        batch.commit().await()

        count
    }

    /**
     * Download Firestore progress into local Room DB.
     * Returns number of chapters restored.
     */
    suspend fun downloadProgress(): Result<Int> = runCatching {
        val uid = auth.currentUser?.uid ?: error("Not logged in")
        val roomDb = AksharaDatabase.getInstance(context)

        val snapshot = withContext(Dispatchers.IO) {
            db.collection("users").document(uid).collection("chapters").get().await()
        }

        var count = 0
        withContext(Dispatchers.IO) {
            snapshot.documents.forEach { doc ->

                val chapterId = doc.id

                val ch = roomDb.chapterDao().getById(chapterId)
                    ?: return@forEach

                roomDb.chapterDao().setCompleted(
                    chapterId,
                    doc.getBoolean("isCompleted") ?: ch.isCompleted
                )

                roomDb.chapterDao().setReadProgress(
                    chapterId,
                    (doc.getLong("readProgressPercent")?.toInt())
                        ?: ch.readProgressPercent
                )

                roomDb.chapterDao().updateQuizScore(
                    chapterId,
                    (doc.getLong("quizScore")?.toInt())
                        ?: ch.quizScore,
                    doc.getLong("lastAttemptDate")
                        ?: ch.lastAttemptDate
                )

                count++
            }
        }
        count
    }
}
