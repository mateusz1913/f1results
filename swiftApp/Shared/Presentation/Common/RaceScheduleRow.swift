import SwiftUI
import shared

struct RaceScheduleRow: View {
    var race: RaceType
    
    var body: some View {
        HStack(alignment: .center) {
            VStack {
                VStack(alignment: .center) {
                    Text(race.round)
                        .font(.system(size: 16))
                        .fontWeight(.bold)
                }
                .size(40)
            }
            .padding(.init(top: 4, leading: 2, bottom: 4, trailing: 4))
            GeometryReader { geo in
                HStack(alignment: .center) {
                    VStack {
                        Text(race.raceName)
                            .font(.system(size: 18))
                            .fontWeight(.semibold)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.horizontal, 10)
                            .padding(.vertical, 2)
                    }
                    .frame(width: geo.size.width * 0.66)
                    VStack {
                        VStack {
                            Text(race.date)
                                .font(.system(size: 14))
                                .fontWeight(.medium)
                                .frame(minWidth: 0, maxWidth: .infinity, alignment: .trailing)
                        }
                        .padding(.init(top: 2, leading: 4, bottom: 2, trailing: 10))
                    }
                    .frame(width: geo.size.width * 0.34)
                }
                .fillMaxHeight()
            }
            .fillMaxWidth()
        }
    }
}

struct RaceScheduleRow_Preview: PreviewProvider {
    static var previews: some View {
        RaceScheduleRow(race: RaceType(season: "2021", round: "1", url: "", raceName: "Bahrain Grand Prix", circuit: CircuitType(circuitId: "bahrain", url: "", circuitName: "Sakhir", location: LocationType(alt: nil, lat: nil, long: nil, locality: "Sakhir", country: "Bahrain")), date: "05-03-2021", time: "18:00"))
            .previewLayout(PreviewLayout.fixed(width: 400, height: 100))
    }
}
