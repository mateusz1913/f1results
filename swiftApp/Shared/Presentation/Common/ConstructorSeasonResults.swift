import SwiftUI
import shared

struct ConstructorSeasonResults: View {
    var raceResultsList: Array<RaceWithResultsType>? = nil
    
    @State private var selectedDriver: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            raceResultsList.map { results in
                ForEach(results, id: \.self) { result in
                    VStack {
                        HStack {
                            Text(result.raceName)
                                .font(.system(size: 18))
                                .fontWeight(.semibold)
                            Spacer()
                        }
                        ForEach(0..<result.results.size, id: \.self) { i in
                            if let driverResult = result.results.get(index: i) {
                                DriverSeasonResultRow(driverResult: driverResult, selectedDriver: $selectedDriver)
                            }
                        }
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 5)
                    .background(colorScheme == .dark ? Color.darkBackground : Color.lightBackground)
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(Color.lightBackground, lineWidth: 1))
                    .padding(.horizontal, 24)
                    .padding(.vertical, 5)
                }
            }
        }
        .fillMaxSize()
        .padding(.vertical, 24)
    }
}
