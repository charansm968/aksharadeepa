package com.akshara.deepa.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akshara.deepa.AksharaApp
import com.akshara.deepa.cloud.CloudSyncManager
import com.akshara.deepa.databinding.FragmentSettingsBinding
import com.akshara.deepa.ui.auth.LoginActivity
import com.akshara.deepa.ui.medium.MediumSelectActivity
import com.akshara.deepa.utils.PrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    private var _b: FragmentSettingsBinding? = null
    private val b get() = _b!!
    private lateinit var sync: CloudSyncManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, saved: Bundle?): View {
        _b = FragmentSettingsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sync = CloudSyncManager(requireContext())

        refreshUI()
        setupButtons()
    }

    private fun refreshUI() {
        val app   = requireActivity().application as AksharaApp
        val prefs = app.prefs
        val email = sync.currentUserEmail() ?: "Not logged in"
        val medium = if (prefs.selectedMedium == PrefsManager.MEDIUM_ENGLISH)
            "English Medium" else "Kannada Medium (ಕನ್ನಡ ಮಾಧ್ಯಮ)"

        b.tvUserEmail.text  = email
        b.tvMediumValue.text = medium
    }

    private fun setupButtons() {
        // ── Sync (upload) ───────────────────────────────────────────────────────
        b.btnSync.setOnClickListener {
            if (!sync.isLoggedIn()) { showToast("Please log in first"); return@setOnClickListener }
            showStatus("Syncing…")
            lifecycleScope.launch {
                val result = withContext(Dispatchers.IO) { sync.uploadProgress() }
                hideStatus()
                result.fold(
                    onSuccess = { count -> showToast("✓ Synced $count chapters to cloud") },
                    onFailure = { err  -> showError("Sync Failed", err.message ?: "Unknown error") }
                )
            }
        }

        // ── Restore (download) ──────────────────────────────────────────────────
        b.btnRestore.setOnClickListener {
            if (!sync.isLoggedIn()) { showToast("Please log in first"); return@setOnClickListener }
            AlertDialog.Builder(requireContext())
                .setTitle("Restore from Cloud?")
                .setMessage("This will overwrite your local progress with cloud data.")
                .setPositiveButton("Restore") { _, _ ->
                    showStatus("Restoring…")
                    lifecycleScope.launch {
                        val result = withContext(Dispatchers.IO) { sync.downloadProgress() }
                        hideStatus()
                        result.fold(
                            onSuccess = { count -> showToast("✓ Restored $count chapters from cloud") },
                            onFailure = { err   -> showError("Restore Failed", err.message ?: "Unknown error") }
                        )
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // ── Change Medium ────────────────────────────────────────────────────────
        b.btnChangeMedium.setOnClickListener {
            startActivity(Intent(requireContext(), MediumSelectActivity::class.java))
        }

        // ── Logout ───────────────────────────────────────────────────────────────
        b.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log Out") { _, _ ->
                    sync.signOut()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    // ── Status overlay ──────────────────────────────────────────────────────────

    private fun showStatus(message: String) {
        b.statusOverlay.visibility = View.VISIBLE
        b.tvStatusMessage.text     = message
        b.btnSync.isEnabled        = false
        b.btnRestore.isEnabled     = false
        b.btnLogout.isEnabled      = false
    }

    private fun hideStatus() {
        b.statusOverlay.visibility = View.GONE
        b.btnSync.isEnabled        = true
        b.btnRestore.isEnabled     = true
        b.btnLogout.isEnabled      = true
    }

    private fun showToast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

    private fun showError(title: String, msg: String) =
        AlertDialog.Builder(requireContext()).setTitle(title).setMessage(msg)
            .setPositiveButton("OK", null).show()

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
