import SwiftUI
import shared

struct ConstructorStandings: View {
    @EnvironmentObject var currentStandingsState: CurrentStandingsState
    
    @State private var selectedConstructor: String?
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        if currentStandingsState.constructorStandings == nil {
            emptyBody
        }
        currentStandingsState.constructorStandings.map { standings in
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
