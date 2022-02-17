package collectionsjs

@JsModule("collections/sorted-array")
@JsNonModule
private external class SortedArrayJs<T>(
    values: Array<out T>,
    equals: ((T, T) -> Boolean)?,
    compare: ((T, T) -> Int)?
) {
    val array: Array<out T>
    val length: Int

    fun add(value: T)
}

class SortedArray<T>(
    equals: ((T, T) -> Boolean)?,
    compare: ((T, T) -> Int)?
) {
    private val sortedArrayJs: SortedArrayJs<T> = SortedArrayJs(arrayOf(), equals, compare)

    val size: Int
        get() = sortedArrayJs.length

    fun iterator(): Iterator<T> {
        return sortedArrayJs.array.iterator()
    }

    fun add(element: T) {
        sortedArrayJs.add(element)
    }

    operator fun plusAssign(element: T) {
        add(element)
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun mutableSlice(fromIndex: Int, toIndex: Int): MutableList<T> {
        return sortedArrayJs.array.slice(fromIndex..toIndex).toMutableList()
    }
}

inline fun <T, R : Comparable<R>> sortedArrayOf(
    crossinline selector: (T) -> R
): SortedArray<T> {
    val compare = { a: T, b: T ->
        selector(a).compareTo(selector(b))
    }
    val equals = { a: T, b: T ->
        selector(a) == selector(b)
    }

    return SortedArray(equals, compare)
}
