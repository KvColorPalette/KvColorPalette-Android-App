package com.kavi.droid.color.palette.app.ui.dashboard.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.ui.common.AppDropDown
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.picker.ui.KvColorPickerBottomSheet
import kotlinx.coroutines.flow.firstOrNull

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

    var colorPickerOpenFor by remember { mutableStateOf(ColorSelection.SINGLE_THEME) }

    val viewModel = SettingsViewModel()

    val selectedThemeType = remember { mutableStateOf("") }

    val selectedSingleColor by viewModel.selectedSingleColor.collectAsState()

    val selectedFirstColor by viewModel.selectedMultiFirstColor.collectAsState()
    val selectedSecondColor by viewModel.selectedMultiSecondColor.collectAsState()
    val blendSwitch by viewModel.blendSwitch.collectAsState()
    val colorBias by viewModel.colorBiasValue.collectAsState()
    val blendColor by viewModel.blendColor.collectAsState()

    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.selectedThemeType.firstOrNull()?.let {
            selectedThemeType.value = it
        }
    }

    LaunchedEffect(selectedThemeType.value) {
        isExpanded = true
        viewModel.storeSelectedThemeType(themeType = selectedThemeType.value)
    }

    Column (
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
                .padding(8.dp)
                .fillMaxWidth()
                //.height(80.dp)
                .border(1.dp, MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Theme Type",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        AppDropDown(
                            modifier = Modifier
                                .padding(start = 30.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                            title = "Theme Type", selectableItems = listOf("Single Color", "Multi Color"),
                            selectedItem = selectedThemeType
                        )
                    }

                    AnimatedContent(
                        targetState = isExpanded,
                        transitionSpec = {
                            (fadeIn()).togetherWith(fadeOut())
                        }
                    ) { isExpanded ->
                        if (isExpanded) {
                            Column {
                                if (selectedThemeType.value == "Single Color") {
                                    Row (
                                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp, top = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Select your color",
                                            modifier = Modifier.padding(start = 16.dp),
                                            fontSize = 18.sp
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Box (
                                            modifier = Modifier
                                                .padding(end = 4.dp)
                                                .width(35.dp)
                                                .height(35.dp)
                                                .background(selectedSingleColor, shape = RoundedCornerShape(8.dp))
                                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                                .clickable {
                                                    colorPickerOpenFor = ColorSelection.SINGLE_THEME
                                                    showColorPicker.value = true
                                                }
                                        )
                                    }
                                } else if (selectedThemeType.value == "Multi Color") {
                                    Row (
                                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Select your colors",
                                            fontSize = 18.sp
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Box (
                                            modifier = Modifier
                                                .width(35.dp)
                                                .height(35.dp)
                                                .background(selectedFirstColor, shape = RoundedCornerShape(8.dp))
                                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                                .clickable {
                                                    colorPickerOpenFor = ColorSelection.MULTI_THEME_FIRST
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
                                                .background(selectedSecondColor, shape = RoundedCornerShape(8.dp))
                                                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                                .clickable {
                                                    colorPickerOpenFor = ColorSelection.MULTI_THEME_SECOND
                                                    showColorPicker.value = true
                                                }
                                        )
                                        if (blendSwitch) {
                                            Text(
                                                text = "=",
                                                modifier = Modifier.padding(
                                                    start = 8.dp,
                                                    end = 8.dp
                                                ),
                                                fontSize = 24.sp,
                                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .width(35.dp)
                                                    .height(35.dp)
                                                    .background(
                                                        blendColor,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .border(
                                                        2.dp,
                                                        MaterialTheme.colorScheme.primary,
                                                        RoundedCornerShape(8.dp)
                                                    )
                                            )
                                        }
                                    }
                                    Row (
                                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Do you want to blend colors?",
                                            fontSize = 18.sp
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        Switch(
                                            checked = blendSwitch,
                                            onCheckedChange = {
                                                viewModel.updateBlendSwitch(it)
                                            }
                                        )
                                    }

                                    if (blendSwitch) {
                                        Row (
                                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Bias",
                                                fontSize = 18.sp
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            Text(
                                                text = "First",
                                                modifier = Modifier.padding(end = 8.dp),
                                                fontSize = 14.sp
                                            )
                                            Slider(
                                                modifier = Modifier
                                                    .width(128.dp)
                                                    .height(50.dp),
                                                value = colorBias,
                                                valueRange = 0f..1f,
                                                onValueChange = {
                                                    viewModel.updateColorBias(it)
                                                },
                                            )
                                            Text(
                                                text = "Second",
                                                modifier = Modifier.padding(start = 8.dp),
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }

                                Button(
                                    modifier = Modifier
                                        .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    onClick = {
                                        if (selectedThemeType.value == "Single Color") {
                                            val colorThemeScheme = KvColorPalette.instance.generateThemeColorSchemePalette(selectedSingleColor)

                                            // Update the color scheme in the `colorScheme` state
                                            colorScheme.value = when {
                                                isDarkTheme -> colorThemeScheme.darkColorScheme
                                                else -> colorThemeScheme.lightColorScheme
                                            }
                                        } else if (selectedThemeType.value == "Multi Color") {
                                            // Generate the new theme palette based on the selected color using `KvColorPalette`
                                            val colorThemeScheme =
                                                KvColorPalette.instance.generateMultiColorThemeColorSchemePalette(
                                                    selectedFirstColor,
                                                    selectedSecondColor,
                                                    colorBias,
                                                    themeGenMode(blendSwitch)
                                                )

                                            // Update the color scheme in the `colorScheme` state
                                            colorScheme.value = when {
                                                isDarkTheme -> colorThemeScheme.darkColorScheme
                                                else -> colorThemeScheme.lightColorScheme
                                            }
                                        }
                                    }
                                ) {
                                    Text("Update Theme")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showColorPicker.value) {
        KvColorPickerBottomSheet(
            showSheet = showColorPicker,
            sheetState = colorPickerState,
            onColorSelected = {
                when (colorPickerOpenFor) {
                    ColorSelection.SINGLE_THEME -> {
                        viewModel.updateSingleSelectedColor(it)
                    }
                    ColorSelection.MULTI_THEME_FIRST -> {
                        viewModel.updateMultiFirstColor(it)
                    }
                    ColorSelection.MULTI_THEME_SECOND -> {
                        viewModel.updateMultiSecondColor(it)
                    }
                }
            }
        )
    }
}

private fun themeGenMode(blendSwitch: Boolean) =  if (blendSwitch) { ThemeGenMode.BLEND } else { ThemeGenMode.SEQUENCE }

@Preview
@Composable
fun SettingsPreview() {
    SettingsTab(Modifier, remember { mutableStateOf(null) })
}