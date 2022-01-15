package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType
import dev.mateusz1913.f1results.datasource.repository.standings.StandingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CurrentStandingsViewModel(
    private val standingsRepository: StandingsRepository
) : BaseViewModel() {
    private val _driverStandingsState = MutableStateFlow(DriverStandingsState())
    val driverStandingsState: StateFlow<DriverStandingsState>
        get() = _driverStandingsState
    @Suppress("unused")
    fun observeDriverStandings(onChange: (DriverStandingsState) -> Unit) {
        driverStandingsState.observe(onChange)
    }

    private val _constructorStandingsState = MutableStateFlow(ConstructorStandingsState())
    val constructorStandingsState: StateFlow<ConstructorStandingsState>
        get() = _constructorStandingsState
    @Suppress("unused")
    fun observeConstructorStandings(onChange: (ConstructorStandingsState) -> Unit) {
        constructorStandingsState.observe(onChange)
    }

    init {
        fetchDriverStandings()
        fetchConstructorStandings()
    }

    fun fetchDriverStandings() {
        _driverStandingsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val driverStandings =
                standingsRepository.fetchDriverStandings("current", "last")?.get(0)
            _driverStandingsState.update {
                it.copy(
                    driverStandings = driverStandings,
                    isFetching = false
                )
            }
        }
    }

    fun fetchConstructorStandings() {
        _constructorStandingsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val constructorStandings =
                standingsRepository.fetchConstructorStandings("current", "last")?.get(0)
            _constructorStandingsState.update {
                it.copy(
                    constructorStandings = constructorStandings,
                    isFetching = false
                )
            }
        }
    }

    data class DriverStandingsState(
        val driverStandings: DriverStandingsType? = null,
        val isFetching: Boolean = false
    )

    data class ConstructorStandingsState(
        val constructorStandings: ConstructorStandingsType? = null,
        val isFetching: Boolean = false
    )
}
