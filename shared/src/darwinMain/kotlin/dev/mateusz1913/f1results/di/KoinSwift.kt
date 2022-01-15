package dev.mateusz1913.f1results.di

import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

@Suppress("unused")
fun initKoinSwift(): KoinApplication = initKoin()

@Suppress("unused")
fun <T> Koin.getDependency(objCClass: ObjCClass): T? =
    getOriginalKotlinClass(objCClass)?.let {
        getDependency(it)
    }

@Suppress("unused")
fun <T> Koin.getCircuitViewModel(objCClass: ObjCClass, circuitId: String): T? =
    getOriginalKotlinClass(objCClass)?.let {
        getDependency(it, circuitId)
    }

@Suppress("unused")
fun <T> Koin.getDriverViewModel(objCClass: ObjCClass, driverId: String): T? =
    getOriginalKotlinClass(objCClass)?.let {
        getDependency(it, driverId)
    }

private fun <T> Koin.getDependency(clazz: KClass<*>, vararg parameters: Any?): T {
    return get(clazz, null) { parametersOf(*parameters) } as T
}
