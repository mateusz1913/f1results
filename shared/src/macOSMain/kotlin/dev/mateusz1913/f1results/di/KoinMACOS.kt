package dev.mateusz1913.f1results.di

import org.koin.core.KoinApplication
import org.koin.dsl.module

fun initKoinMacos(
    appInfo: AppInfo,
): KoinApplication = initKoin(
    module {
        single { appInfo }
    }
)