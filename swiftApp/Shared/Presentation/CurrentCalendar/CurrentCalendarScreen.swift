import SwiftUI
import shared

struct CurrentCalendarScreen: View {
    @ObservedObject var currentCalendarState: CurrentCalendarState
    
    var body: some View {
        VStack {
            RaceSchedule(raceScheduleList: currentCalendarState.currentRaceScheduleList)
        }
    }
}
