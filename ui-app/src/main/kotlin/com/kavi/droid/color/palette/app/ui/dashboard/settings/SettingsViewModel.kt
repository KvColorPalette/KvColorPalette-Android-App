package com.kavi.droid.color.palette.app.ui.dashboard.settings

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.app.data.Quadruple
import com.kavi.droid.color.palette.util.ColorUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel: ViewModel() {
    private val _selectedThemeType = MutableStateFlow("")
    private val _selectedSingleColor = MutableStateFlow(Color.White)
    private val _selectedMultiFirstColor = MutableStateFlow(Color.White)
    private val _selectedMultiSecondColor = MutableStateFlow(Color.White)
    private val _blendSwitch = MutableStateFlow(false)
    private val _colorBiasValue = MutableStateFlow(0.5f)
    private val _blendColor = MutableStateFlow(Color.White)

    val selectedThemeType: StateFlow<String> = _selectedThemeType
    val selectedSingleColor: StateFlow<Color> = _selectedSingleColor
    val selectedMultiFirstColor: StateFlow<Color> = _selectedMultiFirstColor
    val selectedMultiSecondColor: StateFlow<Color> = _selectedMultiSecondColor
    val blendSwitch: StateFlow<Boolean> = _blendSwitch
    val colorBiasValue: StateFlow<Float> = _colorBiasValue
    val blendColor: StateFlow<Color> = _blendColor

    init {
        getSelectedThemeType()
        getSingleSelectedColor()
        getMultiSelectedColors()
    }

    private fun getSelectedThemeType() {
        viewModelScope.launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_TYPE).collect { themeType ->
                if (themeType == ThemeType.MULTI_COLOR_THEME.name) {
                    _selectedThemeType.value = "Multi Color"
                } else {
                    _selectedThemeType.value = "Single Color"
                }
            }
        }
    }

    private fun getSingleSelectedColor() {
        viewModelScope.launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_SINGLE_BASE_COLOR).collect { color ->
                if (color.isEmpty() || color == "NULL") {
                    _selectedSingleColor.value = Color.White
                } else
                    _selectedSingleColor.value = ColorUtil.getColorFromHex(color)
            }
        }
    }

    private fun getMultiSelectedColors() {
        viewModelScope.launch {
            combine(
                combine(
                    AppDatastore.retrieveString(AppDatastore.APP_THEME_MULTI_FIRST_COLOR),
                    AppDatastore.retrieveString(AppDatastore.APP_THEME_MULTI_SECOND_COLOR),
                ) { firstColor, secondColor -> Pair(firstColor, secondColor) },
                combine(
                    AppDatastore.retrieveBoolean(AppDatastore.APP_THEME_MULTI_BLEND_SWITCH),
                    AppDatastore.retrieveFloat(AppDatastore.APP_THEME_MULTI_BIAS),
                ) { blendSwitch, bias -> Pair(blendSwitch, bias) }
            ) { pairOne, pairTwo ->
                Quadruple(pairOne.first, pairOne.second, pairTwo.first, pairTwo.second)
            }.collect { (firstColor, secondColor, blendSwitch, bias) ->
                if (firstColor.isEmpty() || firstColor == "NULL") {
                    _selectedMultiFirstColor.value = Color.White
                } else { _selectedMultiFirstColor.value = ColorUtil.getColorFromHex(firstColor) }

                if (secondColor.isEmpty() || secondColor == "NULL") {
                    _selectedMultiSecondColor.value = Color.White
                } else { _selectedMultiSecondColor.value = ColorUtil.getColorFromHex(secondColor) }

                _blendSwitch.value = blendSwitch
                _colorBiasValue.value = bias

                if (blendSwitch) {
                    _blendColor.value = ColorUtil.blendColors(
                        firstColor = _selectedMultiFirstColor.value,
                        secondColor = _selectedMultiSecondColor.value,
                        bias = bias
                    )
                }
            }
        }
    }

    fun storeSelectedThemeType(themeType: String) {
        viewModelScope.launch {
            if (themeType == "Single Color") {
                AppDatastore.storeValue(AppDatastore.APP_THEME_TYPE, ThemeType.SINGLE_COLOR_THEME.name)
            } else if (themeType == "Multi Color") {
                AppDatastore.storeValue(AppDatastore.APP_THEME_TYPE, ThemeType.MULTI_COLOR_THEME.name)
            }
        }
    }

    fun updateSingleSelectedColor(color: Color) {
        _selectedSingleColor.value = color
        viewModelScope.launch {
            AppDatastore.storeValue(AppDatastore.APP_THEME_SINGLE_BASE_COLOR, ColorUtil.getHex(color = color))
        }
    }

    fun updateMultiFirstColor(color: Color) {
        _selectedMultiFirstColor.value = color
        if (_blendSwitch.value) {
            _blendColor.value =
                ColorUtil.blendColors(color, _selectedMultiSecondColor.value, _colorBiasValue.value)
        }
        viewModelScope.launch {
            AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_FIRST_COLOR, ColorUtil.getHex(color = color))
        }
    }

    fun updateMultiSecondColor(color: Color) {
        _selectedMultiSecondColor.value = color
        if (_blendSwitch.value) {
            _blendColor.value =
                ColorUtil.blendColors(_selectedMultiFirstColor.value, color, _colorBiasValue.value)
        }
        viewModelScope.launch {
            AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_SECOND_COLOR, ColorUtil.getHex(color = color))
        }
    }

    fun updateBlendSwitch(isBlending: Boolean) {
        _blendSwitch.value = isBlending
        viewModelScope.launch {
            AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_BLEND_SWITCH, isBlending)
        }

        if (isBlending) {
            _blendColor.value = ColorUtil.blendColors(_selectedMultiFirstColor.value, _selectedMultiSecondColor.value, _colorBiasValue.value)
        }
    }

    fun updateColorBias(newBias: Float) {
        _colorBiasValue.value = newBias
        _blendColor.value = ColorUtil.blendColors(_selectedMultiFirstColor.value, _selectedMultiSecondColor.value, newBias)
        viewModelScope.launch {
            AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_BIAS, newBias)
        }
    }
}