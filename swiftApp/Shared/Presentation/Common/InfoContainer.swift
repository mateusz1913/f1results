import SwiftUI

struct InfoContainer<Content: View>: View {
    @ViewBuilder var content: () -> Content
    
    var body: some View {
        VStack {
            content()
        }
        .fillMaxWidth()
        .padding(.horizontal, 24)
    }
}

struct InfoContainer_Preview: PreviewProvider {
    static var previews: some View {
        InfoContainer {
            Text("Children content")
        }
        .previewLayout(PreviewLayout.sizeThatFits)
    }
}
