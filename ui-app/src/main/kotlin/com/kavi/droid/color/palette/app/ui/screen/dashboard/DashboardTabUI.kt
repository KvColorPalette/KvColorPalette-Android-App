package com.kavi.droid.color.palette.app.ui.screen.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kavi.droid.color.palette.app.R
import com.kavi.droid.color.palette.app.ui.model.TabItem
import com.kavi.droid.color.palette.app.ui.theme.KvColorPaletteTheme
import com.kavi.droid.color.palette.app.ui.theme.navigationBarColors
import com.kavi.droid.color.palette.app.ui.screen.dashboard.palette.ColorPaletteTab
import com.kavi.droid.color.palette.app.ui.screen.dashboard.settings.SettingsTab
import com.kavi.droid.color.palette.app.ui.screen.dashboard.theme.ThemeColorGenTab

@Composable
fun DashboardTabUI(navController: NavHostController, colorScheme: MutableState<ColorScheme?>) {
    val tabItems = listOf(
        TabItem(name = "Color Palettes", icon = R.drawable.icon_color_grid),
        TabItem(name = "Theme Gen", icon = R.drawable.icon_theme_masks),
        TabItem(name = "Settings", icon = R.drawable.icon_settings_gear)
    )
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    NavigationBarItem(
                        modifier = Modifier
                            .padding(4.dp),
                        colors = navigationBarColors(),
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        label = { Text(tabItem.name) },
                        icon = {
                            Icon(
                                painterResource(id = tabItem.icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                                    .padding(8.dp),
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Content displayed above the bottom bar
        TabContent(
            navController = navController,
            colorScheme = colorScheme,
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize()
        )
    }
}

@Composable
fun TabContent(navController: NavHostController, colorScheme: MutableState<ColorScheme?>, selectedTabIndex: Int, modifier: Modifier = Modifier) {
    when (selectedTabIndex) {
        0 -> ColorPaletteTab(navController = navController, modifier = modifier)
        1 -> ThemeColorGenTab(navController = navController, modifier =  modifier)
        2 -> SettingsTab(modifier = modifier, colorScheme = colorScheme)
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardTabUIPreview() {
    KvColorPaletteTheme {
        DashboardTabUI(rememberNavController(), remember { mutableStateOf(null) })
    }
}