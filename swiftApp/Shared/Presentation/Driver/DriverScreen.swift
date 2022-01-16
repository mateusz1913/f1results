import Foundation
import SwiftUI
import shared

struct DriverScreen: View {
    @ObservedObject var driverState: DriverState
    
    var body: some View {
        if let driver = driverState.driver {
            VStack {
                ScrollView {
                    HStack {
                        VStack {
                            VStack {
                                Text(driver.permanentNumber ?? "")
                                    .font(.system(size: 24))
                                    .fontWeight(.semibold)
                                    .frame(alignment: .center)
                            }
                            .frame(width: 50, height: 50)
                            .overlay(RoundedRectangle(cornerRadius: 25).stroke(.primary, lineWidth: 1))
                            
                            Text(driver.code)
                                .font(.system(size: 36))
                                .fontWeight(.bold)
                                .foregroundColor(.secondary)
                        }
                        .padding(.vertical, 2)
                        .padding(.trailing, 10)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.horizontal, 24)
                    .padding(.top, 20)
                    
                    InfoContainer {
                        Text("\(driver.givenName) \(driver.familyName)")
                            .font(.system(size: 30))
                            .fontWeight(.semibold)
                    }
                    InfoContainer {
                        (Text("Nationality: ").italic() + Text(driver.nationality ?? ""))
                            .font(.system(size: 18))
                            .fontWeight(.medium)
                    }
                    InfoContainer {
                        (Text("Date of birth: ").italic() + Text(driver.dateOfBirth ?? ""))
                    }
                    if let seasons = driverState.seasons, let selectedSeason = driverState.selectedSeason {
                        let selectedSeasonBinding = Binding<String>(get: {
                            return selectedSeason
                        }, set: {
                            driverState.viewModel.fetchDriverStanding(season: $0)
                        })
                        SeasonsSummary(seasons: seasons, driverStanding: driverState.driverStanding, selectedSeason: selectedSeasonBinding)
                    }
                }.frame(maxWidth: .infinity, maxHeight: .infinity)
            }.frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            Text("No driver")
        }
    }
}

struct InfoContainer<C: View>: View {
    let childView: C
    
    init (_ childView: () -> (C)) {
        self.childView = childView()
    }
    
    var body: some View {
        VStack {
            childView
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 24)
    }
}

struct SeasonsSummary: View {
    var seasons: Array<SeasonType>
    var driverStanding: DriverStandingType?
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
