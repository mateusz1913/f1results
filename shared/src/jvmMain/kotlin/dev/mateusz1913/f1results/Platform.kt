package dev.mateusz1913.f1results

actual class Platform actual constructor() {
    actual val platform: String = "JVM - ${System.getProperty("os.name")} ${System.getProperty("os.version")}"
}