package com.kavi.droid.color.palette.app

import android.app.Application
import com.kavi.droid.color.palette.KvColorPalette
import com.kavi.droid.color.palette.app.data.AppDatastore
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
            AppDatastore.retrieveString(AppDatastore.APP_THEME_BASE_COLOR).collect { themeColor ->
                // Initialize the kv color palette
                if (themeColor != "NULL") {
                    KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(themeColor))
                    //KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex(themeColor), secondColor = ColorUtil.getColorFromHex("#283593"), themeGenPattern = ThemeGenPattern.BLEND)
                    //KvColorPalette.initialize(baseColor = ColorUtil.getColorFromHex("#1e88e5"), secondColor = ColorUtil.getColorFromHex("#ff6f00"), themeGenPattern = ThemeGenPattern.BLEND)
                } else {
                    KvColorPalette.initialize(baseColor = MatPackage.MatDGreen.color)
                }
            }
        }


    }
}