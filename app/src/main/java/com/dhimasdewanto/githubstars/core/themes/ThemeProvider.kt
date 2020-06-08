package com.dhimasdewanto.githubstars.core.themes

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class ThemeProvider(context: Context) {
    private val appContext: Context = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    abstract fun getTheme(): String

    abstract fun setThemeByPreference()

    abstract fun setOnClickPreference()

//    abstract fun onThemeChangeListener()
}