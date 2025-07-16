package com.kavi.droid.color.palette.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.repository.SettingsLocalRepositoryImpl
import com.kavi.droid.color.palette.app.ui.theme.KvColorPaletteTheme
import com.kavi.droid.color.palette.app.ui.navigation.NavigationHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHost: NavigationHost
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme: Boolean = isSystemInDarkTheme()
            // Create a state variable for color-scheme
            val appColorScheme: MutableState<ColorScheme?> = remember { mutableStateOf(null) }

            // Set the color-scheme according to generate theme from `KvColorPalette`
            appColorScheme.value = when {
                isDarkTheme -> KvColorPalette.colorSchemeThemePalette.darkColorScheme
                else -> KvColorPalette.colorSchemeThemePalette.lightColorScheme
            }

            // If you want to ignore the color theme generation and use the dynamic color according to the device colors, then make this true.
            KvColorPaletteTheme (isDynamicColor = false, appColorScheme = appColorScheme) {
                navController = rememberNavController()
                // Create the navigation host with start-up destination as 'dashboard tab ui'
                navigationHost.NavGraph(navController = navController, colorScheme = appColorScheme)
            }
        }
    }
}