import SwiftUI
import shared

private enum StandingsTabItems: Int {
    case driver, constructor
}

struct CurrentStandingsScreen: View {
    @ObservedObject var currentStandingsState: CurrentStandingsState
    
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
                DriverStandings(driverStandings: currentStandingsState.driverStandings)
                    .tag(StandingsTabItems.driver.rawValue)
                ConstructorStandings(constructorStandings: currentStandingsState.constructorStandings)
                    .tag(StandingsTabItems.constructor.rawValue)
            }
            #if os(iOS)
            .tabViewStyle(.page(indexDisplayMode: .never))
            #endif
        }
    }
}
