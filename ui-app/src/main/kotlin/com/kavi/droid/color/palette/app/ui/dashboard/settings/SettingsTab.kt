package com.kavi.droid.color.palette.app.ui.dashboard.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.palette.util.ColorUtil
import com.kavi.droid.color.picker.ui.KvColorPickerBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class ColorSelection {
    SINGLE_THEME, MULTI_THEME_FIRST, MULTI_THEME_SECOND
}

enum class ThemeType {
    SINGLE_COLOR_THEME, MULTI_COLOR_THEME
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(modifier: Modifier, colorScheme: MutableState<ColorScheme?>) {

    val isDarkTheme: Boolean = isSystemInDarkTheme()
    val showColorPicker = remember { mutableStateOf(false) }
    val colorPickerState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val selectedFor = remember { mutableStateOf(ColorSelection.SINGLE_THEME) }
    val selectedColor = remember { mutableStateOf(Color.White) }
    val selectedFirstColor = remember { mutableStateOf(Color.White) }
    val selectedSecondColor = remember { mutableStateOf(Color.White) }
    val blendColor = remember { mutableStateOf(Color.White) }
    val isSingleColorThemeSelected = remember { mutableStateOf(true) }
    val isMultiColorThemeSelected = remember { mutableStateOf(false) }
    var colorBiasValue by remember { mutableFloatStateOf(.5f) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_TYPE).collect { themeType ->
                if (themeType == ThemeType.SINGLE_COLOR_THEME.name) {
                    isSingleColorThemeSelected.value = true
                    isMultiColorThemeSelected.value = false
                } else {
                    isSingleColorThemeSelected.value = false
                    isMultiColorThemeSelected.value = true
                }
            }
            AppDatastore.retrieveString(AppDatastore.APP_THEME_BASE_COLOR).collect { themeColorHex ->
                if (ColorUtil.validateColorHex(themeColorHex)) {
                    selectedColor.value = ColorUtil.getColorFromHex(themeColorHex)
                }
            }
            AppDatastore.retrieveString(AppDatastore.APP_THEME_FIRST_COLOR).collect { firstColor ->
                if (ColorUtil.validateColorHex(firstColor)) {
                    selectedFirstColor.value = ColorUtil.getColorFromHex(firstColor)
                }
            }
            AppDatastore.retrieveString(AppDatastore.APP_THEME_SECOND_COLOR).collect { secondColor ->
                if (ColorUtil.validateColorHex(secondColor)) {
                    selectedSecondColor.value = ColorUtil.getColorFromHex(secondColor)
                }
            }
        }
    }

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
                text = "Settings",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(MaterialTheme.colorScheme.tertiary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Single color Theme",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box (
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .width(35.dp)
                                .height(35.dp)
                                .background(selectedColor.value, shape = RoundedCornerShape(8.dp))
                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                .clickable (enabled = isSingleColorThemeSelected.value) {
                                    selectedFor.value = ColorSelection.SINGLE_THEME
                                    showColorPicker.value = true
                                }
                        )
                        Switch(
                            modifier = Modifier.padding(end = 8.dp, start = 4.dp),
                            checked = isSingleColorThemeSelected.value, onCheckedChange = { onChecked ->
                                if (onChecked) {
                                    isSingleColorThemeSelected.value = true
                                    isMultiColorThemeSelected.value = false

                                    CoroutineScope(Dispatchers.IO).launch {
                                        AppDatastore.storeValue(AppDatastore.APP_THEME_TYPE, ThemeType.SINGLE_COLOR_THEME.name)
                                    }
                                }
                            }
                        )
                    }
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(100.dp)
                .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(MaterialTheme.colorScheme.tertiary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Multi color Theme",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            modifier = Modifier.padding(end = 8.dp),
                            checked = isMultiColorThemeSelected.value, onCheckedChange = { onChecked ->
                                if (onChecked) {
                                    isSingleColorThemeSelected.value = false
                                    isMultiColorThemeSelected.value = true

                                    CoroutineScope(Dispatchers.IO).launch {
                                        AppDatastore.storeValue(AppDatastore.APP_THEME_TYPE, ThemeType.MULTI_COLOR_THEME.name)
                                    }
                                }
                            }
                        )
                    }
                    Row (
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box (
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .background(selectedFirstColor.value, shape = RoundedCornerShape(8.dp))
                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                .clickable (enabled = isMultiColorThemeSelected.value) {
                                    selectedFor.value = ColorSelection.MULTI_THEME_FIRST
                                    showColorPicker.value = true
                                }
                        )
                        Text(
                            text = "+",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontSize = 24.sp,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                        )
                        Box (
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .background(selectedSecondColor.value, shape = RoundedCornerShape(8.dp))
                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                .clickable (enabled = isMultiColorThemeSelected.value) {
                                    selectedFor.value = ColorSelection.MULTI_THEME_SECOND
                                    showColorPicker.value = true
                                }
                        )
                        Text(
                            text = "=",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontSize = 24.sp,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                        )
                        Box (
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp)
                                .background(blendColor.value, shape = RoundedCornerShape(8.dp))
                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Slider(
                            modifier = Modifier
                                .width(128.dp)
                                .height(50.dp),
                            value = colorBiasValue,
                            valueRange = 0f..1f,
                            onValueChange = {
                                colorBiasValue = it
                                blendColor.value = ColorUtil.blendColors(selectedFirstColor.value, selectedSecondColor.value, colorBiasValue)

                                if (isMultiColorThemeSelected.value) {
                                    // Generate the new theme palette based on the selected color using `KvColorPalette`
                                    val colorThemeScheme =
                                        KvColorPalette.instance.generateMultiColorThemeColorSchemePalette(
                                            selectedFirstColor.value,
                                            selectedSecondColor.value,
                                            colorBiasValue,
                                            ThemeGenMode.BLEND
                                        )

                                    // Update the color scheme in the `colorScheme` state
                                    colorScheme.value = when {
                                        isDarkTheme -> colorThemeScheme.darkColorScheme
                                        else -> colorThemeScheme.lightColorScheme
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }

    if (showColorPicker.value) {
        KvColorPickerBottomSheet(showSheet = showColorPicker,
            sheetState = colorPickerState, onColorSelected = {
                when (selectedFor.value) {
                    ColorSelection.SINGLE_THEME -> {
                        selectedColor.value = it
                        CoroutineScope(Dispatchers.IO).launch {
                            // Store the picked color in the datastore
                            AppDatastore.storeValue(AppDatastore.APP_THEME_BASE_COLOR, ColorUtil.getHexWithAlpha(selectedColor.value))

                            if (isSingleColorThemeSelected.value) {
                                // Generate the new theme palette based on the selected color using `KvColorPalette`
                                val colorThemeScheme =
                                    KvColorPalette.instance.generateThemeColorSchemePalette(
                                        selectedColor.value
                                    )

                                // Update the color scheme in the `colorScheme` state
                                colorScheme.value = when {
                                    isDarkTheme -> colorThemeScheme.darkColorScheme
                                    else -> colorThemeScheme.lightColorScheme
                                }
                            }
                        }
                    }
                    ColorSelection.MULTI_THEME_FIRST -> {
                        selectedFirstColor.value = it
                        CoroutineScope(Dispatchers.IO).launch {
                            // Store the picked color in the datastore
                            AppDatastore.storeValue(AppDatastore.APP_THEME_FIRST_COLOR, ColorUtil.getHexWithAlpha(selectedFirstColor.value))

                            if (isMultiColorThemeSelected.value) {
                                // Generate the new theme palette based on the selected color using `KvColorPalette`
                                val colorThemeScheme =
                                    KvColorPalette.instance.generateMultiColorThemeColorSchemePalette(
                                        selectedFirstColor.value, selectedSecondColor.value, colorBiasValue, ThemeGenMode.BLEND
                                    )

                                // Update the color scheme in the `colorScheme` state
                                colorScheme.value = when {
                                    isDarkTheme -> colorThemeScheme.darkColorScheme
                                    else -> colorThemeScheme.lightColorScheme
                                }
                            }
                        }

                        blendColor.value = ColorUtil.blendColors(selectedFirstColor.value, selectedSecondColor.value, colorBiasValue)
                    }
                    ColorSelection.MULTI_THEME_SECOND -> {
                        selectedSecondColor.value = it
                        CoroutineScope(Dispatchers.IO).launch {
                            // Store the picked color in the datastore
                            AppDatastore.storeValue(AppDatastore.APP_THEME_SECOND_COLOR, ColorUtil.getHexWithAlpha(selectedSecondColor.value))

                            if (isMultiColorThemeSelected.value) {
                                // Generate the new theme palette based on the selected color using `KvColorPalette`
                                val colorThemeScheme =
                                    KvColorPalette.instance.generateMultiColorThemeColorSchemePalette(
                                        selectedFirstColor.value, selectedSecondColor.value, colorBiasValue, ThemeGenMode.BLEND
                                    )

                                // Update the color scheme in the `colorScheme` state
                                colorScheme.value = when {
                                    isDarkTheme -> colorThemeScheme.darkColorScheme
                                    else -> colorThemeScheme.lightColorScheme
                                }
                            }
                        }

                        blendColor.value = ColorUtil.blendColors(selectedFirstColor.value, selectedSecondColor.value, colorBiasValue)
                    }
                }
            })
    }
}

@Preview
@Composable
fun ThemeColorGenPreview() {
    SettingsTab(Modifier, remember { mutableStateOf(null) })
}