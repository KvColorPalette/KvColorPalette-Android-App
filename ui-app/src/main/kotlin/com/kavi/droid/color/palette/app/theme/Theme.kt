package com.kavi.droid.color.palette.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.KvColorPalette

@Composable
fun KvColorPaletteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val theme = KvColorPalette.appThemePalette

    val appColorScheme = when {
        darkTheme -> KvColorPalette.colorSchemeThemePalette.darkColorScheme
        else -> KvColorPalette.colorSchemeThemePalette.lightColorScheme
    }

    MaterialTheme(
        colorScheme = appColorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun navigationBarColors(): NavigationBarItemColors {
    return NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = Color.Black,
        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
        selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        disabledIconColor = Color.Gray,
        disabledTextColor = Color.Gray,
    )
}