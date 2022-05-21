package dev.mateusz1913.f1results.viewmodel

import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType
import dev.mateusz1913.f1results.datasource.repository.race_schedule.RaceScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrentCalendarViewModel(
    private val raceScheduleRepository: RaceScheduleRepository,
) : BaseViewModel() {
    private val _currentRaceScheduleState = MutableStateFlow(
        CurrentRaceScheduleState(
            currentRaceScheduleList = raceScheduleRepository.getCachedRaceScheduleListFromCurrentSeason()
        )
    )
    val currentRaceScheduleState: StateFlow<CurrentRaceScheduleState>
        get() = _currentRaceScheduleState
    @Suppress("unused")
    fun observeCurrentRaceSchedule(onChange: (CurrentRaceScheduleState) -> Unit) {
        currentRaceScheduleState.observe(onChange)
    }

    init {
        if (currentRaceScheduleState.value.currentRaceScheduleList == null) {
            fetchCurrentRaceSchedule()
        }
    }

    fun fetchCurrentRaceSchedule(onFetched: ((Array<RaceType>?) -> Unit)? = null) {
        _currentRaceScheduleState.update { it.copy(isFetching = true) }
        coroutineScope.launch {
            val raceScheduleList = raceScheduleRepository.fetchRaceScheduleListWithSeason("current")
            _currentRaceScheduleState.update {
                it.copy(
                    currentRaceScheduleList = raceScheduleList,
                    isFetching = false
                )
            }
            onFetched?.invoke(raceScheduleList)
        }
    }

    data class CurrentRaceScheduleState(
        val currentRaceScheduleList: Array<RaceType>? = null,
        val isFetching: Boolean = false
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as CurrentRaceScheduleState

            if (currentRaceScheduleList != null) {
                if (other.currentRaceScheduleList == null) return false
                if (!currentRaceScheduleList.contentEquals(other.currentRaceScheduleList)) return false
            } else if (other.currentRaceScheduleList != null) return false
            if (isFetching != other.isFetching) return false

            return true
        }

        override fun hashCode(): Int {
            var result = currentRaceScheduleList?.contentHashCode() ?: 0
            result = 31 * result + isFetching.hashCode()
            return result
        }
    }
}