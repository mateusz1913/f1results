import SwiftUI
import shared

struct ConstructorStandings: View {
    var constructorStandings: ConstructorStandingsType? = nil
    
    @State private var selectedConstructor: String?
    
    var body: some View {
        VStack {
            if let constructorStandings = constructorStandings {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<constructorStandings.constructorStandings.size, id: \.self) { i in
                            if let standing = constructorStandings.constructorStandings.get(index: i) {
                                let text = "\(standing.position): \(standing.constructor.name) - \(standing.points)"
                                
                                NavigationLink(
                                    destination: NavigationLazyView(ConstructorScreen(constructorState: ConstructorState(constructorId: standing.constructor.constructorId))),
                                    tag: standing.constructor.constructorId,
                                    selection: $selectedConstructor
                                ) {
                                    Text(text)
                                        .padding(4)
                                        .frame(maxWidth: .infinity, alignment: .leading)
                                        .border(.orange, width: 1)                                    
                                }
                            }
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else {
                Text("Constructor standings")
            }
        }
    }
}
