package com.kavi.droid.color.palette.app.ui.navigation

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kavi.droid.color.palette.app.ui.model.PaletteType
import com.kavi.droid.color.palette.app.ui.screen.detail.PaletteGenDetailUI
import com.kavi.droid.color.palette.app.ui.screen.detail.ThemeGenDetailUI
import com.kavi.droid.color.palette.app.ui.screen.dashboard.DashboardTabUI
import javax.inject.Inject

class NavigationHost @Inject constructor() {
    @Composable
    fun NavGraph(navController: NavHostController, colorScheme: MutableState<ColorScheme?>) {
        NavHost(navController = navController, startDestination = "dashboard-tab") {
            composable(route = "dashboard-tab") {
                DashboardTabUI(navController = navController, colorScheme = colorScheme)
            }
            composable(route = "palette-gen-detail/{paletteType}") { navBackStack ->
                val paletteType = navBackStack.arguments?.getString("paletteType")
                paletteType?.let {
                    when (it) {
                        "0" -> PaletteGenDetailUI(paletteType = PaletteType.KV_PALETTE)
                        "1" -> PaletteGenDetailUI(paletteType = PaletteType.ALPHA_PALETTE)
                        "2" -> PaletteGenDetailUI(paletteType = PaletteType.LIGHTNESS_PALETTE)
                        "3" -> PaletteGenDetailUI(paletteType = PaletteType.SATURATION_PALETTE)
                    }
                }?: run {
                    PaletteGenDetailUI(paletteType = PaletteType.KV_PALETTE)
                }
            }
            composable(route = "theme-gen-detail/{themeGenType}") { navBackStack ->
                val themeGenType = navBackStack.arguments?.getString("themeGenType")
                themeGenType?.let {
                    when(it) {
                        "mono-color" -> ThemeGenDetailUI(isMultiColorThemeGen = false)
                        "multi-color" -> ThemeGenDetailUI(isMultiColorThemeGen = true)
                    }
                }
            }
        }
    }
}