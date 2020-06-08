package com.dhimasdewanto.githubstars.core.themes

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class ThemeProvider(context: Context) {
    private val appContext: Context = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    abstract fun initTheme()

    abstract fun getTheme(): Themes

    abstract fun setTheme(theme: Themes)
}