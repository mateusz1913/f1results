import SwiftUI

struct Loading: View {
    var body: some View {
        ProgressView()
            .scaleEffect(2, anchor: .center)
            .progressViewStyle(CircularProgressViewStyle(tint: Color(red: 39 / 255, green: 122 / 255, blue: 187 / 255)))
            .frame(width: 36, height: 36)
    }
}

struct Loading_Preview: PreviewProvider {
    static var previews: some View {
        Loading()
    }
}
