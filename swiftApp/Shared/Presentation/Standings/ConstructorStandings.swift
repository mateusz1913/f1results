import SwiftUI
import shared

struct ConstructorStandings: View {
    var constructorStandings: ConstructorStandingsType? = nil
    
    @State private var selectedConstructor: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        if constructorStandings == nil {
            emptyBody
        }
        constructorStandings.map { standings in
            ScrollView {
                LazyVStack {
                    ForEach(0..<standings.constructorStandings.size, id: \.self) { i in
                        if let standing = standings.constructorStandings.get(index: i) {
                            Button {
                                selectedConstructor = standing.constructor.constructorId
                            } label: {
                                ConstructorStandingRow(constructorStanding: standing)
                            }
                            .buttonStyle(RowButtonStyle(isDarkMode: colorScheme == .dark))
                            NavigationLink(
                                destination: NavigationLazyView(ConstructorScreen(constructorState: ConstructorState(constructorId: standing.constructor.constructorId))),
                                tag: standing.constructor.constructorId,
                                selection: $selectedConstructor
                            ) {
                                EmptyView()
                            }
                            .makeInvisibleOnMacOS()
                        }
                    }
                }
            }
            .fillMaxSize()
        }
    }
    
    private var emptyBody: some View {
        VStack {
            Text("TBD")
        }
        .fillMaxSize()
    }
}
