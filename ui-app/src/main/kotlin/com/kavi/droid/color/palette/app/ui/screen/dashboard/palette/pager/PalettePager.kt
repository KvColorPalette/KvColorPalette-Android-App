package com.kavi.droid.color.palette.app.ui.screen.dashboard.palette.pager

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.model.KvColor
import com.kavi.droid.color.palette.app.ui.screen.common.ColorBox
import com.kavi.droid.color.palette.app.ui.screen.common.ColorDetailRow

@Composable
fun PalettePager() {

    var selectedColor by remember { mutableStateOf(Color.White) }
    val selectedColorList = remember { mutableStateListOf<Color>() }

    LaunchedEffect(selectedColor) {
        selectedColorList.add(0, selectedColor)
    }

    Column {
        Row (Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp)
                    .padding(top = 20.dp),
                text = "Color Palette",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaletteColorRow(givenColor = MatPackage.MatRed, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatRose, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLPurple, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatDPurple, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatDBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLLBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLCyan, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatDCyan, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatDGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatLLGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatYellow, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatGold, selectedColor = selectedColor) { color -> selectedColor = color }
            PaletteColorRow(givenColor = MatPackage.MatOrange, selectedColor = selectedColor) { color -> selectedColor = color }
        }

        ColorDetailRow(selectedColorList = selectedColorList)
    }
}

@Composable
fun PaletteColorRow(givenColor: KvColor, selectedColor: Color, onSelect: (color: Color) -> Unit) {
    val colors = KvColorPalette.instance.generateColorPalette(givenColor = givenColor)
    BoxWithConstraints {
        val screenWidth = this.maxWidth
        Row {
            colors.forEach {
                ColorBox(boxSize = screenWidth * .09f, givenColor = it, selectedColor = selectedColor, onSelect = onSelect)
            }
        }
    }
}