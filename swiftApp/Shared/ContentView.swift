import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var currentRaceResultsViewModel = CurrentRaceResultsViewModel(raceResultsRepository: koin.get(), qualifyingResultsRepository: koin.get())
    @ObservedObject var currentStandingsViewModel = CurrentStandingsViewModel(repository: koin.get())

    var body: some View {
        VStack {
            TabView {
                CurrentRaceResultsScreen(currentRaceResultsViewModel: currentRaceResultsViewModel)
                    .tabItem {
                        Label("Current results", image: "round_sports_score_black_24pt")
                    }
                CurrentStandingsScreen(currentStandingsViewModel: currentStandingsViewModel)
                    .tabItem {
                        Label("Current standings", image: "round_emoji_events_black_24pt")
                    }
                CurrentCalendarScreen()
                    .tabItem {
                        Label("Current calendar", image: "round_event_black_24pt")
                    }
            }
        }
        #if os(macOS)
        .frame(minWidth: 200, idealWidth: 700, maxWidth: 1200, minHeight: 200, idealHeight: 700, maxHeight: 1200, alignment: .center)
        #endif
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
