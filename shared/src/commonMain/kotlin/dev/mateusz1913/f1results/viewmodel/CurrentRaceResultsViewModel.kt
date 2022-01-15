package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.repository.qualifying_results.QualifyingResultsRepository
import dev.mateusz1913.f1results.datasource.repository.race_results.RaceResultsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CurrentRaceResultsViewModel(
    private val raceResultsRepository: RaceResultsRepository,
    private val qualifyingResultsRepository: QualifyingResultsRepository,
) : BaseViewModel() {
    private val _raceResultsState = MutableStateFlow(RaceResultsState())
    val raceResultsState: StateFlow<RaceResultsState>
        get() = _raceResultsState
    @Suppress("unused")
    fun observeRaceResults(onChange: (RaceResultsState) -> Unit) {
        raceResultsState.observe(onChange)
    }

    private val _qualifyingResultsState = MutableStateFlow(QualifyingResultsState())
    val qualifyingResultsState: StateFlow<QualifyingResultsState>
        get() = _qualifyingResultsState
    @Suppress("unused")
    fun observeQualifyingResults(onChange: (QualifyingResultsState) -> Unit) {
        qualifyingResultsState.observe(onChange)
    }

    init {
        fetchCurrentRaceResults()
        fetchCurrentQualifyingResults()
    }

    fun fetchCurrentRaceResults() {
        _raceResultsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val raceResults =
                raceResultsRepository.fetchRaceResult(season = "current", round = "last")
            _raceResultsState.update { it.copy(raceResults = raceResults, isFetching = false) }
        }
    }

    fun fetchCurrentQualifyingResults() {
        _qualifyingResultsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val qualifyingResults = qualifyingResultsRepository.fetchQualifyingResult(
                season = "current",
                round = "last"
            )
            _qualifyingResultsState.update {
                it.copy(
                    qualifyingResults = qualifyingResults,
                    isFetching = false
                )
            }
        }
    }

    data class RaceResultsState(
        val raceResults: RaceWithResultsType? = null,
        val isFetching: Boolean = false
    )

    data class QualifyingResultsState(
        val qualifyingResults: RaceWithQualifyingResultsType? = null,
        val isFetching: Boolean = false
    )
}
