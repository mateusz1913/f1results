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
                        .font(.system(size: 18))
                        .fillMaxWidth()
                        .padding(20)
                }
                .buttonStyle(PlainButtonStyle())
                .fillMaxHeight()
            }
        }
        .frame(height: 60)
    }
}

struct TabBarItem {
    let label: String
    let onPress: () -> Void
}

struct TabBar_Provider: PreviewProvider {
    static var previews: some View {
        TabBar(items: [
            TabBarItem(label: "First label", onPress: {}),
            TabBarItem(label: "Second label", onPress: {}),
        ])
            .previewLayout(PreviewLayout.sizeThatFits)
    }
}
