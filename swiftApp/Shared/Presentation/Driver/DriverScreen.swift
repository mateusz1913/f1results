import Foundation
import SwiftUI
import shared

struct DriverScreen: View {
    @ObservedObject var driverState: DriverState
    
    var body: some View {
        if (driverState.driverIsFetching && driverState.driver == nil) || (driverState.seasonsIsFetching && driverState.seasons == nil) {
            Loading()
        } else if driverState.driver == nil {
            emptyBody
        } else {
            resultBody
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("No driver")
        }
        .fillMaxSize()
    }
    
    private var loadingBody: some View {
        VStack {
            Loading()
        }
        .fillMaxSize()
    }
    
    private var resultBody: some View {
        driverState.driver.map { driver in
            VStack {
                ScrollView {
                    VStack(alignment: .leading) {
                        HStack {
                            VStack(alignment: .leading) {
                                VStack {
                                    Text(driver.permanentNumber ?? "")
                                        .font(.system(size: 24))
                                        .fontWeight(.semibold)
                                }
                                .size(50)
                                .overlay(RoundedRectangle(cornerRadius: 25).stroke(.primary, lineWidth: 1))
                            }
                            .padding(.vertical, 2)
                            .padding(.trailing, 10)
                            Text(driver.code)
                                .font(.system(size: 36))
                                .fontWeight(.bold)
                                .foregroundColor(.secondary)
                            Spacer()
                        }
                        .fillMaxWidth()
                        .padding(.horizontal, 24)
                        .padding(.top, 20)
                        
                        InfoContainer {
                            HStack {
                                Text("\(driver.givenName) \(driver.familyName)")
                                    .font(.system(size: 30))
                                    .fontWeight(.semibold)
                                Spacer()
                            }
                        }
                        InfoContainer {
                            HStack {
                                InfoRow(fontSize: 18, fontWeight: .medium, label: "Nationality: ", value: driver.nationality ?? "")
                                Spacer()
                            }
                        }
                        InfoContainer {
                            HStack {
                                InfoRow(label: "Date of birth: ", value: driver.dateOfBirth ?? "")
                                Spacer()
                            }
                        }
                    }
                    .fillMaxWidth()
                    if let seasons = driverState.seasons, let selectedSeason = driverState.selectedSeason {
                        let selectedSeasonBinding = Binding<String>(get: {
                            return selectedSeason
                        }, set: {
                            driverState.viewModel.fetchDriverStanding(season: $0)
                        })
                        DriverSeasonsSummary(seasons: seasons, driverStanding: driverState.driverStanding, selectedSeason: selectedSeasonBinding)
                    }
                    DriverSeasonResults(raceResultsList: driverState.raceResults, raceResultsIsFetching: driverState.raceResultsIsFetching)
                }
                .fillMaxSize()
            }
            .fillMaxSize()
        }
    }
}
