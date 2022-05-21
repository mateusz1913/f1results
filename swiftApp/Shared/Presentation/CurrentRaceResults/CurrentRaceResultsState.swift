import Foundation
import SwiftUI
import shared

class CurrentRaceResultsState: ObservableObject {
    let viewModel: CurrentRaceResultsViewModel = koin.get()
    
    @Published private(set) var raceResults: RaceWithResultsType? = nil
    @Published private(set) var qualifyingResults: RaceWithQualifyingResultsType? = nil
    
    init() {
        viewModel.observeRaceResults { raceResultsState in
            self.raceResults = raceResultsState.raceResults
        }
        viewModel.observeQualifyingResults { qualifyingResultsState in
            self.qualifyingResults = qualifyingResultsState.qualifyingResults
        }
    }
    
    func fetchCurrentRaceResults() async {
        await withCheckedContinuation({ continuation in
            viewModel.fetchCurrentRaceResults { raceResults in
                continuation.resume()
            }
        })
    }
    
    func fetchCurrentQualifyingResults() async {
        await withCheckedContinuation({ continuation in
            viewModel.fetchCurrentQualifyingResults { qualifyingResults in
                continuation.resume()
            }
        })
    }
    
    deinit {
        viewModel.dispose()
    }
}
