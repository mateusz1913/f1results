import Foundation
import SwiftUI
import shared

class ConstructorState: ObservableObject {
    let constructorId: String
    let viewModel: ConstructorViewModel
    
    @Published private(set) var constructor: ConstructorType? = nil
    @Published private(set) var seasons: Array<SeasonType>? = nil
    @Published private(set) var constructorStanding: ConstructorStandingType? = nil
    @Published private(set) var selectedSeason: String? = nil
    @Published private(set) var raceResults: Array<RaceWithResultsType>? = nil
    
    init(constructorId: String) {
        self.constructorId = constructorId
        viewModel = koin.get(with: constructorId)
        viewModel.observeConstructor { constructorState in
            self.constructor = constructorState.constructor
        }
        viewModel.observeSeasons { seasonsState in
            if let kotlinArr = seasonsState.seasons {
                self.seasons = Array(kotlinArr)
            }
        }
        viewModel.observeConstructorStanding { constructorStandingState in
            self.constructorStanding = constructorStandingState.constructorStanding
        }
        viewModel.observeSelectedSeason { selectedSeason in
            self.selectedSeason = selectedSeason
        }
        viewModel.observeConstructorSeasonRaceResults { constructorSeasonRaceResultsState in
            if let kotlinArr = constructorSeasonRaceResultsState.raceResults {
                self.raceResults = Array(kotlinArr)
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
