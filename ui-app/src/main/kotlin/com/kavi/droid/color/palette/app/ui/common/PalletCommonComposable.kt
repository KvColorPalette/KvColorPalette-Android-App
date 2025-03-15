package com.kavi.droid.color.palette.app.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.isHighLightColor
import com.kavi.droid.color.palette.util.ColorUtil
import java.util.Locale

@Composable
fun ColorBox(givenColor: Color, selectedColor: Color?, onSelect: (color: Color) -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    selectedColor?.let {
        isSelected = ColorUtil.getHexWithAlpha(givenColor) == ColorUtil.getHexWithAlpha(it)
    }

    Box(
        modifier = Modifier
            .width(35.dp)
            .height(35.dp)
            .background(givenColor, RectangleShape)
            .clickable {
                isSelected = true
                onSelect(givenColor)
            }
            .then(if (isSelected) Modifier.border(2.dp, Color.White) else Modifier)
    )
}

@Composable
fun ColorDetailRow(selectedColorList: List<Color>) {
    Row (
        modifier = Modifier
            .padding(top = 4.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            var firstSpotColor: Color = Color.White
            if (selectedColorList.isNotEmpty()) {
                firstSpotColor = selectedColorList[0]
            }

            Box(
                Modifier.padding(end = 6.dp)
                    .width(58.dp)
                    .height(58.dp)
                    .background(firstSpotColor, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )

            SelectionContainer {
                Text(
                    text = ColorUtil.getHex(color = firstSpotColor),
                    modifier = Modifier
                        .width(60.dp)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column {
            var secondSpotColor: Color = Color.White
            if (selectedColorList.size >= 2) {
                secondSpotColor = selectedColorList[1]
            }

            Box(
                Modifier.padding(end = 6.dp)
                    .width(58.dp)
                    .height(58.dp)
                    .background(secondSpotColor, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )

            SelectionContainer {
                Text(
                    text = ColorUtil.getHex(color = secondSpotColor),
                    modifier = Modifier
                        .width(60.dp)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column {
            var thirdSpotColor: Color = Color.White
            if (selectedColorList.size >= 3) {
                thirdSpotColor = selectedColorList[2]
            }

            Box(
                Modifier.padding(end = 6.dp)
                    .width(58.dp)
                    .height(58.dp)
                    .background(thirdSpotColor, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )

            SelectionContainer {
                Text(
                    text = ColorUtil.getHex(color = thirdSpotColor),
                    modifier = Modifier
                        .width(60.dp)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column {
            var forthSpotColor: Color = Color.White
            if (selectedColorList.size >= 4) {
                forthSpotColor = selectedColorList[3]
            }

            Box(
                Modifier.padding(end = 6.dp)
                    .width(58.dp)
                    .height(58.dp)
                    .background(forthSpotColor, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )

            SelectionContainer {
                Text(
                    text = ColorUtil.getHex(color = forthSpotColor),
                    modifier = Modifier
                        .width(60.dp)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column {
            var fifthSpotColor: Color = Color.White
            if (selectedColorList.size >= 5) {
                fifthSpotColor = selectedColorList[4]
            }

            Box(
                Modifier.padding(end = 6.dp)
                    .width(58.dp)
                    .height(58.dp)
                    .background(fifthSpotColor, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )

            SelectionContainer {
                Text(
                    text = ColorUtil.getHex(color = fifthSpotColor),
                    modifier = Modifier
                        .width(60.dp)
                        .padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ColorStrip(color: Color) {
    Row {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(color)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Red: ${String.format(Locale.ENGLISH, "%.2f", color.red)}, Green: ${String.format(Locale.ENGLISH, "%.2f", color.green)}, Blue: ${String.format(Locale.ENGLISH, "%.2f", color.blue)}",
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    color = if (color.isHighLightColor) Color.Black else Color.White
                )

                SelectionContainer {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = ColorUtil.getHexWithAlpha(color),
                        textAlign = TextAlign.End,
                        color = if (color.isHighLightColor) Color.Black else Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaletteCommonUIPreview() {
    ColorStrip(color = Color.Red)
}