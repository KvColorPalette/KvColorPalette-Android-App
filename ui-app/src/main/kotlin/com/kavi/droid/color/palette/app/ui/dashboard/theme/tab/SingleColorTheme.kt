package com.kavi.droid.color.palette.app.ui.dashboard.theme.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kavi.droid.color.palette.app.ui.common.ThemeColorRow
import com.kavi.droid.color.palette.color.MatPackage

@Composable
fun SingleColorTheme(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThemeColorRow(MatPackage.MatRed.color)
        ThemeColorRow(MatPackage.MatOrange.color)
        ThemeColorRow(MatPackage.MatDGreen.color)
        ThemeColorRow(MatPackage.MatLLBlue.color)

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navController.navigate("theme-gen-detail")
            }
        ) {
            Text("Try it out!")
        }
    }
}