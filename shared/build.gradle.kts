plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

version = "1.0"

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
}

val cokoinVersion = "0.3.2"
val koinVersion = "3.1.4"
val kotlinVersion = "1.6.10"
val kotlinxDatetimeVersion = "0.3.1"
val kotlinxSerializationVersion = "1.3.2"
val ktorVersion = "1.6.7"
val lifecycleVersion = "2.4.0"
val napierVersion = "2.3.0"
val sqlDelightVersion = "1.5.3"

kotlin {
    android()
    iosX64()
    iosArm64()
    //iosSimulatorArm64() sure all ios dependencies support this target
    jvm()
    macosX64()
    macosArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        osx.deploymentTarget = "10.14"
        framework {
            baseName = "shared"
            isStatic = false
        }
    }
    
    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.github.aakira:napier:$napierVersion")
                api("io.github.aakira:napier:$napierVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetimeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        // Android
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                implementation("io.insert-koin:koin-android:$koinVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        // Darwin
        val darwinMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val darwinTest by creating {
            dependsOn(commonTest)
        }
        // Ios
        val iosMain by creating {
            dependsOn(darwinMain)
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        //val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
        val iosTest by creating {
            dependsOn(darwinTest)
        }
        val iosX64Test by getting {
            dependsOn(iosTest)
        }
        val iosArm64Test by getting {
            dependsOn(iosTest)
        }
        //val iosSimulatorArm64Test by getting { dependsOn(iosTest) }
        // Jvm
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            }
        }
        // Macos
        val macosMain by creating {
            dependsOn(darwinMain)
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
        val macosTest by creating {
            dependsOn(darwinTest)
        }
        val macosX64Test by getting {
            dependsOn(macosMain)
        }
        val macosArm64Test by getting {
            dependsOn(macosMain)
        }
    }
}

sqldelight {
    database("F1Database") {
        packageName = "dev.mateusz1913.f1results.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}
