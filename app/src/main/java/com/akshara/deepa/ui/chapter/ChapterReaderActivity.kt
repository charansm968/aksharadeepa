package com.akshara.deepa.ui.chapter

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.databinding.ActivityChapterReaderBinding
import kotlinx.coroutines.launch

class ChapterReaderActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ASSET_FOLDER  = "asset_folder"
        const val EXTRA_CHAPTER_NUM   = "chapter_num"
        const val EXTRA_CHAPTER_ID    = "chapter_id"
        const val EXTRA_CHAPTER_NAME  = "chapter_name"
        const val EXTRA_IS_ENGLISH    = "is_english"
        const val EXTRA_READ_PROGRESS = "read_progress"
    }

    private lateinit var b: ActivityChapterReaderBinding
    private val repo by lazy { (application as AksharaApp).repository }

    private lateinit var assetFolder: String
    private lateinit var chapterId: String
    private var chapterNum: Int = 1
    private var currentProgress = 0
    private var lastSavedProgress = 0
    private var completionToastShown = false
    private var pageLoaded = false
    private var userHasScrolled = false
    private var isEnglish = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChapterReaderBinding.inflate(layoutInflater)
        setContentView(b.root)

        assetFolder = intent.getStringExtra(EXTRA_ASSET_FOLDER) ?: ""
        chapterNum = intent.getIntExtra(EXTRA_CHAPTER_NUM, 1)
        chapterId = intent.getStringExtra(EXTRA_CHAPTER_ID) ?: ""
        val name = intent.getStringExtra(EXTRA_CHAPTER_NAME) ?: "Chapter $chapterNum"
        isEnglish = intent.getBooleanExtra(EXTRA_IS_ENGLISH, true)
        lastSavedProgress = intent.getIntExtra(EXTRA_READ_PROGRESS, 0)
        currentProgress = lastSavedProgress

        b.tvChapterTitle.text = name
        b.tvPageInfo.text = "Chapter $chapterNum"
        b.btnBack.setOnClickListener { saveProgressAndFinish() }
        updateProgressBar(lastSavedProgress)

        if (assetFolder.isNotEmpty()) {
            loadSyllabus()
        } else {
            showNoContent()
        }
    }

    private fun loadSyllabus() {
        val assetPath = "file:///android_asset/syllabus_html/$assetFolder/Chapter-$chapterNum.html"
        b.progressBar.visibility = View.VISIBLE

        try {
            b.syllabusWebView.visibility = View.VISIBLE
            b.noContentLayout.visibility = View.GONE
            b.tvPageInfo.text = "Chapter $chapterNum  •  Syllabus"
            b.tvReadStatus.text = if (isEnglish) {
                "Scroll to the end to mark this chapter complete"
            } else {
                "ಅಧ್ಯಾಯ ಪೂರ್ಣಗೊಳಿಸಲು ಕೊನೆಯವರೆಗೆ ಸ್ಕ್ರಾಲ್ ಮಾಡಿ"
            }
            bindScrollProgress()
            b.syllabusWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    pageLoaded = true
                    b.progressBar.visibility = View.GONE
                }
            }
            b.syllabusWebView.settings.javaScriptEnabled = false
            b.syllabusWebView.settings.builtInZoomControls = false
            b.syllabusWebView.settings.displayZoomControls = false
            b.syllabusWebView.loadUrl(assetPath)
        } catch (_: Exception) {
            b.progressBar.visibility = View.GONE
            showNoContent()
        }
    }

    private fun bindScrollProgress() {
        b.syllabusWebView.setOnScrollChangeListener { _, _, scrollY, oldScrollY, _ ->
            if (scrollY > 0 || scrollY != oldScrollY) {
                userHasScrolled = true
            }
            updateProgressFromScroll()
        }
    }

    private fun updateProgressFromScroll() {
        if (!pageLoaded || !userHasScrolled) return

        val contentHeight = (b.syllabusWebView.contentHeight * b.syllabusWebView.scale).toInt()
        val maxScroll = contentHeight - b.syllabusWebView.height
        if (contentHeight <= 0 || b.syllabusWebView.height <= 0 || maxScroll <= 0) return

        val nearBottomThreshold = 12
        val progress = if (b.syllabusWebView.scrollY >= maxScroll - nearBottomThreshold) {
            100
        } else {
            ((b.syllabusWebView.scrollY * 100) / maxScroll).coerceIn(0, 100)
        }

        if (progress < currentProgress) return
        currentProgress = progress
        updateProgressBar(currentProgress)

        if (currentProgress - lastSavedProgress >= 10 || currentProgress == 100) {
            saveProgress(currentProgress)
            lastSavedProgress = currentProgress
        }

        if (currentProgress == 100 && !completionToastShown) {
            completionToastShown = true
            showCompleteMessage()
        }
    }

    private fun updateProgressBar(progress: Int) {
        b.pbReading.progress = progress
        b.tvProgressPct.text = "$progress%"
    }

    private fun saveProgress(progress: Int) {
        if (chapterId.isEmpty()) return
        lifecycleScope.launch { repo.updateReadProgress(chapterId, progress) }
    }

    private fun saveProgressAndFinish() {
        if (chapterId.isNotEmpty() && currentProgress > lastSavedProgress) {
            saveProgress(currentProgress)
        }
        finish()
    }

    private fun showCompleteMessage() {
        if (isEnglish) {
            Toast.makeText(this, "Chapter Complete! Quiz is now unlocked.", Toast.LENGTH_LONG).show()
            b.tvReadStatus.text = "Chapter Complete! Quiz Unlocked."
        } else {
            Toast.makeText(this, "ಅಧ್ಯಾಯ ಪೂರ್ಣ! ಕ್ವಿಜ್ ಈಗ ತೆರೆಯಲ್ಪಟ್ಟಿದೆ.", Toast.LENGTH_LONG).show()
            b.tvReadStatus.text = "ಅಧ್ಯಾಯ ಪೂರ್ಣ!"
        }
    }

    private fun showNoContent() {
        b.syllabusWebView.visibility = View.GONE
        b.noContentLayout.visibility = View.VISIBLE
        if (isEnglish) {
            b.tvNoContent.text = "Syllabus text is not bundled for this chapter yet.\n\nYou can still take quizzes after marking the chapter read."
            b.btnMarkRead.text = "Mark Chapter as Read"
        } else {
            b.tvNoContent.text = "ಈ ಅಧ್ಯಾಯಕ್ಕೆ ಪಠ್ಯ ಲಭ್ಯವಿಲ್ಲ.\n\nಅಧ್ಯಾಯ ಓದಿದೆ ಎಂದು ಗುರುತಿಸಿ ಕ್ವಿಜ್ ಮುಂದುವರಿಸಬಹುದು."
            b.btnMarkRead.text = "ಅಧ್ಯಾಯ ಓದಿದೆ ಎಂದು ಗುರುತಿಸಿ"
        }
        b.btnMarkRead.setOnClickListener {
            saveProgress(100)
            currentProgress = 100
            lastSavedProgress = 100
            updateProgressBar(100)
            showCompleteMessage()
        }
    }

    override fun onBackPressed() {
        saveProgressAndFinish()
    }
}
