import SwiftUI

struct NavigationItem {
    var labelImageName: String
    var labelImageText: String
    @ViewBuilder var content: () -> AnyView
}
