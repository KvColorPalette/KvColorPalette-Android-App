package com.kavi.droid.color.palette.app.ui.common.picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvColorPickerBottomSheet(showSheet: MutableState<Boolean>, sheetState: SheetState, onColorSelected: (selectedColor: Color) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = {
            showSheet.value = false
        },
        sheetState = sheetState,
    ) {
        Column {
            var selectedColor by remember { mutableStateOf(Color.Black) }

            Text(
                text ="Pick you color",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                fontSize = 32.sp
            )

            Text(
                text ="By dragging \'RED\', \'GREEN\', and \'BLUE\' bars below, you can select " +
                        "or generate your color you want exactly, or type your color\'s hex and set it.",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp),
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp
            )

            KvColorPicker(
                modifier = Modifier.padding(16.dp),
                onColorSelected = {
                    selectedColor = it
                }
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            ) {
                OutlinedButton (
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        showSheet.value = false
                    }
                ) {
                    Text("Close")
                }

                Button(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        showSheet.value = false
                        onColorSelected.invoke(selectedColor)
                    }
                ) {
                    Text("Select")
                }
            }
        }

    }
}