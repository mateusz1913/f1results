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
