package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.constructor.ConstructorType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.datasource.repository.constructor.ConstructorRepository
import dev.mateusz1913.f1results.datasource.repository.race_results.RaceResultsRepository
import dev.mateusz1913.f1results.datasource.repository.season_list.SeasonListRepository
import dev.mateusz1913.f1results.datasource.repository.standings.ConstructorStandingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConstructorViewModel(
    private val constructorRepository: ConstructorRepository,
    private val seasonListRepository: SeasonListRepository,
    private val constructorStandingsRepository: ConstructorStandingsRepository,
    private val raceResultsRepository: RaceResultsRepository,
    private val constructorId: String
) : BaseViewModel() {
    private val _constructorState = MutableStateFlow(
        ConstructorState(
            constructor = constructorRepository.getCachedConstructor(constructorId)
        )
    )
    val constructorState: StateFlow<ConstructorState>
        get() = _constructorState

    @Suppress("unused")
    fun observeConstructor(onChange: (ConstructorState) -> Unit) {
        constructorState.observe(onChange)
    }

    private val _seasonsState = MutableStateFlow(
        SeasonsState(
            seasons = seasonListRepository.getCachedConstructorSeasonList(constructorId)
                ?.sortedByDescending { it.season }
                ?.toTypedArray()
        )
    )
    val seasonsState: StateFlow<SeasonsState>
        get() = _seasonsState

    @Suppress("unused")
    fun observeSeasons(onChange: (SeasonsState) -> Unit) {
        seasonsState.observe(onChange)
    }

    private val _constructorStandingState = MutableStateFlow(ConstructorStandingState())
    val constructorStandingState: StateFlow<ConstructorStandingState>
        get() = _constructorStandingState

    @Suppress("unused")
    fun observeConstructorStanding(onChange: (ConstructorStandingState) -> Unit) {
        constructorStandingState.observe(onChange)
    }

    private val _selectedSeasonState = MutableStateFlow<String?>(null)
    val selectedSeasonState: StateFlow<String?>
        get() = _selectedSeasonState

    @Suppress("unused")
    fun observeSelectedSeason(onChange: (String?) -> Unit) {
        selectedSeasonState.observe(onChange)
    }

    private val _constructorSeasonRaceResultsState =
        MutableStateFlow(ConstructorSeasonRaceResultsState())
    val constructorSeasonRaceResultsState: StateFlow<ConstructorSeasonRaceResultsState>
        get() = _constructorSeasonRaceResultsState

    @Suppress("unused")
    fun observeConstructorSeasonRaceResults(onChange: (ConstructorSeasonRaceResultsState) -> Unit) {
        constructorSeasonRaceResultsState.observe(onChange)
    }

    init {
        if (constructorState.value.constructor == null) {
            fetchConstructor()
        }
        if (seasonsState.value.seasons == null) {
            fetchConstructorSeasons()
        } else if (seasonsState.value.seasons!!.isNotEmpty()) {
            fetchConstructorStanding(seasonsState.value.seasons!![0].season)
        }
    }

    fun fetchConstructor() {
        _constructorState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val constructor = constructorRepository.fetchConstructor(constructorId)
            _constructorState.update { it.copy(constructor = constructor, isFetching = false) }
        }
    }

    fun fetchConstructorSeasons() {
        _seasonsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val seasonList = seasonListRepository.fetchConstructorSeasonList(constructorId)
            seasonList?.sortByDescending { it.season }
            _seasonsState.update { it.copy(seasons = seasonList, isFetching = false) }
            if (_selectedSeasonState.value == null && seasonList != null && seasonList.isNotEmpty()) {
                internalFetchConstructorStanding(seasonList[0].season)
            }
        }
    }

    fun fetchConstructorStanding(season: String) {
        coroutineScope.launch {
            internalFetchConstructorStanding(season)
        }
    }

    private suspend fun fetchConstructorSeasonRaceResults(season: String) {
        val cached =
            raceResultsRepository.getCachedConstructorSeasonRaceResult(season, constructorId)
        if (cached != null) {
            _constructorSeasonRaceResultsState.update { it.copy(raceResults = cached) }
            return
        }
        _constructorSeasonRaceResultsState.update { it.copy(isFetching = true) }
        val raceResults =
            raceResultsRepository.fetchConstructorSeasonRaceResultList(season, constructorId)
        _constructorSeasonRaceResultsState.update {
            it.copy(
                raceResults = raceResults,
                isFetching = false
            )
        }
    }

    private suspend fun internalFetchConstructorStanding(season: String) {
        val constructorStanding =
            constructorStandingsRepository.getCachedSeasonConstructorStanding(constructorId, season)
                ?: constructorStandingsRepository.fetchSeasonConstructorStanding(
                    constructorId,
                    season
                )
        _constructorStandingState.update { it.copy(constructorStanding = constructorStanding) }
        _selectedSeasonState.value = season
        fetchConstructorSeasonRaceResults(season)
    }

    data class ConstructorState(
        val constructor: ConstructorType? = null,
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

    data class ConstructorStandingState(
        val constructorStanding: ConstructorStandingType? = null
    )

    data class ConstructorSeasonRaceResultsState(
        val raceResults: Array<RaceWithResultsType>? = null,
        val isFetching: Boolean = false
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as ConstructorSeasonRaceResultsState

            if (raceResults != null) {
                if (other.raceResults == null) return false
                if (!raceResults.contentEquals(other.raceResults)) return false
            } else if (other.raceResults != null) return false
            if (isFetching != other.isFetching) return false

            return true
        }

        override fun hashCode(): Int {
            return raceResults?.contentHashCode() ?: 0
        }
    }
}