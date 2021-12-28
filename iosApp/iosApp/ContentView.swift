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
        .onAppear {
            api.driversApi.getSpecificDriver(driverId: "sainz") { response, error in
                if let driver = response {
                    driverState = driver
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
