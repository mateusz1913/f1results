import SwiftUI
import shared

struct Sidebar: View {
    var items: Array<NavigationItem>
    
    @State private var selectedScreen: Int? = 0
    
    var body: some View {
        List {
            ForEach(0..<items.count) { i in
                let item = items[i]
                NavigationLink(
                    destination: NavigationLazyView(
                        item.content().navigationTitle(item.labelImageText)
                    ).frame(minWidth: 500),
                    tag: i,
                    selection: $selectedScreen,
                    label: {
                        HStack {
                            if let image = NSImage(named: item.labelImageName) {
                                Image(nsImage: image)
                            }
                            Text(item.labelImageText)
                        }
                    }
                )
            }
        }
        .listStyle(SidebarListStyle())
        .frame(minWidth: 200, alignment: .center)
    }
}
