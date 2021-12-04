@JsModule("collections/sorted-array")
@JsNonModule
external class SortedArray<T>(
    values: Array<out T>,
    equals: ((T, T) -> Boolean)?,
    compare: ((T, T) -> Int)?
) {
    fun add(value: T)
}

operator fun <T> SortedArray<T>.plusAssign(element: T) {
    add(element)
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

    return SortedArray(arrayOf(), equals, compare)
}
