package dev.mateusz1913.f1results.di

import dev.mateusz1913.f1results.viewmodel.BaseViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T: BaseViewModel> Module.viewModelFactory(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier, definition = definition)
}
