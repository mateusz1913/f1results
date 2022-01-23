import SwiftUI
import shared

struct RaceSchedule: View {
    var raceScheduleList: Array<RaceType>? = nil
    
    var body: some View {
        VStack {
            if let raceScheduleList = raceScheduleList {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<raceScheduleList.count, id: \.self) { i in
                            if let raceSchedule = raceScheduleList[i] {
                                let text = "\(raceSchedule.round): \(raceSchedule.raceName) \(raceSchedule.date)"
                                NavigationLink(destination: NavigationLazyView(CircuitScreen(circuitState: CircuitState(circuitId: raceSchedule.circuit.circuitId)))) {
                                    Text(text)
                                        .padding(4)
                                        .frame(maxWidth: .infinity, alignment: .leading)
                                        .border(.orange, width: 1)
                                }
                            }
                        }
                    }
                }.frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
    }
}
