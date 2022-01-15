import Foundation
import SwiftUI
import shared

class CurrentCalendarState: ObservableObject {
    let viewModel: CurrentCalendarViewModel = koin.get()
    
    @Published private(set) var currentRaceScheduleList: Array<RaceType>? = nil
    
    init() {
        viewModel.observeCurrentRaceSchedule { currentRaceScheduleList in
            if let kotlinArr = currentRaceScheduleList.currentRaceScheduleList {
                self.currentRaceScheduleList = Array(kotlinArr)
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
