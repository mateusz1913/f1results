import SwiftUI
import shared

struct QualifyingResults: View {
    var qualifyingResults: RaceWithQualifyingResultsType? = nil
    
    var body: some View {
        VStack {
            if let results = qualifyingResults {
                ScrollView {
                    LazyVStack {
                        ForEach(0..<results.qualifyingResults.size, id: \.self) { i in
                            if let result = results.qualifyingResults.get(index: i) {
                                let text = "\(result.position): \(result.driver.givenName) \(result.driver.familyName) - \(result.constructor.name)"
                                Text(text)
                                    .padding(4)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .border(.orange, width: 1)
                            }
                        }
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
    }
}
