import SwiftUI
import shared

@MainActor
class CurrentStandingsViewModel: ObservableObject {
    @Published var constructorStandings: ConstructorStandingsType? = nil
    @Published var driverStandings: DriverStandingsType? = nil
    
    private let repository: StandingsRepository
    
    init(repository: StandingsRepository) {
        self.repository = repository
        
        fetchCurrentDriverStandings()
        fetchCurrentConstructorStandings()
    }
    
    func fetchCurrentConstructorStandings() {
        repository.fetchConstructorStandings(season: "current", round: "last", position: nil) { response, error in
            if let error = error {
                NapierNapier.shared.d(message: "\(error.localizedDescription)", throwable: nil, tag_: "\(self)")
            } else if let response = response, response.size > 0 {
                self.constructorStandings = response.get(index: 0)
            }
        }
    }
    
    func fetchCurrentDriverStandings() {
        repository.fetchDriverStandings(season: "current", round: "last", position: nil) { response, error in
            if let error = error {
                NapierNapier.shared.d(message: "\(error.localizedDescription)", throwable: nil, tag_: "\(self)")
            } else if let response = response, response.size > 0 {
                self.driverStandings = response.get(index: 0)
            }
        }
    }
}
