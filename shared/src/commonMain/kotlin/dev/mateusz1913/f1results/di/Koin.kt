package dev.mateusz1913.f1results.di

import dev.mateusz1913.f1results.datasource.cache.F1Database
import dev.mateusz1913.f1results.datasource.cache.F1DatabaseFactory
import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCache
import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCacheImpl
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
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import kotlin.reflect.KClass

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        initKoinAppDeclaration(this)
    }

    return koinApplication
}

fun initKoinAppDeclaration(koinApplication: KoinApplication): KoinApplication {
    return koinApplication.modules(cacheModule, dataSourceModule)
}

private val cacheModule = module {
    single { F1DatabaseFactory(getDriverFactory(this)).createDatabase() }
    single<CircuitCache> { CircuitCacheImpl(get<F1Database>().circuitQueries) }
}

private val dataSourceModule = module {
    single { createKtorClient() }
    single<CircuitsApi> { CircuitsApiImpl(get()) }
    single { CircuitRepository(get(), get()) }
    single<ConstructorsApi> { ConstructorsApiImpl(get()) }
    single { ConstructorRepository(get()) }
    single<DriversApi> { DriversApiImpl(get()) }
    single { DriverRepository(get()) }
    single<FinishingStatusApi> { FinishingStatusApiImpl(get()) }
    single { FinishingStatusRepository(get()) }
    single<LapTimesApi> { LapTimesApiImpl(get()) }
    single { LapTimesRepository(get()) }
    single<PitStopsApi> { PitStopsApiImpl(get()) }
    single { PitStopsRepository(get()) }
    single<QualifyingResultsApi> { QualifyingResultsApiImpl(get()) }
    single { QualifyingResultsRepository(get()) }
    single<RaceResultsApi> { RaceResultsApiImpl(get()) }
    single { RaceResultsRepository(get()) }
    single<RaceScheduleApi> { RaceScheduleApiImpl(get()) }
    single { RaceScheduleRepository(get()) }
    single<SeasonListApi> { SeasonListApiImpl(get()) }
    single { SeasonListRepository(get()) }
    single<StandingsApi> { StandingsApiImpl(get()) }
    single { StandingsRepository(get()) }
}

fun <T>Koin.getDependency(clazz: KClass<*>): T {
    return get(clazz, null) { parametersOf(clazz.simpleName) } as T
}
