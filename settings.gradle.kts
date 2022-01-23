pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "f1results"
include(":shared")
include(":desktopComposables")
include(":sharedComposables")
include(":androidApp")
include(":desktop")
