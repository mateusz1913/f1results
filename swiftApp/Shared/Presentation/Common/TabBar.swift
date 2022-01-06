import SwiftUI

struct TabBar: View {
    var items = [TabBarItem]()

    var body: some View {
        HStack {
            ForEach(0..<items.count, id: \.self) { i in
                Button {
                    items[i].onPress()
                } label: {
                    Text(items[i].label)
                        .frame(maxWidth: .infinity)
                        .padding(20)
                }
                .border(.orange, width: 1)
            }
        }
        .frame(height: 60)
    }
}

struct TabBarItem {
    let label: String
    let onPress: () -> Void
}
