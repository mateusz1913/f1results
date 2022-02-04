import SwiftUI

extension View {
    func fillMaxHeight() -> some View {
        self.frame(minHeight: 0, maxHeight: .infinity)
    }
    
    func fillMaxSize() -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
    }
    
    func fillMaxWidth() -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity)
    }
    
    func size(_ size: CGFloat) -> some View {
        self.frame(width: size, height: size)
    }
}

extension NavigationLink {
    func makeInvisibleOnMacOS() -> some View {
        self
            #if os(macOS)
            .frame(width: 0, height: 0)
            .background(Color.transparent)
            .opacity(0)
            #endif
    }
}
