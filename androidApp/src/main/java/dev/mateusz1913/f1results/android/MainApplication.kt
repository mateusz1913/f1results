package dev.mateusz1913.f1results.android

import android.app.Application
import dev.mateusz1913.f1results.android.di.appModule
import dev.mateusz1913.f1results.android.utils.initNapier
import dev.mateusz1913.f1results.di.initKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(appModule)
        initNapier()
    }
}