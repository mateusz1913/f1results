import SwiftUI
import shared

struct DriverResultRow: View {
    var result: ResultType
    var noDriverInfo: Bool = false
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: result.positionText)
                VStack(alignment: .center) {
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
                            Text(result.time?.time ?? result.status)
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
            HStack(alignment: .center) {
                #if os(iOS)
                if let uiImage = UIImage(named: "baseline_local_fire_department_black_24pt") {
                    Image(uiImage: uiImage)
                        .foregroundColor(Color.secondary.opacity(result.fastestLap?.rank == "1" ? 1 : 0))
                }
                #elseif os(macOS)
                if let nsImage = NSImage(named: "baseline_local_fire_department_black_24pt") {
                    Image(nsImage: nsImage)
                        .foregroundColor(Color.secondary.opacity(result.fastestLap?.rank == "1" ? 1 : 0))
                }
                #endif
                VStack(alignment: .center) {
                    Text(result.points ?? "-")
                        .font(.system(size: 14))
                        .fontWeight(.bold)
                }
                .size(30)
                .clipShape(Circle())
                .padding(.init(top: 2, leading: 2, bottom: 2, trailing: 10))
            }
        }
        .fillMaxWidth()
    }
}
