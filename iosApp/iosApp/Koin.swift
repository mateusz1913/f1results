import Foundation
import shared

func startKoin() {
    let iosAppInfo: IosAppInfo = IosAppInfo()
    
    let koinApplication = KoinIOSKt.doInitKoinIos(appInfo: iosAppInfo)
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}

class IosAppInfo: AppInfo {
    let appId: String = Bundle.main.bundleIdentifier!
}
