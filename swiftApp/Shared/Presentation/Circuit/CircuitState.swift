import Foundation
import SwiftUI
import shared

class CircuitState: ObservableObject {
    let circuitId: String
    let viewModel: CircuitViewModel
    
    @Published private(set) var circuit: CircuitType? = nil
    
    init(circuitId: String) {
        self.circuitId = circuitId
        viewModel = koin.get(with: circuitId)
        viewModel.observeCircuit { circuitState in
            self.circuit = circuitState.circuit
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
