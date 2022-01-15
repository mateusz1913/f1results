import SwiftUI
import shared

struct ContentView: View {
    let currentRaceResultsState = CurrentRaceResultsState()
    let currentStandingsState = CurrentStandingsState()
    let currentCalendarState = CurrentCalendarState()
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        ZStack {
            colorScheme == .dark ? Color.darkBackground : Color.lightBackground
            NavigationView {
                VStack {
                    TabView {
                        CurrentRaceResultsScreen(currentRaceResultsState: currentRaceResultsState)
                            .tabItem {
                                Label("Current results", image: "round_sports_score_black_24pt")
                            }
                            .navigationTitle("Current results")
                        CurrentStandingsScreen(currentStandingsState: currentStandingsState)
                            .tabItem {
                                Label("Current standings", image: "round_emoji_events_black_24pt")
                            }
                            .navigationTitle("Current standings")
                        CurrentCalendarScreen(currentCalendarState: currentCalendarState)
                            .tabItem {
                                Label("Current calendar", image: "round_event_black_24pt")
                            }
                            .navigationTitle("Current calendar")
                    }
                }
#if os(macOS)
                .frame(minWidth: 200, idealWidth: 700, maxWidth: 1200, minHeight: 200, idealHeight: 700, maxHeight: 1200, alignment: .center)
#endif
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
