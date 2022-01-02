package dev.mateusz1913.f1results.android.utils

import dev.mateusz1913.f1results.android.BuildConfig
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initNapier() {
    if (BuildConfig.DEBUG) {
        Napier.base(DebugAntilog())
    }
}