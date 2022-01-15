import org.jetbrains.compose.compose

group = "dev.mateusz1913"
version = "1.0"

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version "1.0.1-rc2"
}

val accompanistVersion = "0.20.3"
val cokoinVersion = "0.3.2"
val composeVersion = "1.1.0-rc01"
val lifecycleVersion = "2.4.0"

kotlin {
    android()
    jvm() {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.animation)
                implementation(compose("org.jetbrains.compose.ui:ui-tooling"))
                implementation("dev.burnoo:cokoin:$cokoinVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                implementation("dev.burnoo:cokoin-android-viewmodel:$cokoinVersion")
                implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
                implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
            }
        }
        val jvmMain by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_15
        targetCompatibility = JavaVersion.VERSION_15
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}
