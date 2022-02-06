import SwiftUI
import shared

struct ConstructorStandingRow: View {
    var constructorStanding: ConstructorStandingType
    
    var body: some View {
        BaseStandingRow(position: constructorStanding.position, leftText: constructorStanding.constructor.name, rightText: nil, points: constructorStanding.points, wins: constructorStanding.wins)
    }
}

struct DriverStandingRow: View {
    var driverStanding: DriverStandingType
    var noDriverInfo: Bool = false
    
    var body: some View {
        let constructorsNames = getConstructorsNames(driverStanding.constructors)
        BaseStandingRow(position: driverStanding.positionText, leftText: "\(driverStanding.driver.givenName) \(driverStanding.driver.familyName)", rightText: constructorsNames, points: driverStanding.points, wins: driverStanding.wins, noDriverInfo: noDriverInfo)
    }
    
    private func getConstructorsNames(_ constructors: KotlinArray<ConstructorType>) -> String {
        var constructorsNamesArr = [String]()
        for index in 0..<constructors.size {
            if let constructor = constructors.get(index: index) {
                constructorsNamesArr.append(constructor.name)
            }
        }
        return constructorsNamesArr.joined(separator: " / ")
    }
}

struct BaseStandingRow: View {
    var position: String
    var leftText: String
    var rightText: String?
    var points: String
    var wins: String
    var noDriverInfo: Bool = false
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: position)
                VStack(alignment: .center) {
                    HStack(alignment: .center) {
                        if !noDriverInfo {
                            Text(leftText)
                                .font(.system(size: 18))
                                .fontWeight(.bold)
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                .padding(.horizontal, 10)
                                .padding(.vertical, 2)
                        }
                    }
                    .fillMaxWidth()
                    if let rightText = rightText {
                        HStack(alignment: .center) {
                            Text(rightText)
                                .font(.system(size: 18))
                                .fontWeight(.semibold)
                                .padding(.init(top: 2, leading: !noDriverInfo ? 10 : 0, bottom: 2, trailing: 10))
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                        }
                        .fillMaxWidth()                        
                    }
                }
                .fillMaxSize()
            }
            .fillMaxSize()
            StandingScore(points: points, wins: wins)
        }
        .fillMaxWidth()
    }
}

struct StandingPosition: View {
    var position: String
    
    var body: some View {
        VStack {
            VStack(alignment: .center) {
                Text(position)
                    .font(.system(size: 16))
                    .fontWeight(.black)
            }
            .size(40)
            .background(content: {
                if position == "1" {
                    Color.firstPlace
                } else if position == "2" {
                    Color.secondPlace
                } else if position == "3" {
                    Color.thirdPlace
                } else {
                    Color.transparent
                }
            })
            .clipShape(Circle())
        }
        .padding(10)
    }
}

struct StandingScore: View {
    var points: String
    var wins: String
    
    var body: some View {
        HStack(alignment: .center) {
            VStack {
                InfoRow(fontSize: 14, fontWeight: .medium, label: "Wins: ", value: wins)
            }
            .padding(.trailing, 4)
            VStack {
                Text(points)
                    .font(.system(size: 14))
                    .fontWeight(.bold)
            }
            .padding(.init(top: 2, leading: 4, bottom: 2, trailing: 10))
        }
    }
}

struct ConstructorStandingRow_Preview: PreviewProvider {
    static var previews: some View {
        ConstructorStandingRow(constructorStanding: ConstructorStandingType(position: "2", points: "345", wins: "6", constructor: ConstructorType(constructorId: "mercedes", url: nil, name: "Mercedes", nationality: "Germany")))
            .previewLayout(PreviewLayout.fixed(width: 400, height: 100))
    }
}

struct DriverStandingRow_Preview: PreviewProvider {
    static var previews: some View {
        Group {
            DriverStandingRow(driverStanding: DriverStandingType(position: "5", positionText: "5", points: "123", wins: "0", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructors: KotlinArrayFromArray([
                ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom")
            ])))
            DriverStandingRow(driverStanding: DriverStandingType(position: "5", positionText: "5", points: "123", wins: "0", driver: DriverType(driverId: "ricciardo", permanentNumber: "3", code: "RIC", url: nil, givenName: "Daniel", familyName: "Ricciardo", dateOfBirth: "01-07-1989", nationality: "Australia"), constructors: KotlinArrayFromArray([
                ConstructorType(constructorId: "mclaren", url: nil, name: "McLaren", nationality: "United Kingdom")
            ])), noDriverInfo: true)
        }
        .previewLayout(PreviewLayout.fixed(width: 400, height: 100))
    }
}

struct StandingPosition_Preview: PreviewProvider {
    static var previews: some View {
        VStack {
            StandingPosition(position: "1")
            StandingPosition(position: "2")
            StandingPosition(position: "3")
            StandingPosition(position: "13")
        }
        .previewLayout(PreviewLayout.sizeThatFits)
    }
}

struct StandingScore_Preview: PreviewProvider {
    static var previews: some View {
        StandingScore(points: "123", wins: "1")
            .previewLayout(PreviewLayout.sizeThatFits)
    }
}
