import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var currentRaceResultsViewModel = CurrentRaceResultsViewModel(raceResultsRepository: RaceResultsRepository(), qualifyingResultsRepository: QualifyingResultsRepository())
    @ObservedObject var currentStandingsViewModel = CurrentStandingsViewModel(repository: StandingsRepository())

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
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
