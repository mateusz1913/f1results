import shared

extension Array where Element: AnyObject{
    init(_ kotlinArray: KotlinArray<Element>) {
        self.init()
        let iterator = kotlinArray.iterator()
        while iterator.hasNext() {
            self.append(iterator.next() as! Element)
        }
    }
}
