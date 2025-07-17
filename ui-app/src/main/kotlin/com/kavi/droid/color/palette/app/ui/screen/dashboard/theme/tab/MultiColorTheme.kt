package com.kavi.droid.color.palette.app.ui.screen.dashboard.theme.tab

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kavi.droid.color.palette.app.ui.screen.common.ThemeColorRow
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.palette.util.ColorUtil

@Composable
fun MultiColorTheme(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThemeColorRow(MatPackage.MatRed.color, MatPackage.MatDBlue.color, .8f, ThemeGenMode.BLEND)
        ThemeColorRow(MatPackage.MatRed.color, MatPackage.MatDBlue.color, .8f, ThemeGenMode.SEQUENCE)
        ThemeColorRow(MatPackage.MatOrange.color, ColorUtil.getColorFromHex("#800000"), bias = .5f, themeGenMode = ThemeGenMode.BLEND)
        ThemeColorRow(MatPackage.MatOrange.color, ColorUtil.getColorFromHex("#800000"), bias = .5f, themeGenMode = ThemeGenMode.SEQUENCE)
        ThemeColorRow(MatPackage.MatDGreen.color, MatPackage.MatDCyan.color)
        ThemeColorRow(MatPackage.MatLLBlue.color, MatPackage.MatGold.color)

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navController.navigate("theme-gen-detail/multi-color")
            }
        ) {
            Text("Try it out!")
        }
    }
}

@Preview
@Composable
fun ThemeColorGenPreview() {
    MultiColorTheme(navController = rememberNavController())
}