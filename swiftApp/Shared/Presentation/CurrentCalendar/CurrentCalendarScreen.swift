import SwiftUI
import shared

struct CurrentCalendarScreen: View {
    @EnvironmentObject var currentCalendarState: CurrentCalendarState
    
    var body: some View {
        VStack {
            RaceSchedule()
        }
    }
}
