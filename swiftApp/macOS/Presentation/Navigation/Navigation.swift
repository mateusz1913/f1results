import SwiftUI

struct Navigation: View {
    var items: Array<NavigationItem>
    
    var body: some View {
        NavigationView {
            Sidebar(items: items)
            
            EmptyView()
            
            Text("Hello world!")
        }
        .toolbar {
            ToolbarItem(placement: ToolbarItemPlacement.navigation) {
                Button {
                    NSApp.keyWindow?.firstResponder?.tryToPerform(#selector(NSSplitViewController.toggleSidebar(_:)), with: nil)
                } label: {
                    Label("Toggle sidebar", systemImage: "sidebar.left")
                }
            }
        }
        .frame(minWidth: 1200, minHeight: 800, alignment: .center)
    }
}
