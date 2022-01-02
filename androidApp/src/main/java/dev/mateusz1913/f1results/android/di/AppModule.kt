package dev.mateusz1913.f1results.android.di

import dev.mateusz1913.f1results.android.presentation.current_race_results.CurrentRaceResultsViewModel
import dev.mateusz1913.f1results.android.presentation.current_standings.CurrentStandingsViewModel
import dev.mateusz1913.f1results.di.AppInfo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppInfo> { AndroidAppInfo }
    viewModel { CurrentRaceResultsViewModel(get(), get()) }
    viewModel { CurrentStandingsViewModel(get()) }
}