import SwiftUI
import shared

private enum RaceResultsTabBarItems: Int {
    case race, qualification
}

struct CurrentRaceResultsScreen: View {
    @ObservedObject var currentRaceResultsViewModel: CurrentRaceResultsViewModel

    @State var selectedTab = RaceResultsTabBarItems.race.rawValue
    
    var body: some View {
        VStack {
            TabBar(items: [
                TabBarItem(label: "Race", onPress: {
                    withAnimation {
                        selectedTab = RaceResultsTabBarItems.race.rawValue
                    }
                }),
                TabBarItem(label: "Qualification", onPress: {
                    withAnimation {
                        selectedTab = RaceResultsTabBarItems.qualification.rawValue
                    }
                })
            ])
            TabView(selection: $selectedTab) {
                RaceResults(raceResults: currentRaceResultsViewModel.raceResults)
                    .tag(RaceResultsTabBarItems.race.rawValue)
                QualifyingResults(qualifyingResults: currentRaceResultsViewModel.qualifyingResults)
                    .tag(RaceResultsTabBarItems.qualification.rawValue)
            }
            .tabViewStyle(.page(indexDisplayMode: .never))
        }
    }
}
