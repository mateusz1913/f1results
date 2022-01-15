import Foundation
import shared

func startKoin() {
    let koinApplication = KoinSwiftKt.doInitKoinSwift()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}

extension Koin_coreKoin {
    func get() -> CircuitRepository {
        return koin.getDependency(objCClass: CircuitRepository.self) as! CircuitRepository
    }
    
    func get() -> ConstructorRepository {
        return koin.getDependency(objCClass: ConstructorRepository.self) as! ConstructorRepository
    }
    
    func get() -> DriverRepository {
        return koin.getDependency(objCClass: DriverRepository.self) as! DriverRepository
    }
    
    func get() -> FinishingStatusRepository {
        return koin.getDependency(objCClass: FinishingStatusRepository.self) as! FinishingStatusRepository
    }
    
    func get() -> LapTimesRepository {
        return koin.getDependency(objCClass: LapTimesRepository.self) as! LapTimesRepository
    }
    
    func get() -> PitStopsRepository {
        return koin.getDependency(objCClass: PitStopsRepository.self) as! PitStopsRepository
    }
    
    func get() -> QualifyingResultsRepository {
        return koin.getDependency(objCClass: QualifyingResultsRepository.self) as! QualifyingResultsRepository
    }
    
    func get() -> RaceResultsRepository {
        return koin.getDependency(objCClass: RaceResultsRepository.self) as! RaceResultsRepository
    }
    
    func get() -> RaceScheduleRepository {
        return koin.getDependency(objCClass: RaceScheduleRepository.self) as! RaceScheduleRepository
    }
    
    func get() -> SeasonListRepository {
        return koin.getDependency(objCClass: SeasonListRepository.self) as! SeasonListRepository
    }
    
    func get() -> StandingsRepository {
        return koin.getDependency(objCClass: StandingsRepository.self) as! StandingsRepository
    }
    
    func get() -> CurrentCalendarViewModel {
        return koin.getDependency(objCClass: CurrentCalendarViewModel.self) as! CurrentCalendarViewModel
    }
    
    func get() -> CurrentRaceResultsViewModel {
        return koin.getDependency(objCClass: CurrentRaceResultsViewModel.self) as! CurrentRaceResultsViewModel
    }
    
    func get() -> CurrentStandingsViewModel {
        return koin.getDependency(objCClass: CurrentStandingsViewModel.self) as! CurrentStandingsViewModel
    }
    
    func get(with circuitId: String) -> CircuitViewModel {
        return koin.getCircuitViewModel(objCClass: CircuitViewModel.self, circuitId: circuitId) as! CircuitViewModel
    }
    
    func get(with driverId: String) -> DriverViewModel {
        return koin.getDriverViewModel(objCClass: DriverViewModel.self, driverId: driverId) as! DriverViewModel
    }
}
