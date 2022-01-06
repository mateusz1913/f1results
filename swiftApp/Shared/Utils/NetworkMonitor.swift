import Network
import SwiftUI

class NetworkMonitor {
    static let shared = NetworkMonitor()
    
    private let monitor = NWPathMonitor()
    private var status: NWPath.Status = .requiresConnection

    var state = NetworkMonitorState()
    
    private init() {}
    
    deinit {
        stopMonitoring()
    }
    
    private func onUpdate(_ path: NWPath) {
        DispatchQueue.main.async {
            self.status = path.status
            self.state.isReachable = path.status == .satisfied
            self.state.isReachableOnCellular = path.isExpensive
        }
    }
    
    func startMonitoring() {
        monitor.pathUpdateHandler = { [weak self] path in
            if let strongSelf = self {
                strongSelf.onUpdate(path)
            }
        }
        
        let queue = DispatchQueue(label: "networkMonitor")
        monitor.start(queue: queue)
    }
    
    func stopMonitoring() {
        monitor.cancel()
    }
}

class NetworkMonitorState: ObservableObject {
    @Published var isReachable: Bool = false
    @Published var isReachableOnCellular: Bool = false
}
