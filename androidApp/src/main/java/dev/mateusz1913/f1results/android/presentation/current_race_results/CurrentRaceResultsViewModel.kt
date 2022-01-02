package dev.mateusz1913.f1results.android.presentation.current_race_results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType
import dev.mateusz1913.f1results.datasource.repository.qualifying_results.QualifyingResultsRepository
import dev.mateusz1913.f1results.datasource.repository.race_results.RaceResultsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class CurrentRaceResultsViewModel(
    private val raceResultsRepository: RaceResultsRepository,
    private val qualifyingResultsRepository: QualifyingResultsRepository,
): ViewModel() {
    var qualifyingResults by mutableStateOf<RaceWithQualifyingResultsType?>(null)
        private set
    var raceResults by mutableStateOf<RaceWithResultsType?>(null)
        private set

    var isFetchingQualifyingResults by mutableStateOf(false)
        private set
    var isFetchingRaceResults by mutableStateOf(false)
        private set

    init {
        fetchCurrentRaceResults()
        fetchCurrentQualifyingResults()
    }

    fun fetchCurrentRaceResults() {
        isFetchingRaceResults = true
        viewModelScope.launch {
            raceResults = raceResultsRepository.fetchRaceResult(season = "current", round = "last")
            isFetchingRaceResults = false
        }
    }

    fun fetchCurrentQualifyingResults() {
        isFetchingQualifyingResults = true
        viewModelScope.launch {
            qualifyingResults = qualifyingResultsRepository.fetchQualifyingResult(season = "current", round = "last")
            isFetchingQualifyingResults = false
        }
    }
}