package com.kavi.droid.color.palette.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.app.R
import com.kavi.droid.color.palette.extension.isHighLightColor
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.palette.util.ColorUtil

@Composable
fun SelectedColorUI(
    colorHex: MutableState<TextFieldValue>,
    selectedColor: MutableState<Color>,
    showSheet: MutableState<Boolean>
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row {
            Column (
                modifier = Modifier
                    .weight(.75f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp),
                    text = "Select your color",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Text(
                    text = "Touch on the color box or type your color-hex " +
                            "on below to pick your primary color to generate color palette.",
                    textAlign = TextAlign.Start,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(1.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                    ),
                    value = colorHex.value,
                    maxLines = 1,
                    label = { Text(text = "Color Hex") },
                    onValueChange = { newColorHex ->
                        colorHex.value = newColorHex
                        if (ColorUtil.validateColorHex(newColorHex.text))
                            selectedColor.value = ColorUtil.getColorFromHex(newColorHex.text)
                        else {
                            TextFieldValue("")
                            println("Not Valid")
                        }
                    }
                )
            }

            // Display the current color in a Box with a MaterialTheme shape
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .height(70.dp)
                    .width(70.dp)
                    //.weight(.25f)
                    .background(selectedColor.value, shape = MaterialTheme.shapes.large)
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .clickable {
                        showSheet.value = true
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_click_me),
                    contentDescription = "Select Color",
                    tint = if (selectedColor.value.isHighLightColor) Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SelectedColorsUI(
    firstColorHex: MutableState<TextFieldValue>,
    secondColorHex: MutableState<TextFieldValue>,
    selectedFirstColor: MutableState<Color>,
    selectedSecondColor: MutableState<Color>,
    showSheetForFirst: MutableState<Boolean>,
    showSheetForSecond: MutableState<Boolean>,
    selectedThemeGenMode: MutableState<String> = mutableStateOf(ThemeGenMode.SEQUENCE.name),
    biasValue: MutableState<Float> = mutableFloatStateOf(0.5f)
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp))
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .padding(12.dp)
    ) {
        Column {
            Row {
                Column (
                    modifier = Modifier
                        .weight(.75f)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp),
                        text = "Select theme properties",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Text(
                        modifier = Modifier
                            .padding(end = 6.dp),
                        text = "Touch on the color box or type your color-hex " +
                                "on below to pick your primary color to generate color palette.",
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp
                    )

                    Row {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(.5f)
                                .width(100.dp)
                                .padding(1.dp)
                                .padding(end = 4.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                            ),
                            value = firstColorHex.value,
                            maxLines = 1,
                            label = { Text(text = "Color #1") },
                            onValueChange = { newColorHex ->
                                firstColorHex.value = newColorHex
                                if (ColorUtil.validateColorHex(newColorHex.text))
                                    selectedFirstColor.value = ColorUtil.getColorFromHex(newColorHex.text)
                                else {
                                    TextFieldValue("")
                                    println("Not Valid")
                                }
                            }
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .weight(.5f)
                                .width(100.dp)
                                .padding(1.dp)
                                .padding(end = 8.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                            ),
                            value = secondColorHex.value,
                            maxLines = 1,
                            label = { Text(text = "Color #2") },
                            onValueChange = { newColorHex ->
                                secondColorHex.value = newColorHex
                                if (ColorUtil.validateColorHex(newColorHex.text))
                                    selectedSecondColor.value = ColorUtil.getColorFromHex(newColorHex.text)
                                else {
                                    TextFieldValue("")
                                    println("Not Valid")
                                }
                            }
                        )
                    }

                }

                Row  {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        var value by remember { mutableFloatStateOf(.5f) }

                        AppVerticalSlider(
                            modifier = Modifier
                                .width(128.dp)
                                .height(50.dp)
                                .padding(top = 8.dp, end = 6.dp, start = 4.dp),
                            value = value,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.tertiary,
                                inactiveTrackColor = MaterialTheme.colorScheme.tertiary
                            ),
                            onValueChange = { value = it },
                            onValueChangeFinished = {
                                biasValue.value = (1f - value)
                            }
                        )
                    }

                    Column (
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        // Display the current color in a Box with a MaterialTheme shape
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(60.dp)
                                .width(60.dp)
                                .background(
                                    selectedFirstColor.value,
                                    shape = MaterialTheme.shapes.large
                                )
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    showSheetForFirst.value = true
                                }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_click_me),
                                contentDescription = "Select Color",
                                tint = if (selectedFirstColor.value.isHighLightColor) Color.Black else Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        // Display the current color in a Box with a MaterialTheme shape
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(60.dp)
                                .width(60.dp)
                                .background(
                                    selectedSecondColor.value,
                                    shape = MaterialTheme.shapes.large
                                )
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    showSheetForSecond.value = true
                                }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_click_me),
                                contentDescription = "Select Color",
                                tint = if (selectedSecondColor.value.isHighLightColor) Color.Black else Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            Row (modifier = Modifier.fillMaxWidth()) {
                Column (modifier = Modifier.weight(.65f)) {
                    AppDropDown(
                        title = "Theme Pattern",
                        selectableItems = listOf(ThemeGenMode.SEQUENCE.name, ThemeGenMode.BLEND.name),
                        selectedItem = selectedThemeGenMode
                    )
                }
                Column (modifier = Modifier.weight(.35f)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(1.dp)
                            .padding(start = 4.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                        ),
                        value = biasValue.value.toString(),
                        maxLines = 1,
                        label = { Text(text = "Bias") },
                        onValueChange = { newColorHex ->
                            biasValue.value = newColorHex.toFloat()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropDown(
    modifier: Modifier = Modifier,
    title: String,
    selectableItems: List<String>,
    selectedItem: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        ) {
            OutlinedTextField(
                value = selectedItem.value,
                onValueChange = {},
                readOnly = true,
                label = { Text(title) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        modifier = modifier
                            .rotate(if (expanded) 180f else 0f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                selectableItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedItem.value = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AppVerticalSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    enabled: Boolean = true,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors()
) {
    Slider(
        colors = colors,
        interactionSource = interactionSource,
        onValueChangeFinished = onValueChangeFinished,
        enabled = enabled,
        value = value,
        valueRange = valueRange,
        steps = steps,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(2.dp)
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxWidth,
                        minHeight = constraints.minHeight,
                        maxHeight = constraints.minHeight
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .then(modifier)
    )
}

@Preview(showBackground = true)
@Composable
fun CommonUIPreview() {
    val selectedColor = remember { mutableStateOf(Color.White) }
    val colorHex = remember { mutableStateOf(TextFieldValue("")) }
    val showSheet = remember { mutableStateOf(false) }

    //SelectedColorUI(colorHex, selectedColor, showSheet)
    SelectedColorsUI(colorHex, colorHex, selectedColor, selectedColor, showSheet, showSheet)
}