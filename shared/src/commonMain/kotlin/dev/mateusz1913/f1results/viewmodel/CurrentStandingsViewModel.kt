package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType
import dev.mateusz1913.f1results.datasource.repository.standings.ConstructorStandingsRepository
import dev.mateusz1913.f1results.datasource.repository.standings.DriverStandingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CurrentStandingsViewModel(
    private val driverStandingsRepository: DriverStandingsRepository,
    private val constructorStandingsRepository: ConstructorStandingsRepository
) : BaseViewModel() {
    private val _driverStandingsState = MutableStateFlow(
        DriverStandingsState(
            driverStandings = driverStandingsRepository.getCachedLatestDriverStandings()
        )
    )
    val driverStandingsState: StateFlow<DriverStandingsState>
        get() = _driverStandingsState

    @Suppress("unused")
    fun observeDriverStandings(onChange: (DriverStandingsState) -> Unit) {
        driverStandingsState.observe(onChange)
    }

    private val _constructorStandingsState = MutableStateFlow(
        ConstructorStandingsState(
            constructorStandings = constructorStandingsRepository.getCachedLatestConstructorStandings()
        )
    )
    val constructorStandingsState: StateFlow<ConstructorStandingsState>
        get() = _constructorStandingsState

    @Suppress("unused")
    fun observeConstructorStandings(onChange: (ConstructorStandingsState) -> Unit) {
        constructorStandingsState.observe(onChange)
    }

    init {
        if (driverStandingsState.value.driverStandings == null) {
            fetchDriverStandings()
        }
        if (constructorStandingsState.value.constructorStandings == null) {
            fetchConstructorStandings()
        }
    }

    fun fetchDriverStandings(onFetched: ((DriverStandingsType?) -> Unit)? = null) {
        _driverStandingsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val driverStandings =
                driverStandingsRepository.fetchDriverStandings("current", "last")
            _driverStandingsState.update {
                it.copy(
                    driverStandings = driverStandings,
                    isFetching = false
                )
            }
            onFetched?.invoke(driverStandings)
        }
    }

    fun fetchConstructorStandings(onFetched: ((ConstructorStandingsType?) -> Unit)? = null) {
        _constructorStandingsState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val constructorStandings =
                constructorStandingsRepository.fetchConstructorStandings("current", "last")
            _constructorStandingsState.update {
                it.copy(
                    constructorStandings = constructorStandings,
                    isFetching = false
                )
            }
            onFetched?.invoke(constructorStandings)
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
