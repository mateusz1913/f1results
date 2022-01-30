import SwiftUI
import shared

struct RaceResults: View {
    var raceResults: RaceWithResultsType? = nil
    
    @State private var selectedDriver: String?
    
    var body: some View {
        VStack {
            if let results = raceResults {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<results.results.size, id: \.self) { i in
                            if let result = results.results.get(index: i) {
                                let text = "\(result.positionText): \(result.driver.givenName) \(result.driver.familyName) - \(result.constructor.name)"
                                NavigationLink(
                                    destination: NavigationLazyView(DriverScreen(driverState: DriverState(driverId: result.driver.driverId))),
                                    tag: result.driver.driverId,
                                    selection: $selectedDriver
                                ) {
                                    Text(text)
                                        .padding(4)
                                        .frame(maxWidth: .infinity, alignment: .leading)
                                        .border(.orange, width: 1)
                                }
                            }
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
    }
}
