import SwiftUI

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
