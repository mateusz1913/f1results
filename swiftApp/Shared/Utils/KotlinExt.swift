import shared

extension Array where Element: AnyObject {
    init(_ kotlinArray: KotlinArray<Element>) {
        self.init()
        let iterator = kotlinArray.iterator()
        while iterator.hasNext() {
            self.append(iterator.next() as! Element)
        }
    }
}

func KotlinArrayFromArray<T: AnyObject>(_ array: Array<T>) -> KotlinArray<T> {
    KotlinArray.init(size: Int32(array.count)) { i in
        array[i.intValue]
    }
}
