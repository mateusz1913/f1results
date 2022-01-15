import Foundation
import SwiftUI
import shared

class CurrentStandingsState: ObservableObject {
    let viewModel: CurrentStandingsViewModel = koin.get()
    
    @Published private(set) var driverStandings: DriverStandingsType? = nil
    @Published private(set) var constructorStandings: ConstructorStandingsType? = nil
    
    init() {
        viewModel.observeDriverStandings { driverStandingsState in
            self.driverStandings = driverStandingsState.driverStandings
        }
        viewModel.observeConstructorStandings { constructorStandingsState in
            self.constructorStandings = constructorStandingsState.constructorStandings
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
