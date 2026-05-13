package com.akshara.deepa.ui.medium

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.databinding.ActivityMediumSelectBinding
import com.akshara.deepa.ui.MainActivity
import com.akshara.deepa.utils.PrefsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediumSelectActivity : AppCompatActivity() {
    private lateinit var b: ActivityMediumSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMediumSelectBinding.inflate(layoutInflater)
        setContentView(b.root)
        animateEntrance()
        b.cvEnglish.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(90).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(70).start()
                selectMedium(PrefsManager.MEDIUM_ENGLISH)
            }.start()
        }
        b.cvKannada.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(90).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).setDuration(70).start()
                selectMedium(PrefsManager.MEDIUM_KANNADA)
            }.start()
        }
    }
    private fun selectMedium(medium: String) {
        val prefs = (application as AksharaApp).prefs
        prefs.selectedMedium = medium; prefs.isSetupDone = true
        lifecycleScope.launch {
            (application as AksharaApp).database.chapterDao()
            delay(300)
            startActivity(Intent(this@MediumSelectActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    private fun animateEntrance() {
        b.ivLogo.alpha = 0f; b.cvEnglish.alpha = 0f; b.cvKannada.alpha = 0f
        b.cvEnglish.translationY = 60f; b.cvKannada.translationY = 60f
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(b.ivLogo,"alpha",0f,1f).apply{duration=600},
                ObjectAnimator.ofFloat(b.ivLogo,"scaleX",0.5f,1f).apply{duration=600},
                ObjectAnimator.ofFloat(b.ivLogo,"scaleY",0.5f,1f).apply{duration=600}
            )
            interpolator = DecelerateInterpolator(); start()
        }
        b.cvEnglish.animate().alpha(1f).translationY(0f).setDuration(380).setStartDelay(650)
            .setInterpolator(DecelerateInterpolator()).start()
        b.cvKannada.animate().alpha(1f).translationY(0f).setDuration(380).setStartDelay(800)
            .setInterpolator(DecelerateInterpolator()).start()
    }
}
