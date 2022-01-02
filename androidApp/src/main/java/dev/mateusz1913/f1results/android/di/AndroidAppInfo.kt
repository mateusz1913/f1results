package dev.mateusz1913.f1results.android.di

import dev.mateusz1913.f1results.android.BuildConfig
import dev.mateusz1913.f1results.di.AppInfo

object AndroidAppInfo: AppInfo {
    override val appId: String
        get() = BuildConfig.APPLICATION_ID
}