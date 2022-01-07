package dev.mateusz1913.f1results.android

import android.app.Application
import dev.mateusz1913.f1results.android.utils.initNapier

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initNapier()
    }
}