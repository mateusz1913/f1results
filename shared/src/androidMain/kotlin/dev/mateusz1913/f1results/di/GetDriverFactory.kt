package dev.mateusz1913.f1results.di

import dev.mateusz1913.f1results.datasource.cache.DatabaseDriverFactory
import org.koin.core.scope.Scope

actual fun getDriverFactory(scope: Scope) = DatabaseDriverFactory(scope.get())
