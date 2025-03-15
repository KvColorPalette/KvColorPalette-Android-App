package com.kavi.droid.color.palette.app.ui.dashboard.palette.pager

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.model.KvColor
import com.kavi.droid.color.palette.app.ui.common.ColorBox
import com.kavi.droid.color.palette.app.ui.common.ColorDetailRow

@Composable
fun SaturationPalettePager() {

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
                text = "Saturation",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        ) {
            SaturationPaletteColorRow(givenColor = MatPackage.MatRed, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatRose, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLPurple, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatDPurple, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatDBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLLBlue, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLCyan, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatDCyan, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatDGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatLLGreen, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatYellow, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatGold, selectedColor = selectedColor) { color -> selectedColor = color }
            SaturationPaletteColorRow(givenColor = MatPackage.MatOrange, selectedColor = selectedColor) { color -> selectedColor = color }
        }

        ColorDetailRow(selectedColorList = selectedColorList)
    }
}

@Composable
fun SaturationPaletteColorRow(givenColor: KvColor, selectedColor: Color, onSelect: (color: Color) -> Unit) {
    val colors = KvColorPalette.instance.generateSaturationColorPalette(givenColor = givenColor.color)
    Row {
        colors.forEach {
            ColorBox(givenColor = it, selectedColor = selectedColor, onSelect = onSelect)
        }
    }
}

@Preview
@Composable
fun SaturationPalettePagerPreview() {
    SaturationPalettePager()
}