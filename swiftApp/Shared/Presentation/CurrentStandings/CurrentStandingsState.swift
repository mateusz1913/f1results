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
    
    func fetchCurrentDriverStandings() async {
        await withCheckedContinuation({ continuation in
            viewModel.fetchDriverStandings { driverStandings in
                continuation.resume()
            }
        })
    }
    
    func fetchCurrentConstructorStandings() async {
        await withCheckedContinuation({ continuation in
            viewModel.fetchConstructorStandings { constructorStandings in
                continuation.resume()
            }
        })
    }
    
    deinit {
        viewModel.dispose()
    }
}
