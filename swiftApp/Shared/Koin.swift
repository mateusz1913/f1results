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
    func get() -> RaceResultsRepository {
        return koin.getDependency(objCClass: RaceResultsRepository.self) as! RaceResultsRepository
    }
    
    func get() -> QualifyingResultsRepository {
        return koin.getDependency(objCClass: QualifyingResultsRepository.self) as! QualifyingResultsRepository
    }
    
    func get() -> StandingsRepository {
        return koin.getDependency(objCClass: StandingsRepository.self) as! StandingsRepository
    }
}
