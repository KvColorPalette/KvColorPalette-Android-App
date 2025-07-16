package com.kavi.droid.color.palette.app.data.repository

import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.app.data.dto.Quadruple
import com.kavi.droid.color.palette.app.ui.screen.dashboard.settings.ThemeType
import com.kavi.droid.color.palette.util.ColorUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SettingsLocalRepositoryImpl @Inject constructor(): SettingsDataRepository {
    override suspend fun getSelectedThemeType(): Flow<String> =
        AppDatastore.retrieveString(AppDatastore.APP_THEME_TYPE)

    override suspend fun storeThemeType(themeType: ThemeType) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_TYPE, themeType.name)
    }

    override suspend fun getSingleSelectedColor(): Flow<String> =
        AppDatastore.retrieveString(AppDatastore.APP_THEME_SINGLE_BASE_COLOR)

    override suspend fun storeSingleSelectedColor(color: Color) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_SINGLE_BASE_COLOR, ColorUtil.getHex(color = color))
    }

    override suspend fun getMultiSelectedColors(): Flow<Quadruple<String, String, Boolean, Float>> =
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
    }

    override suspend fun storeMultiSelectedFirstColor(color: Color) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_FIRST_COLOR, ColorUtil.getHex(color = color))
    }

    override suspend fun storeMultiSelectedSecondColor(color: Color) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_SECOND_COLOR, ColorUtil.getHex(color = color))
    }

    override suspend fun storeBlendSwitchStatus(isBlending: Boolean) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_BLEND_SWITCH, isBlending)
    }

    override suspend fun storeColorBias(bias: Float) {
        AppDatastore.storeValue(AppDatastore.APP_THEME_MULTI_BIAS, bias)
    }
}