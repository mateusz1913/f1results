import SwiftUI
import shared

struct ConstructorStandingRow: View {
    var constructorStanding: ConstructorStandingType
    
    var body: some View {
        HStack(alignment: .center) {
            HStack(alignment: .center) {
                StandingPosition(position: constructorStanding.position)
                VStack {
                    HStack(alignment: .center) {
                        Text(constructorStanding.constructor.name)
                            .font(.system(size: 18))
                            .fontWeight(.semibold)
                            .padding(.init(top: 2, leading: 10, bottom: 2, trailing: 10))
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                    }
                    .fillMaxWidth()
                }
                .fillMaxSize()
            }
            .fillMaxSize()
            StandingScore(points: constructorStanding.points, wins: constructorStanding.wins)
        }
        .fillMaxWidth()
    }
}
