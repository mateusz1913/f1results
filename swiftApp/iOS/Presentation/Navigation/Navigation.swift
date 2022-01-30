import SwiftUI

struct Navigation: View {
    var items: Array<NavigationItem>
    
    var body: some View {
        NavigationView {
            TabView {
                ForEach(0..<items.count) { i in
                    let item = items[i]
                    item
                        .content()
                        .tabItem {
                            Label(item.labelImageText, image: item.labelImageName)
                        }
                        .navigationTitle(item.labelImageText)
                }
            }
        }
    }
}
