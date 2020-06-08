package com.dhimasdewanto.githubstars.view.main.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.dhimasdewanto.githubstars.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickPreference()
    }

    private fun onClickPreference() {
        val preference = findPreference<Preference>("LIST_PREFERENCE_THEMES")
        preference?.setOnPreferenceChangeListener { preference, newValue ->
            val value = newValue as String
            val themeMode = getThemeSettings(value)
            AppCompatDelegate.setDefaultNightMode(themeMode)

            true
        }
    }

    private fun getThemeSettings(theme: String): Int {
        return when (theme) {
            "LIGHT" -> AppCompatDelegate.MODE_NIGHT_NO
            "DARK" -> AppCompatDelegate.MODE_NIGHT_YES
            "DEFAULT" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
    }
}