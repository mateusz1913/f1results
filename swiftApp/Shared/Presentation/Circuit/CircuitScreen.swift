import CoreLocation
import SwiftUI
import shared

struct CircuitScreen: View {
    @ObservedObject var circuitState: CircuitState
    
    var body: some View {
        if circuitState.circuitIsFetching && circuitState.circuit == nil {
            loadingBody
        } else if circuitState.circuit == nil {
            emptyBody
        } else {
            resultBody
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("No circuit")
        }
        .fillMaxSize()
    }
    
    private var loadingBody: some View {
        VStack {
            Loading()
        }
        .fillMaxSize()
    }
    
    private var resultBody: some View {
        circuitState.circuit.map { circuit in
            VStack {
                if let latString = circuit.location.lat, let longString = circuit.location.long_, let latitude = Double(latString), let longitude = Double(longString) {
                    CircuitLayoutWithMap(latitude: latitude, longitude: longitude) {
                        VStack {
                            VStack {
                                InfoRow(fontSize: 20, fontWeight: .bold, label: "Circuit: ", value: circuit.circuitName)
                            }
                            .padding(.init(top: 24, leading: 20, bottom: 0, trailing: 20))
                            HStack {
                                VStack {
                                    InfoRow(fontSize: 16, fontWeight: .semibold, label: "Locality: ", value: circuit.location.locality)
                                }
                                .fillMaxWidth()
                                .padding(.vertical, 10)
                                VStack {
                                    InfoRow(fontSize: 16, fontWeight: .semibold, label: "Country: ", value: circuit.location.country)
                                }
                                .fillMaxWidth()
                                .padding(.vertical, 10)
                            }
                        }
                        .fillMaxWidth()
                    }
                }
            }
            .fillMaxSize()
        }
    }
}
