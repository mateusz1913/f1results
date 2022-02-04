import SwiftUI
import shared

struct DriverQualifyingRow: View {
    var result: QualifyingResultType
    var noDriverInfo: Bool = false
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: result.position)
                VStack {
                    HStack(alignment: .center) {
                        if !noDriverInfo {
                            Text("\(result.driver.givenName) \(result.driver.familyName)")
                                .font(.system(size: 18))
                                .fontWeight(.bold)
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                .padding(.horizontal, 10)
                                .padding(.vertical, 2)
                            VStack {
                                VStack(alignment: .center) {
                                    if let number = result.number {
                                        Text(number)
                                            .font(.system(size: 14))
                                            .fontWeight(.medium)
                                    }
                                }
                                .size(26)
                                .overlay(RoundedRectangle(cornerRadius: 13).stroke(lineWidth: 1))
                            }
                            .padding(.horizontal, 10)
                            .padding(.vertical, 2)
                        }
                    }
                    .fillMaxWidth()
                    HStack(alignment: .center) {
                        Text(result.constructor.name)
                            .font(.system(size: 16))
                            .fontWeight(.bold)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, 10)
                            .padding(.vertical, 2)
                        VStack {
                            Text(result.Q3 ?? result.Q2 ?? result.Q1)
                                .font(.system(size: 14))
                                .fontWeight(.medium)
                                .padding(.trailing, 10)
                        }
                        .padding(.vertical, 2)
                    }
                    .fillMaxWidth()
                }
                .fillMaxSize()
            }
            .fillMaxSize()
        }
    }
}
