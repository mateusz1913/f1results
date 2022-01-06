import Foundation
import shared

func startKoin() {
    let iosAppInfo: SwiftAppInfo = SwiftAppInfo()
    
    let koinApplication = KoinSwiftKt.doInitKoinSwift(appInfo: iosAppInfo)
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}

class SwiftAppInfo: AppInfo {
    let appId: String = Bundle.main.bundleIdentifier!
}
