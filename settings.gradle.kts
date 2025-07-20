import java.net.URI

rootProject.name = "KvColorPalette-Android-App"

listOf(
    ":ui-app"
).onEach {
    include(it)
}

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when(requested.id.toString()) {
                in listOf(
                    "com.google.gms.google-services"
                ) -> useModule("com.google.gms:google-services:${requested.version}")
                in listOf(
                    "kotlin-serialization"
                ) -> useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                in listOf(
                    "kotlin-kapt"
                ) -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "com.google.dagger.hilt.android" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
                else -> return@eachPlugin
            }
        }
    }

    repositories {
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./gradle/version-catalog/libs.versions.toml"))
        }
    }

    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

// This is for local development (Use this as a gradle composite build)
includeBuild("../KvColorPalette-Android") {
    dependencySubstitution {
        substitute(module("com.github.KvColorPalette:KvColorPalette-Android"))
            .using(project(":kv-color-palette"))
    }
}
/*includeBuild("../KvColorPicker-Android") {
    dependencySubstitution {
        substitute(module("com.github.KvColorPalette:KvColorPicker-Android"))
            .using(project(":kv-color-picker"))
    }
}*/
 