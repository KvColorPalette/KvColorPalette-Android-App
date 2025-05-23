package com.kavi.droid.color.palette.app.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.theme.KvColorPaletteTheme
import com.kavi.droid.color.palette.app.ui.common.SelectedColorUI
import com.kavi.droid.color.palette.app.ui.common.ThemeColorItem
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.droid.color.palette.model.ColorSchemeThemePalette
import com.kavi.droid.color.palette.util.ColorUtil
import com.kavi.droid.color.picker.ui.KvColorPickerBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeGenDetailUI() {
    val selectedColor = remember { mutableStateOf(Color.White) }
    val colorHex = remember { mutableStateOf(TextFieldValue("")) }
    var themeColorPalette by remember { mutableStateOf<ColorSchemeThemePalette?>(null) }

    val showSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "Theme Generator",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            SelectedColorUI(colorHex, selectedColor, showSheet)

            if (showSheet.value) {
                KvColorPickerBottomSheet(showSheet = showSheet,
                    sheetState = sheetState, onColorSelected = {
                        selectedColor.value = it
                        colorHex.value = TextFieldValue(ColorUtil.getHex(color = selectedColor.value))
                    })
            }

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    themeColorPalette = KvColorPalette.instance.generateThemeColorSchemePalette(givenColor = selectedColor.value)
                }
            ) {
                Text("Generate Theme")
            }

            themeColorPalette?.let {
                Row (
                    modifier = Modifier
                ) {
                    Column (
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Light",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Column (
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .verticalScroll(rememberScrollState())
                        ) {
                            ThemeColorItem(color = it.lightColorScheme.primary, itemName = "Primary")
                            ThemeColorItem(color = it.lightColorScheme.secondary, itemName = "Secondary")
                            ThemeColorItem(color = it.lightColorScheme.tertiary, itemName = "Tertiary")
                            ThemeColorItem(color = it.lightColorScheme.quaternary, itemName = "Quaternary")
                            ThemeColorItem(color = it.lightColorScheme.onPrimary, itemName = "onPrimary")
                            ThemeColorItem(color = it.lightColorScheme.onSecondary, itemName = "onSecondary")
                            ThemeColorItem(color = it.lightColorScheme.background, itemName = "Background")
                            ThemeColorItem(color = it.lightColorScheme.shadow, itemName = "Shadow")
                        }
                    }

                    Column (
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Dark",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Column (
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp, start = 2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .verticalScroll(rememberScrollState())
                        ) {
                            ThemeColorItem(color = it.darkColorScheme.primary, itemName = "Primary")
                            ThemeColorItem(color = it.darkColorScheme.secondary, itemName = "Secondary")
                            ThemeColorItem(color = it.darkColorScheme.tertiary, itemName = "Tertiary")
                            ThemeColorItem(color = it.darkColorScheme.quaternary, itemName = "Quaternary")
                            ThemeColorItem(color = it.darkColorScheme.onPrimary, itemName = "onPrimary")
                            ThemeColorItem(color = it.darkColorScheme.onSecondary, itemName = "onSecondary")
                            ThemeColorItem(color = it.darkColorScheme.background, itemName = "Background")
                            ThemeColorItem(color = it.darkColorScheme.shadow, itemName = "Shadow")
                        }
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeGenDetailUIPreview() {
    KvColorPaletteTheme {
        ThemeGenDetailUI()
    }
}