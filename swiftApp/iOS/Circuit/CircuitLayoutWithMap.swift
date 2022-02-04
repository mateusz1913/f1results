import CoreLocation
import SwiftUI
import BottomSheetSwiftUI

enum MapDetailsBottomSheetPosition: CGFloat, CaseIterable {
    case middle = 0.33
    case bottom = 0.1
}

struct CircuitLayoutWithMap<Content: View>: View {
    var latitude: Double
    var longitude: Double
    @ViewBuilder var content: () -> Content
    
    @State private var bottomSheetPosition: MapDetailsBottomSheetPosition = .bottom
    
    var body: some View {
        MapboxMapView()
            .centerCoordinate(CLLocationCoordinate2D(latitude: latitude, longitude: longitude))
            .fillMaxWidth()
            .frame(minHeight: 250)
            .bottomSheet(bottomSheetPosition: self.$bottomSheetPosition, options: [], headerContent: {}, mainContent: content)
    }
}
