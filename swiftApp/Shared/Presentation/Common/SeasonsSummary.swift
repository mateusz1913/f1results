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

struct ConstructorSeasonsSummary_Preview: PreviewProvider {
    static var previews: some View {
        VStack {
            ConstructorSeasonsSummary(seasons: [
                SeasonType(season: "2022", url: ""),
                SeasonType(season: "2021", url: "")
            ], constructorStanding: ConstructorStandingType(position: "4", points: "234", wins: "5", constructor: ConstructorType(constructorId: "ferrari", url: nil, name: "Ferrari", nationality: "Italy")), selectedSeason: Binding.constant("2022"))
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 200))
    }
}

struct DriverSeasonsSummary_Preview: PreviewProvider {
    static var previews: some View {
        VStack {
            DriverSeasonsSummary(seasons: [
                SeasonType(season: "2022", url: ""),
                SeasonType(season: "2021", url: "")
            ], driverStanding: DriverStandingType(position: "5", positionText: "5", points: "123", wins: "0", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructors: KotlinArrayFromArray([
                ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom")
            ])), selectedSeason: Binding.constant("2022"))
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 200))
    }
}
