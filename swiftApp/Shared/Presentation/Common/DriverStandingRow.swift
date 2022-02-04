import SwiftUI
import shared

struct DriverStandingRow: View {
    var driverStanding: DriverStandingType
    var noDriverInfo: Bool = false
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: driverStanding.positionText)
                VStack(alignment: .center) {
                    HStack(alignment: .center) {
                        if !noDriverInfo {
                            Text("\(driverStanding.driver.givenName) \(driverStanding.driver.familyName)")
                                .font(.system(size: 18))
                                .fontWeight(.bold)
                                .padding(.init(top: 2, leading: 10, bottom: 2, trailing: 10))
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                        }
                    }
                    .fillMaxWidth()
                    HStack(alignment: .center) {
                        let constructorsNames = getConstructorsNames(driverStanding.constructors)
                        Text(constructorsNames)
                            .font(.system(size: 18))
                            .fontWeight(.semibold)
                            .padding(.init(top: 2, leading: !noDriverInfo ? 10 : 0, bottom: 2, trailing: 10))
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                    }
                    .fillMaxWidth()
                }
                .fillMaxSize()
            }
            .fillMaxSize()
            StandingScore(points: driverStanding.points, wins: driverStanding.wins)
        }
        .fillMaxWidth()
    }
    
    private func getConstructorsNames(_ constructors: KotlinArray<ConstructorType>) -> String {
        var constructorsNamesArr = [String]()
        for index in 0..<constructors.size {
            if let constructor = constructors.get(index: index) {
                constructorsNamesArr.append(constructor.name)
            }
        }
        return constructorsNamesArr.joined(separator: " / ")
    }
}
