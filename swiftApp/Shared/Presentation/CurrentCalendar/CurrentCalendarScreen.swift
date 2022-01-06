import SwiftUI
import shared

struct CurrentCalendarScreen: View {
    @ObservedObject var networkConnectionState = NetworkMonitor.shared.state
    
    var body: some View {
        VStack {
            Text("Current calendar \(networkConnectionState.isReachable ? "Connected" : "Not connected")")
        }
    }
}
