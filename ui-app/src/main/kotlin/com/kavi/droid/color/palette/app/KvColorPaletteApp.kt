package com.kavi.droid.color.palette.app

import android.app.Application
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.app.data.Quadruple
import com.kavi.droid.color.palette.app.ui.dashboard.settings.ThemeType
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.model.ThemeGenMode
import com.kavi.droid.color.palette.util.ColorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class KvColorPaletteApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the app datastore
        AppDatastore.init(this)

        CoroutineScope(Dispatchers.IO).launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_TYPE).collect { themeType ->
                if (themeType == ThemeType.SINGLE_COLOR_THEME.name) {
                    AppDatastore.retrieveString(AppDatastore.APP_THEME_SINGLE_BASE_COLOR).collect { themeColor ->
                        if (themeColor != "NULL") {
                            KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(themeColor))
                        } else {
                            KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                        }
                    }
                } else if (themeType == ThemeType.MULTI_COLOR_THEME.name) {
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
                } else {
                    KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                }
            }
        }
    }

    private fun getThemeMode(blendSwitch: Boolean): ThemeGenMode {
        return if (blendSwitch) { ThemeGenMode.BLEND } else { ThemeGenMode.SEQUENCE }
    }
}