import SwiftUI
import shared

struct QualifyingResults: View {
    var qualifyingResults: RaceWithQualifyingResultsType? = nil
    
    @State private var selectedDriver: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            if let results = qualifyingResults {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<results.qualifyingResults.size, id: \.self) { i in
                            if let result = results.qualifyingResults.get(index: i) {
                                Button {
                                    selectedDriver = result.driver.driverId
                                } label: {
                                    DriverQualifyingRow(result: result)
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
