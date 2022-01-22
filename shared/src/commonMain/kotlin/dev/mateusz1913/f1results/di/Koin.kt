package dev.mateusz1913.f1results.di

import dev.mateusz1913.f1results.datasource.cache.F1Database
import dev.mateusz1913.f1results.datasource.cache.F1DatabaseFactory
import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCache
import dev.mateusz1913.f1results.datasource.cache.circuit.CircuitCacheImpl
import dev.mateusz1913.f1results.datasource.cache.constructor.ConstructorCache
import dev.mateusz1913.f1results.datasource.cache.constructor.ConstructorCacheImpl
import dev.mateusz1913.f1results.datasource.cache.driver.DriverCache
import dev.mateusz1913.f1results.datasource.cache.driver.DriverCacheImpl
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.QualifyingResultsCache
import dev.mateusz1913.f1results.datasource.cache.qualifying_results.QualifyingResultsCacheImpl
import dev.mateusz1913.f1results.datasource.cache.race_results.RaceResultsCache
import dev.mateusz1913.f1results.datasource.cache.race_results.RaceResultsCacheImpl
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCache
import dev.mateusz1913.f1results.datasource.cache.race_schedule.RaceScheduleCacheImpl
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCache
import dev.mateusz1913.f1results.datasource.cache.requests_timestamps.RequestsTimestampsCacheImpl
import dev.mateusz1913.f1results.datasource.cache.season_list.SeasonCache
import dev.mateusz1913.f1results.datasource.cache.season_list.SeasonCacheImpl
import dev.mateusz1913.f1results.datasource.cache.standings.ConstructorStandingsCache
import dev.mateusz1913.f1results.datasource.cache.standings.ConstructorStandingsCacheImpl
import dev.mateusz1913.f1results.datasource.cache.standings.DriverStandingsCacheImpl
import dev.mateusz1913.f1results.datasource.cache.standings.DriverStandingsCache
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
import dev.mateusz1913.f1results.datasource.repository.standings.ConstructorStandingsRepository
import dev.mateusz1913.f1results.datasource.repository.standings.DriverStandingsRepository
import dev.mateusz1913.f1results.viewmodel.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        initKoinAppDeclaration(this)
    }

    return koinApplication
}

fun initKoinAppDeclaration(koinApplication: KoinApplication): KoinApplication {
    return koinApplication.modules(cacheModule, networkModule, viewModelModule)
}

private val cacheModule = module {
    single<F1Database> { F1DatabaseFactory(getDriverFactory(this)).createDatabase() }
    single<CircuitCache> { CircuitCacheImpl(get<F1Database>().circuitQueries) }
    single<ConstructorCache> { ConstructorCacheImpl(get<F1Database>().constructorQueries) }
    single<DriverCache> { DriverCacheImpl(get<F1Database>().driverQueries) }
    single<QualifyingResultsCache> {
        QualifyingResultsCacheImpl(
            get<F1Database>().qualifyingResultsQueries,
            get<F1Database>().raceQueries,
            get<F1Database>().circuitQueries,
            get<F1Database>().driverQueries,
            get<F1Database>().constructorQueries
        )
    }
    single<RaceResultsCache> {
        RaceResultsCacheImpl(
            get<F1Database>().raceResultsQueries,
            get<F1Database>().raceQueries,
            get<F1Database>().circuitQueries,
            get<F1Database>().driverQueries,
            get<F1Database>().constructorQueries
        )
    }
    single<RaceScheduleCache> {
        RaceScheduleCacheImpl(
            get<F1Database>().raceQueries,
            get<F1Database>().circuitQueries
        )
    }
    single<SeasonCache> { SeasonCacheImpl(get<F1Database>().seasonQueries) }
    single<ConstructorStandingsCache> {
        ConstructorStandingsCacheImpl(
            get<F1Database>().constructorStandingsQueries,
            get<F1Database>().constructorQueries
        )
    }
    single<DriverStandingsCache> {
        DriverStandingsCacheImpl(
            get<F1Database>().driverStandingsQueries,
            get<F1Database>().constructorQueries,
            get<F1Database>().driverQueries
        )
    }
    single<RequestsTimestampsCache> { RequestsTimestampsCacheImpl(get<F1Database>().requestsTimestampsQueries) }
}

private val networkModule = module {
    single { createKtorClient() }
    single<CircuitsApi> { CircuitsApiImpl(get()) }
    single { CircuitRepository(get(), get(), get()) }
    single<ConstructorsApi> { ConstructorsApiImpl(get()) }
    single { ConstructorRepository(get(), get(), get()) }
    single<DriversApi> { DriversApiImpl(get()) }
    single { DriverRepository(get(), get(), get()) }
    single<FinishingStatusApi> { FinishingStatusApiImpl(get()) }
    single { FinishingStatusRepository(get()) }
    single<LapTimesApi> { LapTimesApiImpl(get()) }
    single { LapTimesRepository(get()) }
    single<PitStopsApi> { PitStopsApiImpl(get()) }
    single { PitStopsRepository(get()) }
    single<QualifyingResultsApi> { QualifyingResultsApiImpl(get()) }
    single { QualifyingResultsRepository(get(), get(), get()) }
    single<RaceResultsApi> { RaceResultsApiImpl(get()) }
    single { RaceResultsRepository(get(), get(), get()) }
    single<RaceScheduleApi> { RaceScheduleApiImpl(get()) }
    single { RaceScheduleRepository(get(), get(), get()) }
    single<SeasonListApi> { SeasonListApiImpl(get()) }
    single { SeasonListRepository(get(), get(), get()) }
    single<StandingsApi> { StandingsApiImpl(get()) }
    single { ConstructorStandingsRepository(get(), get(), get()) }
    single { DriverStandingsRepository(get(), get(), get()) }
}

private val viewModelModule = module {
    viewModelFactory { CurrentCalendarViewModel(get()) }
    viewModelFactory { CurrentRaceResultsViewModel(get(), get()) }
    viewModelFactory { CurrentStandingsViewModel(get(), get()) }
    viewModelFactory { CircuitViewModel(get(), get()) }
    viewModelFactory { ConstructorViewModel(get(), get(), get(), get()) }
    viewModelFactory { DriverViewModel(get(), get(), get(), get()) }
}
