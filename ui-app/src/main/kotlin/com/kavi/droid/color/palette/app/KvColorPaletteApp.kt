package com.kavi.droid.color.palette.app

import android.app.Application
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.app.data.dto.Quadruple
import com.kavi.droid.color.palette.app.data.repository.SettingsLocalRepositoryImpl
import com.kavi.droid.color.palette.app.ui.screen.dashboard.settings.ThemeType
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.palette.util.ColorUtil
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltAndroidApp
class KvColorPaletteApp: Application() {

    @Inject
    lateinit var settingsLocalRepositoryImpl: SettingsLocalRepositoryImpl

    override fun onCreate() {
        super.onCreate()

        // Initialize the app datastore
        AppDatastore.init(this)

        KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)

        CoroutineScope(Dispatchers.Main).launch {
            settingsLocalRepositoryImpl.getSelectedThemeType().collect { themeType ->
                when (themeType) {
                    ThemeType.SINGLE_COLOR_THEME.name -> {
                        settingsLocalRepositoryImpl.getSingleSelectedColor().collect { themeColor ->
                            if (themeColor != "NULL") {
                                KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(themeColor))
                            } else {
                                KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                            }
                        }
                    }
                    ThemeType.MULTI_COLOR_THEME.name -> {
                        settingsLocalRepositoryImpl.getMultiSelectedColors().collect { (firstColor, secondColor, blendSwitch, bias) ->
                            if (firstColor != "NULL" && secondColor != "NULL") {
                                KvColorPalette.initialize(
                                    baseColor = ColorUtil.getColorFromHex(firstColor),
                                    secondColor = ColorUtil.getColorFromHex(secondColor),
                                    bias = bias,
                                    themeGenMode = getThemeMode(blendSwitch)
                                )
                            } else {
                                KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                            }
                        }
                    }
                    else -> {
                        KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                    }
                }
            }
        }
    }

    private fun getThemeMode(blendSwitch: Boolean): ThemeGenMode {
        return if (blendSwitch) { ThemeGenMode.BLEND } else { ThemeGenMode.SEQUENCE }
    }
}