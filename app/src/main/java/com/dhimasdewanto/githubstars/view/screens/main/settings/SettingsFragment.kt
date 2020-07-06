package com.dhimasdewanto.githubstars.view.screens.main.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dhimasdewanto.githubstars.R
import com.dhimasdewanto.githubstars.core.themes.LIST_PREFERENCE_THEMES
import com.dhimasdewanto.githubstars.core.themes.ThemeProvider
import com.dhimasdewanto.githubstars.core.themes.Themes
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SettingsFragment : PreferenceFragmentCompat(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val themeProvider by instance<ThemeProvider>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickPreference()
    }

    private fun onClickPreference() {
        val preference = findPreference<Preference>(LIST_PREFERENCE_THEMES)
        preference?.setOnPreferenceChangeListener { _, themeValue ->
            val themeString = themeValue as String
            val theme = Themes.valueOf(themeString)
            themeProvider.setTheme(theme)

            true
        }
    }
}