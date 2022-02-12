import SwiftUI
import MapboxMaps

struct ExploreScreen: View {
    var body: some View {
        VStack(alignment: .center) {
            Spacer(minLength: 50)
            ScrollView {
                ExploreRow(label: "Archival races", iconName: "round_sports_score_black_24pt") {
                    //
                }
                ExploreRow(label: "Archival standings", iconName: "round_emoji_events_black_24pt") {
                    //
                }
                ExploreRow(label: "Drivers", iconName: "round_sports_motorsports_black_24pt") {
                    //
                }
                ExploreRow(label: "Constructors", iconName: "round_groups_black_24pt") {
                    //
                }
                ExploreRow(label: "Settings", iconName: "round_settings_black_24pt") {
                    //
                }
            }
            .fillMaxSize()
            .padding(.horizontal, 24)
        }
        .fillMaxSize()
    }
}

struct ExploreRow: View {
    let label: String
    let iconName: String
    let onPress: () -> Void
    
    var body: some View {
        Button(action: onPress, label: {
            HStack {
                Text(label.uppercased())
                    .font(.system(size: 24))
                    .fontWeight(.bold)
                Spacer()
                #if os(iOS)
                if let image = UIImage(named: iconName) {
                    Image(uiImage: image)
                }
                #elseif os(macOS)
                if let image = NSImage(named: iconName) {
                    Image(nsImage: image)
                }
                #endif
            }
            .contentShape(Rectangle())
            .fillMaxWidth()
        })
            .buttonStyle(ExploreRowButtonStyle())
            .fillMaxWidth()
            .padding(.vertical, 5)
    }
}

struct ExploreRowButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration
            .label
            .foregroundColor(Color.primaryColor)
            .opacity(configuration.isPressed ? 0.4 : 1)
    }
}
