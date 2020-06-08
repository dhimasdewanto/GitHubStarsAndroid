package com.dhimasdewanto.githubstars.core.themes

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemeProviderImpl(context: Context) : ThemeProvider(context) {
    override fun initTheme() {
        val themeMode = getAppCompatDelegateTheme(getTheme())
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    override fun setTheme(theme: Themes) {
        val themeMode = getAppCompatDelegateTheme(theme)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    override fun getTheme(): Themes {
        val selectedTheme = preferences.getString(LIST_PREFERENCE_THEMES, Themes.LIGHT.toString())
        return Themes.valueOf(selectedTheme ?: Themes.LIGHT.toString())
    }

    private fun getAppCompatDelegateTheme(theme: Themes): Int {
        if (theme == Themes.LIGHT) return AppCompatDelegate.MODE_NIGHT_NO
        if (theme == Themes.DARK) return AppCompatDelegate.MODE_NIGHT_YES
        if (theme == Themes.DEFAULT) return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        return AppCompatDelegate.MODE_NIGHT_NO
    }
}