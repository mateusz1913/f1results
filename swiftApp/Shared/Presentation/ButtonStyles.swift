import SwiftUI

struct RowButtonStyle: ButtonStyle {
    var isDarkMode: Bool
    
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .background((isDarkMode ? Color.darkBackground : Color.lightBackground).shadow(radius: 2))
            .opacity(configuration.isPressed ? 0.4 : 1)
            .overlay(RoundedRectangle(cornerRadius: 5).stroke(Color.lightBackground, lineWidth: 1))
            .padding(.horizontal, 24)
            .padding(.vertical, 4)
    }
}

struct BorderlessRowButtonStyle: ButtonStyle {
    var isDarkMode: Bool
    
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .background(isDarkMode ? Color.darkBackground : Color.lightBackground)
            .opacity(configuration.isPressed ? 0.4 : 1)
    }
}
