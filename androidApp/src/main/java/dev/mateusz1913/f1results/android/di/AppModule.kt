package dev.mateusz1913.f1results.android.di

import dev.mateusz1913.f1results.android.presentation.circuit.CircuitViewModel
import dev.mateusz1913.f1results.android.presentation.current_race_results.CurrentRaceResultsViewModel
import dev.mateusz1913.f1results.android.presentation.current_standings.CurrentStandingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CurrentRaceResultsViewModel(get(), get()) }
    viewModel { CurrentStandingsViewModel(get()) }
    viewModel { CircuitViewModel(get(), get()) }
}