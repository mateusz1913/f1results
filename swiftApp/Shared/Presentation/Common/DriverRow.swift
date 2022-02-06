import SwiftUI
import shared

struct DriverQualifyingRow: View {
    var result: QualifyingResultType
    var noDriverInfo: Bool = false
    
    var body: some View {
        DriverRow(position: result.position, noDriverInfo: noDriverInfo, driverName: "\(result.driver.givenName) \(result.driver.familyName)", driverNumber: result.number, driverConstructorName: result.constructor.name, driverTime: result.Q3 ?? result.Q2 ?? result.Q1, noDriverPoints: true, driverPoints: nil, driverFastestLap: nil)
    }
}

struct DriverResultRow: View {
    var result: ResultType
    var noDriverInfo: Bool = false
    
    var body: some View {
        DriverRow(position: result.positionText, noDriverInfo: noDriverInfo, driverName: "\(result.driver.givenName) \(result.driver.familyName)", driverNumber: result.number, driverConstructorName: result.constructor.name, driverTime: result.time?.time ?? result.status, noDriverPoints: false, driverPoints: result.points, driverFastestLap: result.fastestLap)
    }
}

struct DriverRow: View {
    var position: String
    var noDriverInfo: Bool = false
    var driverName: String
    var driverNumber: String?
    var driverConstructorName: String
    var driverTime: String
    var noDriverPoints: Bool = false
    var driverPoints: String?
    var driverFastestLap: FastestLapType?
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: position)
                VStack(alignment: .center) {
                    HStack(alignment: .center) {
                        if !noDriverInfo {
                            Text(driverName)
                                .font(.system(size: 18))
                                .fontWeight(.bold)
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                .padding(.horizontal, 10)
                                .padding(.vertical, 2)
                            VStack {
                                VStack(alignment: .center) {
                                    if let number = driverNumber {
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
                        Text(driverConstructorName)
                            .font(.system(size: 16))
                            .fontWeight(.bold)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, 10)
                            .padding(.vertical, 2)
                        VStack {
                            Text(driverTime)
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
            if !noDriverPoints {
                HStack(alignment: .center, spacing: 0) {
#if os(iOS)
                    if let uiImage = UIImage(named: "baseline_local_fire_department_black_24pt") {
                        Image(uiImage: uiImage)
                            .foregroundColor(Color.secondaryColor.opacity(driverFastestLap?.rank == "1" ? 1 : 0))
                    }
#elseif os(macOS)
                    if let nsImage = NSImage(named: "baseline_local_fire_department_black_24pt") {
                        Image(nsImage: nsImage)
                            .foregroundColor(Color.secondaryColor.opacity(driverFastestLap?.rank == "1" ? 1 : 0))
                    }
#endif
                    ZStack(alignment: .center) {
                        Text(driverPoints ?? "-")
                            .font(.system(size: 14))
                            .fontWeight(.bold)
                    }
                    .size(30)
                    .clipShape(Circle())
                }
                .padding(.init(top: 2, leading: 2, bottom: 2, trailing: 10))
            }
        }
        .fillMaxWidth()
    }
}

struct DriverQualifyingRow_Preview: PreviewProvider {
    static var previews: some View {
        Group {
            DriverQualifyingRow(result: QualifyingResultType(number: "3", position: "7", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), Q1: "1:23.456", Q2: "1:22.333", Q3: "1:21.333"))
            DriverQualifyingRow(result: QualifyingResultType(number: "3", position: "7", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), Q1: "1:23.456", Q2: "1:22.333", Q3: "1:21.333"), noDriverInfo: true)
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 100))
    }
}

struct DriverResultRow_Preview: PreviewProvider {
    static var previews: some View {
        Group {
            DriverResultRow(result: ResultType(number: "3", position: "6", positionText: "6", points: "8", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "7", laps: "65", status: "", time: DurationType(millis: nil, time: "+12.0"), fastestLap: FastestLapType(rank: "3", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh"))))
            DriverResultRow(result: ResultType(number: "3", position: "6", positionText: "6", points: "8", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructor: ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom"), grid: "7", laps: "65", status: "", time: DurationType(millis: nil, time: "+12.0"), fastestLap: FastestLapType(rank: "1", lap: "4", time: DurationType(millis: "123", time: "1:23"), averageSpeed: AverageSpeedType(speed: "260", units: "kmh"))), noDriverInfo: true)
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 100))
    }
}
