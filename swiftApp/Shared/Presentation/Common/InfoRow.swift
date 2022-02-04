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
