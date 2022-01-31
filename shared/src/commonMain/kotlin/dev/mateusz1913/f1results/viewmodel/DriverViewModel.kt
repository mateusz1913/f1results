package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.driver.DriverType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType
import dev.mateusz1913.f1results.datasource.repository.driver.DriverRepository
import dev.mateusz1913.f1results.datasource.repository.race_results.RaceResultsRepository
import dev.mateusz1913.f1results.datasource.repository.season_list.SeasonListRepository
import dev.mateusz1913.f1results.datasource.repository.standings.DriverStandingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DriverViewModel(
    private val driverRepository: DriverRepository,
    private val seasonListRepository: SeasonListRepository,
    private val driverStandingsRepository: DriverStandingsRepository,
    private val raceResultsRepository: RaceResultsRepository,
    private val driverId: String
) : BaseViewModel() {
    private val _driverState =
        MutableStateFlow(DriverState(driver = driverRepository.getCachedDriver(driverId)))
    val driverState: StateFlow<DriverState>
        get() = _driverState

    @Suppress("unused")
    fun observeDriver(onChange: (DriverState) -> Unit) {
        driverState.observe(onChange)
    }

    private val _seasonsState = MutableStateFlow(SeasonsState(seasons = seasonListRepository
        .getCachedDriverSeasonList(driverId)
        ?.sortedByDescending { it.season }
        ?.toTypedArray()))
    val seasonsState: StateFlow<SeasonsState>
        get() = _seasonsState

    @Suppress("unused")
    fun observeSeasons(onChange: (SeasonsState) -> Unit) {
        seasonsState.observe(onChange)
    }

    private val _driverStandingState = MutableStateFlow(DriverStandingState())
    val driverStandingState: StateFlow<DriverStandingState>
        get() = _driverStandingState

    @Suppress("unused")
    fun observeDriverStanding(onChange: (DriverStandingState) -> Unit) {
        driverStandingState.observe(onChange)
    }

    private val _selectedSeasonState = MutableStateFlow<String?>(null)
    val selectedSeasonState: StateFlow<String?>
        get() = _selectedSeasonState

    @Suppress("unused")
    fun observeSelectedSeason(onChange: (String?) -> Unit) {
        selectedSeasonState.observe(onChange)
    }

    private val _driverSeasonRaceResultsState = MutableStateFlow(DriverSeasonRaceResultsState())
    val driverSeasonRaceResultsState: StateFlow<DriverSeasonRaceResultsState>
        get() = _driverSeasonRaceResultsState

    @Suppress("unused")
    fun observeDriverSeasonRaceResults(onChange: (DriverSeasonRaceResultsState) -> Unit) {
        driverSeasonRaceResultsState.observe(onChange)
    }

    init {
        if (driverState.value.driver == null) {
            fetchDriver()
        }
        if (seasonsState.value.seasons == null) {
            fetchDriverSeasons()
        } else {
            fetchDriverStanding(seasonsState.value.seasons!![0].season)
        }
    }

    fun fetchDriver() {
        _driverState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val driver = driverRepository.fetchDriver(driverId)
            _driverState.update { it.copy(driver = driver, isFetching = false) }
        }
    }

    fun fetchDriverSeasons() {
        _seasonsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val seasonList = seasonListRepository.fetchDriverSeasonList(driverId)
            seasonList?.sortByDescending { it.season }
            _seasonsState.update { it.copy(seasons = seasonList, isFetching = false) }
            if (_selectedSeasonState.value == null && seasonList != null && seasonList.isNotEmpty()) {
                fetchDriverStanding(seasonList[0].season)
            }
        }
    }

    fun fetchDriverStanding(season: String) {
        coroutineScope.launch {
            internalFetchDriverStanding(season)
        }
    }

    private suspend fun fetchDriverSeasonResults(season: String) {
        val cached = raceResultsRepository.getCachedDriverSeasonRaceResult(season, driverId)
        if (cached != null) {
            _driverSeasonRaceResultsState.update { it.copy(raceResults = cached) }
            return
        }
        _driverSeasonRaceResultsState.update { it.copy(isFetching = true) }
        val raceResults = raceResultsRepository.fetchDriverSeasonRaceResultList(season, driverId)
        _driverSeasonRaceResultsState.update {
            it.copy(
                raceResults = raceResults,
                isFetching = false
            )
        }
    }

    private suspend fun internalFetchDriverStanding(season: String) {
        val driverStanding =
            driverStandingsRepository.getCachedSeasonDriverStanding(driverId, season)
                ?: driverStandingsRepository.fetchSeasonDriverStanding(driverId, season)
        _driverStandingState.update { it.copy(driverStanding = driverStanding) }
        _selectedSeasonState.value = season
        fetchDriverSeasonResults(season)
    }

    data class DriverState(
        val driver: DriverType? = null,
        val isFetching: Boolean = false
    )

    data class SeasonsState(
        val seasons: Array<SeasonType>? = null,
        val isFetching: Boolean = false
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as SeasonsState

            if (seasons != null) {
                if (other.seasons == null) return false
                if (!seasons.contentEquals(other.seasons)) return false
            } else if (other.seasons != null) return false
            if (isFetching != other.isFetching) return false

            return true
        }

        override fun hashCode(): Int {
            var result = seasons?.contentHashCode() ?: 0
            result = 31 * result + isFetching.hashCode()
            return result
        }
    }

    data class DriverStandingState(
        val driverStanding: DriverStandingType? = null,
    )

    data class DriverSeasonRaceResultsState(
        val raceResults: Array<RaceWithResultsType>? = null,
        val isFetching: Boolean = false
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as DriverSeasonRaceResultsState

            if (raceResults != null) {
                if (other.raceResults == null) return false
                if (!raceResults.contentEquals(other.raceResults)) return false
            } else if (other.raceResults != null) return false
            if (isFetching != other.isFetching) return false

            return true
        }

        override fun hashCode(): Int {
            var result = raceResults?.contentHashCode() ?: 0
            result = 31 * result + isFetching.hashCode()
            return result
        }
    }
}
