import Foundation
import SwiftUI
import shared

struct ConstructorScreen: View {
    @ObservedObject var constructorState: ConstructorState
    
    var body: some View {
        if constructorState.constructor == nil {
            emptyBody
        }
        constructorState.constructor.map { constructor in
            VStack {
                ScrollView {
                    InfoContainer {
                        HStack {
                            Text(constructor.name)
                                .font(.system(size: 30))
                                .fontWeight(.semibold)
                            Spacer()
                        }
                    }
                    InfoContainer {
                        HStack {
                            InfoRow(fontSize: 18, fontWeight: .medium, label: "Nationality: ", value: constructor.nationality)
                            Spacer()
                        }
                    }
                    if let seasons = constructorState.seasons, let selectedSeason = constructorState.selectedSeason {
                        let selectedSeasonBinding = Binding<String>(get: {
                            return selectedSeason
                        }, set: {
                            constructorState.viewModel.fetchConstructorStanding(season: $0)
                        })
                        ConstructorSeasonsSummary(seasons: seasons, constructorStanding: constructorState.constructorStanding, selectedSeason: selectedSeasonBinding)
                    }
                    ConstructorSeasonResults(raceResultsList: constructorState.raceResults)
                }
                .fillMaxSize()
            }
            .fillMaxSize()
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("No constructor")
        }
        .fillMaxSize()
    }
}
