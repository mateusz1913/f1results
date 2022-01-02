import SwiftUI

@main
struct iOSApp: App {
    init() {
        startKoin()
        initNapier()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
