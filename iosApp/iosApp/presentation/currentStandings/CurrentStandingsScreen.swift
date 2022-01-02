import SwiftUI
import shared

private enum StandingsTabItems: Int {
    case driver, constructor
}

struct CurrentStandingsScreen: View {
    @ObservedObject var currentStandingsViewModel: CurrentStandingsViewModel
    
    @State var selectedTab = StandingsTabItems.driver.rawValue
    
    var body: some View {
        VStack {
            TabBar(items: [
                TabBarItem(label: "Drivers", onPress: {
                    withAnimation {
                        selectedTab = StandingsTabItems.driver.rawValue
                    }
                }),
                TabBarItem(label: "Constructors", onPress: {
                    withAnimation {
                        selectedTab = StandingsTabItems.constructor.rawValue
                    }
                })
            ])
            TabView(selection: $selectedTab) {
                DriverStandings(driverStandings: currentStandingsViewModel.driverStandings)
                    .tag(StandingsTabItems.driver.rawValue)
                ConstructorStandings(constructorStandings: currentStandingsViewModel.constructorStandings)
                    .tag(StandingsTabItems.constructor.rawValue)
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
        }
    }
}
