import SwiftUI
import shared

struct RaceResults: View {
    var raceResults: RaceWithResultsType? = nil
    
    @State private var selectedDriver: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            if let results = raceResults {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<results.results.size, id: \.self) { i in
                            if let result = results.results.get(index: i) {
                                Button {
                                    selectedDriver = result.driver.driverId
                                } label: {
                                    DriverResultRow(result: result)
                                }
                                .buttonStyle(RowButtonStyle(isDarkMode: colorScheme == .dark))
                                NavigationLink(
                                    destination: NavigationLazyView(DriverScreen(driverState: DriverState(driverId: result.driver.driverId))),
                                    tag: result.driver.driverId,
                                    selection: $selectedDriver
                                ) {
                                    EmptyView()
                                }
                                .makeInvisibleOnMacOS()
                            }
                        }
                    }
                }
                .fillMaxSize()
            } else {
                emptyBody
            }
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("TBD")
        }
        .fillMaxSize()
    }
}
