package com.kavi.droid.color.palette.app

import android.app.Application
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.AppDatastore
import com.kavi.droid.color.palette.app.ui.dashboard.settings.ThemeType
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.droid.color.palette.util.ColorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KvColorPaletteApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the app datastore
        AppDatastore.init(this)

        CoroutineScope(Dispatchers.IO).launch {
            AppDatastore.retrieveString(AppDatastore.APP_THEME_TYPE).collect { themeType ->
                if (themeType == ThemeType.SINGLE_COLOR_THEME.name) {
                    AppDatastore.retrieveString(AppDatastore.APP_THEME_BASE_COLOR).collect { themeColor ->
                        if (themeColor != "NULL") {
                            KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(themeColor))
                        } else {
                            KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                        }
                    }
                } else if (themeType == ThemeType.MULTI_COLOR_THEME.name) {
                    AppDatastore.retrieveString(AppDatastore.APP_THEME_FIRST_COLOR).collect { firstColor ->
                        AppDatastore.retrieveString(AppDatastore.APP_THEME_SECOND_COLOR).collect { secondColor ->
                            if (firstColor != "NULL" && secondColor != "NULL") {
                                KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(firstColor), secondColor = ColorUtil.getColorFromHex(secondColor))
                            } else {
                                KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                            }
                        }
                    }
                } else {
                    KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                }
            }
        }
    }
}