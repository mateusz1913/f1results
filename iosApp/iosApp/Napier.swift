import shared

func initNapier() {
    #if DEBUG
    NapierProxyKt.debugBuild()
    #endif
}

extension NapierNapier {
    static func v(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .verbose, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static func d(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .debug, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static func i(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .info, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static func w(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .warning, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static func e(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .error, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static func a(tag: String? = nil, _ items: Any..., separator: String = " ", file: String = #file, function: String = #function) {
        log(logLevel: .assert, tag: tag, items, separator: separator, file: file, function: function)
    }
    
    static private func log(logLevel: NapierLogLevel, tag: String?, _ items: [Any], separator: String, file: String, function: String) {
        let message = items.map { "\($0)" }.joined(separator: separator)
        shared.log(
            priority: logLevel,
            tag: tag ?? {
                let fileName = URL(fileURLWithPath: file).lastPathComponent
                let functionName: String
                if let firstBraceIndex = function.firstIndex(of: "(") {
                    functionName = String(function[..<firstBraceIndex])
                } else {
                    functionName = function
                }
                return "\(fileName):\(functionName)"
            }(),
            throwable: nil,
            message_: message
        )
    }
}

