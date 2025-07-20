package com.kavi.droid.color.palette.app.ui.screen.dashboard.blend

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.kavi.droid.color.palette.util.ColorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ColorBlendViewModel @Inject constructor(): ViewModel() {

    private var _selectedFirstColor = MutableStateFlow(Color.White)
    val selectedFirstColor: StateFlow<Color> = _selectedFirstColor

    private var _selectedSecondColor = MutableStateFlow(Color.White)
    val selectedSecondColor: StateFlow<Color> = _selectedSecondColor

    private var _colorBias = MutableStateFlow(0.5f)
    val colorBias: StateFlow<Float> = _colorBias

    private var _blendColor = MutableStateFlow(Color.White)
    val blendColor: StateFlow<Color> = _blendColor


    fun setFirstSelectedColor(color: Color) {
        _selectedFirstColor.value = color
    }

    fun setSecondSelectedColor(color: Color) {
        _selectedSecondColor.value = color
    }

    fun setColorBias(bias: Float) {
        _colorBias.value = bias
    }

    fun blendColor() {
        _blendColor.value = ColorUtil.blendColors(firstColor = _selectedFirstColor.value, secondColor = _selectedSecondColor.value, bias = _colorBias.value)
    }
}