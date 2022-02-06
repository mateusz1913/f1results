import SwiftUI

struct InfoRow: View {
    var fontSize: CGFloat?
    var fontWeight: Font.Weight?
    var label: String
    var value: String
    
    var body: some View {
        (Text(label).italic() + Text(value))
            .font(.system(size: fontSize ?? 16))
            .fontWeight(fontWeight)
            .padding(.vertical, 2)
    }
}

struct InfoRow_Preview: PreviewProvider {
    static var previews: some View {
        VStack {
            InfoRow(label: "First label: ", value: "First value")
            InfoRow(fontSize: 24, fontWeight: .bold, label: "Second label: ", value: "Second value")
        }
        .previewLayout(PreviewLayout.sizeThatFits)
    }
}
