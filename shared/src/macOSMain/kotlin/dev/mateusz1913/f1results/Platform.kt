package dev.mateusz1913.f1results

import platform.Foundation.NSProcessInfo

actual class Platform actual constructor() {
    actual val platform: String = "MacOS ${NSProcessInfo.processInfo.operatingSystemVersionString}"
}