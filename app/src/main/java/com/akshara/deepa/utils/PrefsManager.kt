package com.akshara.deepa.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("akshara_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_MEDIUM       = "selected_medium"
        const val KEY_SETUP_DONE   = "setup_done"
        const val MEDIUM_ENGLISH   = "en"
        const val MEDIUM_KANNADA   = "kn"
    }

    var selectedMedium: String
        get() = prefs.getString(KEY_MEDIUM, "") ?: ""
        set(v) = prefs.edit().putString(KEY_MEDIUM, v).apply()

    var isSetupDone: Boolean
        get() = prefs.getBoolean(KEY_SETUP_DONE, false)
        set(v) = prefs.edit().putBoolean(KEY_SETUP_DONE, v).apply()

    val isEnglish: Boolean get() = selectedMedium == MEDIUM_ENGLISH
    val mediumCode: String get() = selectedMedium.ifEmpty { MEDIUM_ENGLISH }
}
