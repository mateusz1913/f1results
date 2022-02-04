import SwiftUI
import shared

struct ConstructorSeasonsSummary: View {
    var seasons: Array<SeasonType>
    var constructorStanding: ConstructorStandingType?
    @Binding var selectedSeason: String
    
    var body: some View {
        SeasonsSummary(seasons: seasons, selectedSeason: $selectedSeason) {
            constructorStanding.map { standing in
                VStack(alignment: .leading) {
                    Text("Standings: ")
                        .font(.system(size: 18))
                        .italic()
                    ConstructorStandingRow(constructorStanding: standing)
                }
                .fillMaxWidth()
                .padding(.horizontal, 24)
                .padding(.top, 10)
            }
        }
    }
}

struct DriverSeasonsSummary: View {
    var seasons: Array<SeasonType>
    var driverStanding: DriverStandingType?
    @Binding var selectedSeason: String
    
    var body: some View {
        SeasonsSummary(seasons: seasons, selectedSeason: $selectedSeason) {
            driverStanding.map { standing in
                VStack(alignment: .leading) {
                    Text("Standings: ")
                        .font(.system(size: 18))
                        .italic()
                    DriverStandingRow(driverStanding: standing, noDriverInfo: true)
                }
                .fillMaxWidth()
                .padding(.horizontal, 24)
                .padding(.top, 10)
            }
        }
    }
}

struct SeasonsSummary<Content: View>: View {
    var seasons: Array<SeasonType>
    @Binding var selectedSeason: String
    
    @ViewBuilder var content: () -> Content
    
    var body: some View {
        VStack {
            HStack {
                Text("Selected season: ")
                Spacer()
                Picker("", selection: $selectedSeason) {
                    ForEach(seasons, id: \.self) {
                        Text($0.season).tag($0.season)
                    }
                }
                .pickerStyle(.menu)
            }
            .fillMaxWidth()
            .padding(.horizontal, 24)
            content()
        }
        .padding(.top, 10)
    }
}
