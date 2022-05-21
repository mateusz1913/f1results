import SwiftUI
import shared

struct DriverStandings: View {
    @EnvironmentObject var currentStandingsState: CurrentStandingsState
    
    @State private var selectedDriver: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        if currentStandingsState.driverStandings == nil {
            emptyBody
        }
        currentStandingsState.driverStandings.map { standings in
            ScrollView {
                LazyVStack {
                    ForEach(0..<standings.driverStandings.size, id: \.self) { i in
                        if let standing = standings.driverStandings.get(index: i) {
                            Button {
                                selectedDriver = standing.driver.driverId
                            } label: {
                                DriverStandingRow(driverStanding: standing)
                            }
                            .buttonStyle(RowButtonStyle(isDarkMode: colorScheme == .dark))
                            NavigationLink(
                                destination: NavigationLazyView(DriverScreen(driverState: DriverState(driverId: standing.driver.driverId))),
                                tag: standing.driver.driverId,
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
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("TBD")
        }
        .fillMaxSize()
    }
}
