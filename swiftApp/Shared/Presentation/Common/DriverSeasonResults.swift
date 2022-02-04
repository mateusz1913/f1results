import SwiftUI
import shared

struct DriverSeasonResults: View {
    var raceResultsList: Array<RaceWithResultsType>? = nil
    
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
                        if let firstDriverResult = result.results.get(index: 0) {
                            DriverSeasonResultRow(driverResult: firstDriverResult, noDriverInfo: true, selectedDriver: Binding.constant(nil))
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
