import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.ArrayList

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.1-rc2"
    id("org.openjfx.javafxplugin") version "0.0.9"
}

group = "dev.mateusz1913"
version = "1.0"

val cokoinVersion = "0.3.2"
val decomposeVersion = "0.4.0"
val koinVersion = "3.1.4"
val kotlinVersion = "1.6.10"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":sharedComposables"))
                implementation(kotlin("stdlib-jdk8"))
                implementation(compose.desktop.currentOs)
                implementation(compose.materialIconsExtended)
                implementation("dev.burnoo:cokoin:$cokoinVersion")
                implementation("com.arkivanov.decompose:decompose:$decomposeVersion")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:$decomposeVersion")
            }
        }
        val jvmTest by getting
    }
}

javafx {
    version = "15"
    val javaFXModules = ArrayList<String>()
    javaFXModules.add("javafx.controls")
    javaFXModules.add("javafx.swing")
    javaFXModules.add("javafx.web")
    modules = javaFXModules
}

compose.desktop {
    application {
        mainClass = "dev.mateusz1913.f1results.desktop.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "desktop"
            packageVersion = "1.0.0"
        }
    }
}
