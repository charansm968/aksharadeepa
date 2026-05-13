package com.akshara.deepa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.R
import com.akshara.deepa.cloud.CloudSyncManager
import com.akshara.deepa.databinding.ActivityLoginBinding
import com.akshara.deepa.ui.medium.MediumSelectActivity
import com.akshara.deepa.ui.MainActivity
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding
    private lateinit var sync: CloudSyncManager
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        sync = CloudSyncManager(this)

        // If already logged in, skip to main
        if (sync.isLoggedIn()) {
            goToMain()
            return
        }

        setupInputListeners()
        setupButtons()
    }

    private fun setupInputListeners() {
        b.etEmail.doOnTextChanged    { _, _, _, _ -> b.tilEmail.error    = null }
        b.etPassword.doOnTextChanged { _, _, _, _ -> b.tilPassword.error = null }
    }

    private fun setupButtons() {
        b.btnLogin.setOnClickListener { attemptLogin() }
        b.btnCreateAccount.setOnClickListener { attemptRegister() }
        b.tvForgotPassword.setOnClickListener { showForgotPassword() }
    }

    private fun attemptLogin() {
        val email = b.etEmail.text.toString().trim()
        val pass  = b.etPassword.text.toString()

        if (!validateInputs(email, pass)) return

        showStatus("Logging in…")

        scope.launch {
            val result = withContext(Dispatchers.IO) { sync.signIn(email, pass) }
            hideStatus()
            result.fold(
                onSuccess = {
                    showToast("Welcome back!")
                    goToMain()
                },
                onFailure = { err ->
                    showErrorDialog("Login Failed", err.message ?: "Invalid email or password.")
                }
            )
        }
    }

    private fun attemptRegister() {
        val email = b.etEmail.text.toString().trim()
        val pass  = b.etPassword.text.toString()

        if (!validateInputs(email, pass)) return
        if (pass.length < 6) {
            b.tilPassword.error = "Password must be at least 6 characters"
            return
        }

        showStatus("Creating account…")

        scope.launch {
            val result = withContext(Dispatchers.IO) { sync.createAccount(email, pass) }
            hideStatus()
            result.fold(
                onSuccess = {
                    showToast("Account created!")
                    val prefs = (application as AksharaApp).prefs
                    if (!prefs.isSetupDone || prefs.selectedMedium.isEmpty()) {
                        startActivity(Intent(this@LoginActivity, MediumSelectActivity::class.java))
                    } else {
                        goToMain()
                    }
                    finish()
                },
                onFailure = { err ->
                    showErrorDialog("Registration Failed", err.message ?: "Could not create account.")
                }
            )
        }
    }

    private fun showForgotPassword() {
        val email = b.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            b.tilEmail.error = "Enter your email first"
            return
        }
        showStatus("Sending reset email…")
        scope.launch {
            val result = withContext(Dispatchers.IO) { sync.sendPasswordReset(email) }
            hideStatus()
            result.fold(
                onSuccess  = { showToast("Reset email sent. Check your inbox.") },
                onFailure  = { err -> showErrorDialog("Error", err.message ?: "Could not send reset email.") }
            )
        }
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        var valid = true
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            b.tilEmail.error = "Enter a valid email address"; valid = false
        }
        if (pass.isEmpty()) {
            b.tilPassword.error = "Password cannot be empty"; valid = false
        }
        return valid
    }

    // ── Status overlay ─────────────────────────────────────────────────────────

    private fun showStatus(message: String) {
        b.statusOverlay.visibility  = View.VISIBLE
        b.tvStatusMessage.text      = message
        b.btnLogin.isEnabled        = false
        b.btnCreateAccount.isEnabled = false
    }

    private fun hideStatus() {
        b.statusOverlay.visibility  = View.GONE
        b.btnLogin.isEnabled        = true
        b.btnCreateAccount.isEnabled = true
    }

    // ── Navigation helpers ──────────────────────────────────────────────────────

    private fun goToMain() {
        val prefs = (application as AksharaApp).prefs
        val dest  = if (!prefs.isSetupDone || prefs.selectedMedium.isEmpty())
            MediumSelectActivity::class.java else MainActivity::class.java
        startActivity(Intent(this, dest))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun showErrorDialog(title: String, msg: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
