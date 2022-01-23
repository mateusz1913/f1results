import org.jetbrains.compose.compose
import java.util.ArrayList

group = "dev.mateusz1913"
version = "1.0"

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.1-rc2"
    id("org.openjfx.javafxplugin") version "0.0.9"
}

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
                implementation(kotlin("stdlib-jdk8"))
                implementation(compose.desktop.currentOs)
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
