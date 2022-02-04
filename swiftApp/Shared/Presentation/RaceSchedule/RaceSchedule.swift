import SwiftUI
import shared

struct RaceSchedule: View {
    var raceScheduleList: Array<RaceType>? = nil
    
    @State private var selectedCircuit: String?
    
    var body: some View {
        VStack {
            if let raceScheduleList = raceScheduleList {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<raceScheduleList.count, id: \.self) { i in
                            if let raceSchedule = raceScheduleList[i] {
                                Button {
                                    selectedCircuit = raceSchedule.circuit.circuitId
                                } label: {
                                    RaceScheduleRow(race: raceSchedule)
                                }
                                .buttonStyle(RaceScheduleRowButton())
                                NavigationLink(
                                    destination: NavigationLazyView(CircuitScreen(circuitState: CircuitState(circuitId: raceSchedule.circuit.circuitId))),
                                    tag: raceSchedule.circuit.circuitId,
                                    selection: $selectedCircuit
                                ) {
                                    EmptyView()
                                }
                                .makeInvisibleOnMacOS()
                            }
                        }
                    }
                }
                .fillMaxSize()
            } else {
                emptyBody
            }
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("TBD")
        }
        .fillMaxSize()
    }
}

fileprivate struct RaceScheduleRowButton: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .opacity(configuration.isPressed ? 0.4 : 1)
            .padding(.horizontal, 24)
            .padding(.vertical, 4)
    }
}
