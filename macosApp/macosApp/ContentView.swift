import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greeting()
    let api = F1Repository.shared.api
    @State private var driverState: DriverType? = nil

    var body: some View {
        VStack {
            Text(greet)
                .foregroundColor(.primary)
            if let driver = driverState {
                Text("\(driver.givenName) \(driver.familyName)")
                    .foregroundColor(.secondary)
            }
        }
        .frame(minWidth: 200, idealWidth: 700, maxWidth: 1200, minHeight: 200, idealHeight: 700, maxHeight: 1200, alignment: .center)
        .onAppear {
            api.driversApi.getSpecificDriver(driverId: "alonso") { response, error in
                if let response = response {
                    if response.MRData.DriverTable.Drivers.size > 0 {
                        let driver = response.MRData.DriverTable.Drivers.get(index: 0)
                        driverState = driver
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
