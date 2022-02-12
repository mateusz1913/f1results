import SwiftUI

extension View {
    func fillMaxHeight() -> some View {
        self.frame(minHeight: 0, maxHeight: .infinity)
    }
    
    func fillMaxHeight(alignment: Alignment) -> some View {
        self.frame(minHeight: 0, maxHeight: .infinity, alignment: alignment)
    }
    
    func fillMaxSize() -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
    }
    
    func fillMaxSize(alignment: Alignment) -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: alignment)
    }
    
    func fillMaxWidth() -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity)
    }
    
    func fillMaxWidth(alignment: Alignment) -> some View {
        self.frame(minWidth: 0, maxWidth: .infinity, alignment: alignment)
    }
    
    func size(_ size: CGFloat) -> some View {
        self.frame(width: size, height: size)
    }
    
    func size(_ size: CGFloat, alignment: Alignment) -> some View {
        self.frame(width: size, height: size, alignment: alignment)
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
