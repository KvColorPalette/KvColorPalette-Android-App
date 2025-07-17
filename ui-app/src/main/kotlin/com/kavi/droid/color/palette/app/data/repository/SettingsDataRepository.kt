package com.kavi.droid.color.palette.app.data.repository

import androidx.compose.ui.graphics.Color
import com.kavi.droid.color.palette.app.data.dto.Quadruple
import com.kavi.droid.color.palette.app.ui.screen.dashboard.settings.ThemeType
import kotlinx.coroutines.flow.Flow

interface SettingsDataRepository {

    suspend fun getSelectedThemeType(): Flow<String>

    suspend fun storeThemeType(themeType: ThemeType)

    suspend fun getSingleSelectedColor(): Flow<String>

    suspend fun storeSingleSelectedColor(color: Color)

    suspend fun getMultiSelectedColors(): Flow<Quadruple<String, String, Boolean, Float>>

    suspend fun storeMultiSelectedFirstColor(color: Color)

    suspend fun storeMultiSelectedSecondColor(color: Color)

    suspend fun storeBlendSwitchStatus(isBlending: Boolean)

    suspend fun storeColorBias(bias: Float)
}