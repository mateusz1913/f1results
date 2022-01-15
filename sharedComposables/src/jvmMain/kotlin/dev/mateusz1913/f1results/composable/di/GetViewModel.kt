package dev.mateusz1913.f1results.composable.di

import androidx.compose.runtime.Composable
import dev.burnoo.cokoin.get
import dev.mateusz1913.f1results.viewmodel.BaseViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
actual inline fun <reified T: BaseViewModel> getViewModelInstance(
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?,
): T = get(qualifier, parameters)
