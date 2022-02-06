import SwiftUI
import shared

struct ConstructorSeasonResults: View {
    var raceResultsList: Array<RaceWithResultsType>? = nil
    
    var raceResultsIsFetching: Bool = false
    
    @State private var selectedDriver: String? = nil
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        if raceResultsIsFetching {
            loadingBody
        } else {
            resultBody
        }
    }
    
    private var loadingBody: some View {
        VStack {
            Loading()
        }
        .fillMaxSize()
    }
    
    private var resultBody: some View {
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

struct ConstructorSeasonResults_Preview: PreviewProvider {
    static var previews: some View {
        Group {
            ConstructorSeasonResults(raceResultsList: [], raceResultsIsFetching: true)
            ConstructorSeasonResults(raceResultsList: [
                RaceWithResultsType(season: "2021", round: "1", url: "", raceName: "Bahrain Grand Prix", circuit: CircuitType(circuitId: "bahrain", url: "", circuitName: "Sakhir", location: LocationType(alt: nil, lat: nil, long: nil, locality: "Sakhir", country: "Bahrain")), date: "05-03-2021", time: "18:00", results: KotlinArrayFromArray([
                    ResultType(number: "4", position: "3", positionText: "3", points: "15", driver: DriverType(driverId: "norris", permanentNumber: "4", code: "NOR", url: nil, givenName: "Lando", familyName: "Norris", dateOfBirth: "13-11-1999", nationality: "England"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "5", laps: "65", status: "", time: DurationType(millis: nil, time: "+4.0"), fastestLap: FastestLapType(rank: "2", lap: "24", time: DurationType(millis: "123", time: "1:22"), averageSpeed: AverageSpeedType(speed: "265", units: "kmh"))),
                    ResultType(number: "3", position: "6", positionText: "6", points: "8", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "7", laps: "65", status: "", time: DurationType(millis: nil, time: "+12.0"), fastestLap: FastestLapType(rank: "3", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh")))
                ])),
                RaceWithResultsType(season: "2021", round: "2", url: "", raceName: "Australian Grand Prix", circuit: CircuitType(circuitId: "melbourne", url: "", circuitName: "Albert Park", location: LocationType(alt: nil, lat: nil, long: nil, locality: "Melbourne", country: "Australia")), date: "05-03-2021", time: "18:00", results: KotlinArrayFromArray([
                    ResultType(number: "3", position: "8", positionText: "8", points: "4", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "9", laps: "65", status: "", time: DurationType(millis: "135", time: "+45.135"), fastestLap: FastestLapType(rank: "3", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh"))),
                    ResultType(number: "4", position: "13", positionText: "13", points: "0", driver: DriverType(driverId: "norris", permanentNumber: "4", code: "NOR", url: nil, givenName: "Lando", familyName: "Norris", dateOfBirth: "13-11-1999", nationality: "England"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "11", laps: "65", status: "", time: DurationType(millis: nil, time: "+54.0"), fastestLap: FastestLapType(rank: "12", lap: "24", time: DurationType(millis: "123", time: "1:24"), averageSpeed: AverageSpeedType(speed: "255", units: "kmh"))),
                ]))
            ], raceResultsIsFetching: false)
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 600))
    }
}
