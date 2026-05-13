package com.akshara.deepa.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.databinding.ActivitySplashBinding
import com.akshara.deepa.ui.medium.MediumSelectActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.akshara.deepa.cloud.CloudSyncManager
import com.akshara.deepa.ui.auth.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var b: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val syncManager = CloudSyncManager(this)

        if (!syncManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)
        animateLogo()
        lifecycleScope.launch {
            (application as AksharaApp).database.chapterDao() // warm up
            delay(2200)
            val prefs = (application as AksharaApp).prefs
            val dest  = if (!prefs.isSetupDone || prefs.selectedMedium.isEmpty())
                MediumSelectActivity::class.java else MainActivity::class.java
            startActivity(Intent(this@SplashActivity, dest))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
    private fun animateLogo() {
        b.ivLogo.alpha = 0f; b.tvAppName.alpha = 0f; b.tvTagline.alpha = 0f
        val set = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(b.ivLogo,"scaleX",0.4f,1.1f,1f).apply{duration=700},
            ObjectAnimator.ofFloat(b.ivLogo,"scaleY",0.4f,1.1f,1f).apply{duration=700},
            ObjectAnimator.ofFloat(b.ivLogo,"alpha",0f,1f).apply{duration=500}
        )
        set.interpolator = DecelerateInterpolator(); set.start()
        b.tvAppName.translationY = 40f; b.tvTagline.translationY = 40f
        b.tvAppName.animate().alpha(1f).translationY(0f).setDuration(500).setStartDelay(600)
            .setInterpolator(DecelerateInterpolator()).start()
        b.tvTagline.animate().alpha(1f).translationY(0f).setDuration(500).setStartDelay(850)
            .setInterpolator(DecelerateInterpolator()).start()
    }
}
