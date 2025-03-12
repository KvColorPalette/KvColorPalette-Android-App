package com.kavi.droid.color.palette.app.ui.dashboard.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.util.ColorUtil
import com.kavi.droid.color.picker.ui.KvColorPickerBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTab(modifier: Modifier) {

    val showColorPicker = remember { mutableStateOf(false) }
    val colorPickerState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val selectedColor = remember { mutableStateOf(Color.White) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_BASE_COLOR).collect { themeColor ->
                selectedColor.value = ColorUtil.getColorFromHex(themeColor)
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
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable {
                    showColorPicker.value = true
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current Theme",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Box (
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .background(selectedColor.value, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                )
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 12.dp)
                )
            }
        }
    }

    if (showColorPicker.value) {
        KvColorPickerBottomSheet(showSheet = showColorPicker,
            sheetState = colorPickerState, onColorSelected = {
                selectedColor.value = it
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatastore.storeValue(AppDatastore.APP_THEME_BASE_COLOR, ColorUtil.getHexWithAlpha(selectedColor.value))
                }
            })
    }
}

@Preview
@Composable
fun ThemeColorGenPreview() {
    SettingsTab(Modifier)
}