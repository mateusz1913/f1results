import SwiftUI
import MapboxMaps

struct MapboxMapView: UIViewRepresentable {
    private let mapView: MapView = MapView(frame: .zero, mapInitOptions: MapInitOptions(resourceOptions: ResourceOptions(accessToken: "pk.eyJ1IjoibWF0ZXVzejE5MTMiLCJhIjoiY2t5bTBic2Y5M2JsbjJ4cGJqaHAzbTh1eiJ9.NddT1twGmoTRaX9NA9bypg"), styleURI: .satellite))
    
    private let markerImage: PointAnnotation.Image = {
        var image: UIImage = UIImage(named: "map_marker")!
        return .init(image: image, name: "map_marker")
    }()
    
    func makeUIView(context: Context) -> some MapView {
        return mapView
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        //
    }
    
    func makeCoordinator() -> MapboxMapView.Coordinator {
        Coordinator(self)
    }
    
    //MARK: Props
    func centerCoordinate(_ centerCoordinate: CLLocationCoordinate2D) -> MapboxMapView {
        mapView.mapboxMap.setCamera(to: CameraOptions(center: centerCoordinate, zoom: 13.5))
        var pointAnnotation = PointAnnotation(coordinate: centerCoordinate)
        pointAnnotation.image = markerImage
        pointAnnotation.iconAnchor = .bottom
        let pointAnnotationManager = mapView.annotations.makePointAnnotationManager()
        pointAnnotationManager.annotations = [pointAnnotation]
        return self
    }
    
    final class Coordinator: NSObject {
        var control: MapboxMapView
        
        init(_ control: MapboxMapView) {
            self.control = control
        }
    }
}
