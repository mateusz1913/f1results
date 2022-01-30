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
            Navigation(items: [
                NavigationItem(labelImageName: "round_sports_score_black_24pt", labelImageText: "Current results", content: {
                    AnyView(CurrentRaceResultsScreen(currentRaceResultsState: currentRaceResultsState))
                }),
                NavigationItem(labelImageName: "round_emoji_events_black_24pt", labelImageText: "Current standings", content: {
                    AnyView(CurrentStandingsScreen(currentStandingsState: currentStandingsState))
                }),
                NavigationItem(labelImageName: "round_event_black_24pt", labelImageText: "Current calendar", content: {
                    AnyView(CurrentCalendarScreen(currentCalendarState: currentCalendarState))
                })
            ])
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
