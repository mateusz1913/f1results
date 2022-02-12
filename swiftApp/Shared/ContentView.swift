import SwiftUI
import shared

struct ContentView: View {
    let currentRaceResultsState = CurrentRaceResultsState()
    let currentStandingsState = CurrentStandingsState()
    let currentCalendarState = CurrentCalendarState()
    
    @Environment(\.colorScheme) var colorScheme
    
    func prepareNavigationItems() -> [NavigationItem] {
        var items: [NavigationItem] = [
            NavigationItem(labelImageName: "round_sports_score_black_24pt", labelImageText: "Current results", content: {
                AnyView(CurrentRaceResultsScreen(currentRaceResultsState: currentRaceResultsState))
            }),
            NavigationItem(labelImageName: "round_emoji_events_black_24pt", labelImageText: "Current standings", content: {
                AnyView(CurrentStandingsScreen(currentStandingsState: currentStandingsState))
            }),
            NavigationItem(labelImageName: "round_event_black_24pt", labelImageText: "Current calendar", content: {
                AnyView(CurrentCalendarScreen(currentCalendarState: currentCalendarState))
            })
        ]
        #if os(iOS)
        items.append(NavigationItem(labelImageName: "round_more_horiz_black_24pt", labelImageText: "Explore") {
            AnyView(ExploreScreen())
        })
        #elseif os(macOS)
        let macOSItems: [NavigationItem] = [
            NavigationItem(labelImageName: "round_sports_score_black_24pt", labelImageText: "Archival races") {
                AnyView(VStack {})
            },
            NavigationItem(labelImageName: "round_emoji_events_black_24pt", labelImageText: "Archival standings") {
                AnyView(VStack {})
            },
            NavigationItem(labelImageName: "round_sports_motorsports_black_24pt", labelImageText: "Drivers") {
                AnyView(VStack {})
            },
            NavigationItem(labelImageName: "round_groups_black_24pt", labelImageText: "Constructors") {
                AnyView(VStack {})
            },
            NavigationItem(labelImageName: "round_settings_black_24pt", labelImageText: "Settings") {
                AnyView(VStack {})
            }
        ]
        items.append(contentsOf: macOSItems)
        #endif
        return items
    }
    
    var body: some View {
        ZStack {
            colorScheme == .dark ? Color.darkBackground : Color.lightBackground
            Navigation(items: prepareNavigationItems())
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
