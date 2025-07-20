package com.kavi.droid.color.palette.app.ui.screen.dashboard.blend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.app.R
import com.kavi.droid.color.palette.extension.isHighLightColor
import com.kavi.droid.color.picker.ui.KvColorPickerBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBlendTab(modifier: Modifier, viewModel: ColorBlendViewModel = hiltViewModel()) {

    val showSheetForFirst = remember { mutableStateOf(false) }
    val showSheetForSecond = remember { mutableStateOf(false) }
    val selectedFirstColor by viewModel.selectedFirstColor.collectAsState()
    val selectedSecondColor by viewModel.selectedSecondColor.collectAsState()
    val colorBias by viewModel.colorBias.collectAsState()

    val sheetStateForFirstColor = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val sheetStateForSecondColor = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val blendColor by viewModel.blendColor.collectAsState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row (Modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 24.dp),
                text = "Color Blend",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier,
                text = "Touch on the color boxes to pick your first and second color to see the generated blend color from selected colors. " +
                        "From the slider you can choose how bias is your blend color to first or second color you selected",
                style = MaterialTheme.typography.bodyMedium
            )

            Row (
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
            ) {
                // Display the current color in a Box with a MaterialTheme shape
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(100.dp)
                            .width(100.dp)
                            .background(
                                selectedFirstColor,
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
                            tint = if (selectedFirstColor.isHighLightColor) Color.Black else Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                    Text(
                        text = "First color",
                    )
                }


                // Display the current color in a Box with a MaterialTheme shape
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(100.dp)
                            .width(100.dp)
                            .background(
                                selectedSecondColor,
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
                            tint = if (selectedSecondColor.isHighLightColor) Color.Black else Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                    Text(
                        text = "Second color",
                    )
                }
            }

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text (
                    text = "First",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(.25f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.End
                )
                
                Slider(
                    modifier = Modifier
                        //.width(128.dp)
                        .height(50.dp)
                        .weight(.7f),
                    value = colorBias,
                    valueRange = 0f..1f,
                    onValueChange = {
                        viewModel.setColorBias(it)
                        viewModel.blendColor()
                    },
                )
                Text(
                    text = "Second",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(.25f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
            }

            Text(
                text = "Color Bias: ${"%.2f".format(colorBias)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        blendColor,
                        shape = MaterialTheme.shapes.large
                    )
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(12.dp)
                    )
            ) {
                Row (
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_click_me),
                        contentDescription = "Select Color",
                        tint = if (blendColor.isHighLightColor) Color.Black else Color.White,
                    )
                    Text(
                        text = "Blended Color",
                        color = if (blendColor.isHighLightColor) Color.Black else Color.White,
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp),
                    )
                }
            }
        }

        if (showSheetForFirst.value) {
            KvColorPickerBottomSheet(showSheet = showSheetForFirst,
                sheetState = sheetStateForFirstColor, onColorSelected = {
                    viewModel.setFirstSelectedColor(it)
                    viewModel.blendColor()
                })
        }

        if (showSheetForSecond.value) {
            KvColorPickerBottomSheet(showSheet = showSheetForSecond,
                sheetState = sheetStateForSecondColor, onColorSelected = {
                    viewModel.setSecondSelectedColor(it)
                    viewModel.blendColor()
                })
        }
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
}

@Preview
@Composable
fun ColorBlendTabPreview() {
    ColorBlendTab(modifier = Modifier)
}