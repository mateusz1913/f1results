package dev.mateusz1913.f1results.di

import dev.mateusz1913.f1results.datasource.remote.createKtorClient
import dev.mateusz1913.f1results.datasource.remote.circuit.CircuitsApi
import dev.mateusz1913.f1results.datasource.remote.circuit.CircuitsApiImpl
import dev.mateusz1913.f1results.datasource.remote.constructor.ConstructorsApi
import dev.mateusz1913.f1results.datasource.remote.constructor.ConstructorsApiImpl
import dev.mateusz1913.f1results.datasource.remote.driver.DriversApi
import dev.mateusz1913.f1results.datasource.remote.driver.DriversApiImpl
import dev.mateusz1913.f1results.datasource.remote.finishing_status.FinishingStatusApi
import dev.mateusz1913.f1results.datasource.remote.finishing_status.FinishingStatusApiImpl
import dev.mateusz1913.f1results.datasource.remote.lap_times.LapTimesApi
import dev.mateusz1913.f1results.datasource.remote.lap_times.LapTimesApiImpl
import dev.mateusz1913.f1results.datasource.remote.pitstops.PitStopsApi
import dev.mateusz1913.f1results.datasource.remote.pitstops.PitStopsApiImpl
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApi
import dev.mateusz1913.f1results.datasource.remote.qualifying_results.QualifyingResultsApiImpl
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApi
import dev.mateusz1913.f1results.datasource.remote.race_results.RaceResultsApiImpl
import dev.mateusz1913.f1results.datasource.remote.race_schedule.RaceScheduleApi
import dev.mateusz1913.f1results.datasource.remote.race_schedule.RaceScheduleApiImpl
import dev.mateusz1913.f1results.datasource.remote.season_list.SeasonListApi
import dev.mateusz1913.f1results.datasource.remote.season_list.SeasonListApiImpl
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApi
import dev.mateusz1913.f1results.datasource.remote.standings.StandingsApiImpl
import dev.mateusz1913.f1results.datasource.repository.circuit.CircuitRepository
import dev.mateusz1913.f1results.datasource.repository.constructor.ConstructorRepository
import dev.mateusz1913.f1results.datasource.repository.driver.DriverRepository
import dev.mateusz1913.f1results.datasource.repository.finishing_status.FinishingStatusRepository
import dev.mateusz1913.f1results.datasource.repository.lap_times.LapTimesRepository
import dev.mateusz1913.f1results.datasource.repository.pitstops.PitStopsRepository
import dev.mateusz1913.f1results.datasource.repository.qualifying_results.QualifyingResultsRepository
import dev.mateusz1913.f1results.datasource.repository.race_results.RaceResultsRepository
import dev.mateusz1913.f1results.datasource.repository.race_schedule.RaceScheduleRepository
import dev.mateusz1913.f1results.datasource.repository.season_list.SeasonListRepository
import dev.mateusz1913.f1results.datasource.repository.standings.StandingsRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        initKoinAppDeclaration(this, appModule)
    }

    return koinApplication
}

fun initKoinAppDeclaration(koinApplication: KoinApplication, appModule: Module): KoinApplication {
    return koinApplication.modules(commonModule, appModule)
}

private val commonModule = module {
    single { createKtorClient() }
    single<CircuitsApi> { CircuitsApiImpl(get()) }
    single { CircuitRepository() }
    single<ConstructorsApi> { ConstructorsApiImpl(get()) }
    single { ConstructorRepository() }
    single<DriversApi> { DriversApiImpl(get()) }
    single { DriverRepository() }
    single<FinishingStatusApi> { FinishingStatusApiImpl(get()) }
    single { FinishingStatusRepository() }
    single<LapTimesApi> { LapTimesApiImpl(get()) }
    single { LapTimesRepository() }
    single<PitStopsApi> { PitStopsApiImpl(get()) }
    single { PitStopsRepository() }
    single<QualifyingResultsApi> { QualifyingResultsApiImpl(get()) }
    single { QualifyingResultsRepository() }
    single<RaceResultsApi> { RaceResultsApiImpl(get()) }
    single { RaceResultsRepository() }
    single<RaceScheduleApi> { RaceScheduleApiImpl(get()) }
    single { RaceScheduleRepository() }
    single<SeasonListApi> { SeasonListApiImpl(get()) }
    single { SeasonListRepository() }
    single<StandingsApi> { StandingsApiImpl(get()) }
    single { StandingsRepository() }
}