package com.kavi.droid.color.palette.app.ui.screen.dashboard.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kavi.droid.color.palette.app.ui.screen.dashboard.theme.tab.MultiColorTheme
import com.kavi.droid.color.palette.app.ui.screen.dashboard.theme.tab.SingleColorTheme

@Composable
fun ThemeColorGenTab(navController: NavHostController, modifier: Modifier) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 8.dp),
                text = "Theme Color Palettes",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                modifier = Modifier.padding(8.dp)
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Single Color", color = MaterialTheme.colorScheme.tertiary) },
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Multi Color", color = MaterialTheme.colorScheme.tertiary) }
                )
            }

            when (selectedTabIndex) {
                0 -> SingleColorTheme(navController)
                1 -> MultiColorTheme(navController)
            }
        }
    }
}

@Preview
@Composable
fun ThemeColorGenPreview() {
    ThemeColorGenTab(navController = rememberNavController(), Modifier)
}