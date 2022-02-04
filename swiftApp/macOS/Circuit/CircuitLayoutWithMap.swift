import CoreLocation
import SwiftUI

struct CircuitLayoutWithMap<Content: View>: View {
    var latitude: Double
    var longitude: Double
    @ViewBuilder var content: () -> Content
    
    var body: some View {
        VStack {
            MapboxMapView()
                .centerCoordinate(CLLocationCoordinate2D(latitude: latitude, longitude: longitude))
                .size(500)
            content()
        }
    }
}
