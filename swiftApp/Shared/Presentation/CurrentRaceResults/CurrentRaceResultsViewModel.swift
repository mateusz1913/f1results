import SwiftUI
import shared

@MainActor
class CurrentRaceResultsViewModel: ObservableObject {
    @Published var qualifyingResults: RaceWithQualifyingResultsType? = nil
    @Published var raceResults: RaceWithResultsType? = nil
    
    private let qualifyingResultsRepository: QualifyingResultsRepository
    private let raceResultsRepository: RaceResultsRepository
    
    init(raceResultsRepository: RaceResultsRepository, qualifyingResultsRepository: QualifyingResultsRepository) {
        self.raceResultsRepository = raceResultsRepository
        self.qualifyingResultsRepository = qualifyingResultsRepository
        
        fetchCurrentRaceResults()
        fetchCurrentQualificationResults()
    }
    
    func fetchCurrentRaceResults() {
        raceResultsRepository.fetchRaceResult(season: "current", round: "last", position: nil) { response, error in
            if let error = error {
                NapierNapier.shared.d(message: "\(error.localizedDescription)", throwable: nil, tag_: "\(self)")
            } else if let response = response {
                self.raceResults = response
            }
        }
    }
    
    func fetchCurrentQualificationResults() {
        qualifyingResultsRepository.fetchQualifyingResult(season: "current", round: "last", position: nil) { response, error in
            if let error = error {
                NapierNapier.shared.d(message: "\(error.localizedDescription)", throwable: nil, tag_: "\(self)")
            } else if let response = response {
                self.qualifyingResults = response
            }
        }
    }
}
