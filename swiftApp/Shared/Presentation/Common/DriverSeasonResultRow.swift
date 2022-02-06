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

struct DriverSeasonResultRow_Preview: PreviewProvider {
    static var previews: some View {
        Group {
            DriverSeasonResultRow(driverResult: ResultType(number: "3", position: "6", positionText: "6", points: "8", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "7", laps: "65", status: "", time: DurationType(millis: nil, time: "+12.0"), fastestLap: FastestLapType(rank: "3", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh"))), selectedDriver: Binding.constant(nil))
            DriverSeasonResultRow(driverResult: ResultType(number: "3", position: "6", positionText: "6", points: "8", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "7", laps: "65", status: "", time: DurationType(millis: nil, time: "+12.0"), fastestLap: FastestLapType(rank: "3", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh"))), noDriverInfo: true, selectedDriver: Binding.constant(nil))
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 150))
    }
}
