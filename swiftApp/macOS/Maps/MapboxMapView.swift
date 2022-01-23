import SwiftUI
import CoreLocation
import WebKit

struct MapboxMapView: NSViewRepresentable {
    private let webView: WKWebView = WKWebView()
    
    func makeNSView(context: NSViewRepresentableContext<MapboxMapView>) -> some WKWebView {
        webView.navigationDelegate = context.coordinator
        webView.uiDelegate = context.coordinator
        return webView
    }
    
    func updateNSView(_ nsView: NSViewType, context: Context) {}
    
    func makeCoordinator() -> MapboxMapView.Coordinator {
        Coordinator(self)
    }
    
    //MARK: Props
    func centerCoordinate(_ centerCoordinate: CLLocationCoordinate2D) -> MapboxMapView {
        webView.loadHTMLString("""
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Display a map on a webpage</title>
<meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no">
<link href="https://api.mapbox.com/mapbox-gl-js/v2.6.1/mapbox-gl.css" rel="stylesheet">
<script src="https://api.mapbox.com/mapbox-gl-js/v2.6.1/mapbox-gl.js"></script>
<style>
body { margin: 0; padding: 0; }
#map { position: absolute; top: 0; bottom: 0; width: 100%; }
</style>
</head>
<body>
<div id="map"></div>
<script>
    // TO MAKE THE MAP APPEAR YOU MUST
    // ADD YOUR ACCESS TOKEN FROM
    // https://account.mapbox.com
    mapboxgl.accessToken = 'pk.eyJ1IjoibWF0ZXVzejE5MTMiLCJhIjoiY2t5bTBic2Y5M2JsbjJ4cGJqaHAzbTh1eiJ9.NddT1twGmoTRaX9NA9bypg';
const center = [\(centerCoordinate.longitude), \(centerCoordinate.latitude)];
const map = new mapboxgl.Map({
container: 'map', // container ID
style: 'mapbox://styles/mapbox/satellite-v9', // style URL
center: center, // starting position [lng, lat]
zoom: 13.5 // starting zoom
});
const marker = new mapboxgl.Marker({ color: '#FF8C00' }).setLngLat(center).addTo(map);
</script>
 
</body>
</html>
""", baseURL: nil)
        return self
    }
    
    final class Coordinator: NSObject, WKNavigationDelegate, WKUIDelegate {
        var control: MapboxMapView
        
        init(_ control: MapboxMapView) {
            self.control = control
        }
    }
}
