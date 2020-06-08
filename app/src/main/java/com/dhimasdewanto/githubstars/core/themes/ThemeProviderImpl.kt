package com.dhimasdewanto.githubstars.core.themes

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemeProviderImpl(context: Context) : ThemeProvider(context) {
    override fun getTheme(): String {
        val selectedTheme = preferences.getString("LIST_PREFERENCE_THEMES", "Default")
        return selectedTheme ?: "DEFAULT"
    }

    override fun setThemeByPreference() {
        val themeMode = getThemeSettings(getTheme())
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    override fun setOnClickPreference() {
//        val preference = preferences
    }

    private fun getThemeSettings(theme: String): Int {
        return when (theme) {
            "LIGHT" -> AppCompatDelegate.MODE_NIGHT_NO
            "DARK" -> AppCompatDelegate.MODE_NIGHT_YES
            "DEFAULT" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
    }

//    fun onThemeChangeListener() {
//        SharedPreferences.OnSharedPreferenceChangeListener { preference: SharedPreferences, key: String ->
//
//            if (key == "LIST_PREFERENCE_THEMES") {
//
//                when(preference.getString("LIST_PREFERENCE_THEMES", "Default")) {
//                    "LIGHT" -> { setTheme(AppCompatDelegate.MODE_NIGHT_NO) }
//                    "DARK" -> { setTheme(AppCompatDelegate.MODE_NIGHT_YES) }
//                    "DEFAULT" -> { setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
//                }
//
//            }
//
//        }
//    }
//
//    private fun setTheme(themeMode: Int) {
//        AppCompatDelegate.setDefaultNightMode(themeMode)
//    }
}