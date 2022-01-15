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
    
    init(driverId: String) {
        self.driverId = driverId
        viewModel = koin.get(with: driverId)
        NapierNapier.d(tag: "DriverState", viewModel.driverState.value, driverId, separator: "; ")
        viewModel.observeDriver { driverState in
            NapierNapier.d(tag: "DriverState", "driver", driverState.isFetching, self.viewModel.driverState.value, separator: "; ")
            self.driver = driverState.driver
        }
        viewModel.observeSeasons { seasonsState in
            NapierNapier.d(tag: "DriverState", "seasons", seasonsState.isFetching, separator: "; ")
            if let kotlinArr = seasonsState.seasons {
                self.seasons = Array(kotlinArr)
            }
        }
        viewModel.observeDriverStanding { driverStandingState in
            self.driverStanding = driverStandingState.driverStanding
        }
        viewModel.observeSelectedSeason { selectedSeason in
            NapierNapier.d(tag: "DriverState", "selectedSeason", selectedSeason, separator: "; ")
            self.selectedSeason = selectedSeason
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
