import Foundation
import SwiftUI
import shared

class DriverState: ObservableObject {
    let driverId: String
    let viewModel: DriverViewModel
    
    @Published private(set) var driver: DriverType? = nil
    @Published private(set) var seasons: Array<SeasonType>? = nil
    @Published private(set) var driverStanding: DriverStandingType? = nil
    @Published private(set) var selectedSeason: String? = nil
    @Published private(set) var raceResults: Array<RaceWithResultsType>? = nil
    
    init(driverId: String) {
        self.driverId = driverId
        viewModel = koin.get(with: driverId)
        viewModel.observeDriver { driverState in
            self.driver = driverState.driver
        }
        viewModel.observeSeasons { seasonsState in
            if let kotlinArr = seasonsState.seasons {
                self.seasons = Array(kotlinArr)
            }
        }
        viewModel.observeDriverStanding { driverStandingState in
            self.driverStanding = driverStandingState.driverStanding
        }
        viewModel.observeSelectedSeason { selectedSeason in
            self.selectedSeason = selectedSeason
        }
        viewModel.observeDriverSeasonRaceResults { driverSeasonRaceResultsState in
            if let kotlinArr = driverSeasonRaceResultsState.raceResults {
                self.raceResults = Array(kotlinArr)
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
