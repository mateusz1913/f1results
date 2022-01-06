package di

import dev.mateusz1913.f1results.di.AppInfo

object JVMAppInfo: AppInfo {
    override val appId: String
        get() = "JVM"
}