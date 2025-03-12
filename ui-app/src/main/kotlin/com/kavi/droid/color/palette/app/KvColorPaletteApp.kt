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
                    KvColorPalette.initialize(basicColor = ColorUtil.getColorFromHex(themeColor))
                } else {
                    KvColorPalette.initialize(basicColor = MatPackage.MatDGreen.color)
                }
            }
        }


    }
}