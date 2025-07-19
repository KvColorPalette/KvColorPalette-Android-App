package com.kavi.droid.color.palette.app.ui.screen.dashboard.blend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun ColorBlendTab(navController: NavHostController, modifier: Modifier) {

    Column (
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Row (Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 8.dp),
                text = "Color Blend",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
}

@Preview
@Composable
fun ColorBlendTabPreview() {
    ColorBlendTab(navController = rememberNavController(), modifier = Modifier)
}