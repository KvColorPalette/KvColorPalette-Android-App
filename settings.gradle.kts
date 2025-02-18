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
/*includeBuild("../KvColorPicker-Android") {
    dependencySubstitution {
        substitute(module("com.github.KvColorPalette:KvColorPicker-Android"))
            .using(project(":kv-color-picker"))
    }
}*/
 