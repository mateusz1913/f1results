import SwiftUI

@main
struct f1resultsApp: App {
    init() {
        startKoin()
        initNapier()
        NetworkMonitor.shared.startMonitoring()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
