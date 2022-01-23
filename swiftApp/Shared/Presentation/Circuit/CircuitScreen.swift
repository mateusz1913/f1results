import CoreLocation
import SwiftUI
import shared

struct CircuitScreen: View {
    @ObservedObject var circuitState: CircuitState
    
    var body: some View {
        if let circuit = circuitState.circuit {
            VStack {
                if let latString = circuit.location.lat, let longString = circuit.location.long_, let latitude = Double(latString), let longitude = Double(longString) {
                    MapboxMapView()
                        .centerCoordinate(CLLocationCoordinate2D(latitude: latitude, longitude: longitude))
                        .frame(maxWidth: .infinity, minHeight: 250)
                }
                VStack {
                    Text(circuit.circuitName)
                        .font(.system(size: 30))
                        .fontWeight(.semibold)
                }
                .frame(maxWidth: .infinity)
                .padding(.horizontal, 24)
                .padding(.vertical, 10)
            }.frame(maxWidth: .infinity, maxHeight: .infinity)
        } else {
            Text("No circuit")
        }
    }
}
