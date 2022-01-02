package dev.mateusz1913.f1results.android.presentation.current_standings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType
import dev.mateusz1913.f1results.datasource.repository.standings.StandingsRepository
import kotlinx.coroutines.launch

class CurrentStandingsViewModel(
    private val standingsRepository: StandingsRepository
): ViewModel() {
    var driverStandings by mutableStateOf<DriverStandingsType?>(null)
        private set
    var constructorStandings by mutableStateOf<ConstructorStandingsType?>(null)
        private set

    var isFetchingDriverStandings by mutableStateOf(false)
        private set
    var isFetchingConstructorStandings by mutableStateOf(false)
        private set

    init {
        fetchDriverStandings()
        fetchConstructorStandings()
    }

    fun fetchDriverStandings() {
        isFetchingDriverStandings = true
        viewModelScope.launch {
            driverStandings = standingsRepository.fetchDriverStandings("current", "last")?.get(0)
            isFetchingDriverStandings = false
        }
    }

    fun fetchConstructorStandings() {
        isFetchingConstructorStandings = true
        viewModelScope.launch {
            constructorStandings = standingsRepository.fetchConstructorStandings("current", "last")?.get(0)
            isFetchingConstructorStandings = false
        }
    }
}