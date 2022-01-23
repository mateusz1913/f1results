import SwiftUI
import Mapbox

struct MapboxMapView: NSViewRepresentable {
    private let mapView: MGLMapView = MGLMapView(frame: .zero, styleURL: MGLStyle.satelliteStyleURL)
    
    func makeNSView(context: NSViewRepresentableContext<MapboxMapView>) -> some MGLMapView {
        mapView.delegate = context.coordinator
        return mapView
    }
    
    func updateNSView(_ nsView: NSViewType, context: Context) {
        //
    }
    
    func makeCoordinator() -> MapboxMapView.Coordinator {
        Coordinator(self)
    }
    
    //MARK: Props
    func centerCoordinate(_ centerCoordinate: CLLocationCoordinate2D) -> MapboxMapView {
        mapView.centerCoordinate = centerCoordinate
        mapView.zoomLevel = 13.5
        return self
    }
    
    final class Coordinator: NSObject, MGLMapViewDelegate {
        var control: MapboxMapView
        
        init(_ control: MapboxMapView) {
            self.control = control
        }
    }
}
