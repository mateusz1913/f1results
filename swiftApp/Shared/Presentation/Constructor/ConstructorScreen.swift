import Foundation
import SwiftUI
import shared

struct ConstructorScreen: View {
    @ObservedObject var constructorState: ConstructorState
    
    var body: some View {
        if let constructor = constructorState.constructor {
            VStack {
                ScrollView {
                    VStack {
                        Text(constructor.name)
                            .font(.system(size: 30))
                            .fontWeight(.semibold)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.horizontal, 24)
                    VStack {
                        (Text("Nationality: ").italic() + Text(constructor.nationality))
                            .font(.system(size: 18))
                            .fontWeight(.medium)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.horizontal, 24)
                    if let seasons = constructorState.seasons, let selectedSeason = constructorState.selectedSeason {
                        let selectedSeasonBinding = Binding<String>(get: {
                            return selectedSeason
                        }, set: {
                            constructorState.viewModel.fetchConstructorStanding(season: $0)
                        })
                        ConstructorSeasonsSummary(seasons: seasons, constructorStanding: constructorState.constructorStanding, selectedSeason: selectedSeasonBinding)
                    }
                }.frame(maxWidth: .infinity, maxHeight: .infinity)
            }.frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            Text("No constructor")
        }
    }
}

struct ConstructorSeasonsSummary: View {
    var seasons: Array<SeasonType>
    var constructorStanding: ConstructorStandingType?
    @Binding var selectedSeason: String
    
    var body: some View {
        VStack {
            HStack {
                Text("Selected season: ")
                Spacer()
                Picker(selectedSeason, selection: $selectedSeason) {
                    ForEach(seasons, id: \.self) {
                        Text($0.season).tag($0.season)
                    }
                }
                .pickerStyle(.menu)
            }
            .frame(maxWidth: .infinity)
        }
        .padding(.horizontal, 24)
        .padding(.top, 10)
    }
}
