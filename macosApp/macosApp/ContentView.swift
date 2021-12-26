import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greeting()
    
    var body: some View {
        VStack {
            Text(greet)
                .padding()
        }.frame(width: 600, height: 600, alignment: .center)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
