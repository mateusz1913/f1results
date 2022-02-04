import SwiftUI
import shared

struct DriverSeasonResultRow: View {
    var driverResult: ResultType
    var noDriverInfo: Bool = false
    
    @Binding var selectedDriver: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            let driverStartingPosition = driverResult.grid == "0" ? "Pit lane" : driverResult.grid
            HStack {
                InfoRow(fontSize: 14, fontWeight: .medium, label: "Started: ", value: driverStartingPosition)
                    .padding(.vertical, 5)
                Spacer()
            }
            if noDriverInfo {
                DriverResultRow(result: driverResult, noDriverInfo: noDriverInfo)
            } else {
                Button {
                    selectedDriver = driverResult.driver.driverId
                } label: {
                    DriverResultRow(result: driverResult)
                }
                .buttonStyle(BorderlessRowButtonStyle(isDarkMode: colorScheme == .dark))
                NavigationLink(
                    destination: NavigationLazyView(
                        DriverScreen(driverState: DriverState(driverId: driverResult.driver.driverId))
                        #if os(macOS)
                            .frame(minWidth: 500)
                        #endif
                    ),
                    tag: driverResult.driver.driverId,
                    selection: $selectedDriver
                ) {
                    EmptyView()
                }
                .makeInvisibleOnMacOS()
            }
        }
    }
}
