import SwiftUI
import shared

struct DriverStandings: View {
    var driverStandings: DriverStandingsType? = nil
    
    var body: some View {
        VStack {
            if let driverStandings = driverStandings {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<driverStandings.driverStandings.size, id: \.self) { i in
                            if let standing = driverStandings.driverStandings.get(index: i) {
                                let text = "\(standing.positionText): \(standing.driver.givenName) \(standing.driver.familyName) - \(getConstructorsNames(standing.constructors))"
                                
                                Text(text)
                                    .padding(4)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .border(.orange, width: 1)
                            }
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else {
                Text("Driver standings")
            }
        }
    }
    
    func getConstructorsNames(_ constructors: KotlinArray<ConstructorType>) -> String {
        var constructorsNamesArr = [String]()
        for index in 0..<constructors.size {
            if let constructor = constructors.get(index: index) {
                constructorsNamesArr.append(constructor.name)
            }
        }
        return constructorsNamesArr.joined(separator: " / ")
    }
}
