package funn.j2k.math

class Primes : List<Long> {
    private val cache = sortedSetOf<Long>(2)

    private val primeGenerator = sequence<Long> {
        yield(2)
        var candidate = 3L
        while (true) {
            var isPrime = true
            cache.forEach {
                if (candidate % it == 0L) {
                    isPrime = false
                    return@forEach
                }
            }

            if (isPrime) {
                cache.add(candidate)
                yield(candidate)
            }

            candidate += 2
        }
    }

    fun getComputed() = cache.toList()

    override val size: Int
        get() = Int.MAX_VALUE

    override fun get(index: Int): Long {
        if (index > cache.size) {
            return primeGenerator.take(index - cache.size).last()
        }

        return cache.elementAt(index)
    }

    override fun isEmpty(): Boolean = cache.isEmpty()

    override fun iterator(): Iterator<Long> = primeGenerator.iterator()

    override fun listIterator(): ListIterator<Long> = ListIteratorImpl()

    override fun listIterator(index: Int): ListIterator<Long> = ListIteratorImpl(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<Long> {
        if (toIndex > cache.size) {
            primeGenerator.take(toIndex - cache.size + 1).last()
        }
        return cache.toList().subList(fromIndex, toIndex)
    }

    override fun lastIndexOf(element: Long): Int = indexOf(element)

    override fun indexOf(element: Long): Int {
        var index = cache.indexOf(element)

        if (index == -1) {
            primeGenerator.takeWhile { it != element }
            index = cache.size - 1
        }

        return index
    }

    override fun containsAll(elements: Collection<Long>): Boolean {
        return elements.all(::contains)
    }

    override fun contains(element: Long): Boolean {
        if (cache.last() < element) {
            return primeGenerator.takeWhile { it <= element }.last() == element
        }
        return cache.contains(element)
    }

    private inner class ListIteratorImpl(startIndex: Int = 0) : ListIterator<Long> {
        private var _nextIndex = startIndex

        override fun hasNext(): Boolean = true

        override fun hasPrevious(): Boolean = _nextIndex != 0

        override fun next(): Long = get(_nextIndex++)

        override fun nextIndex(): Int = _nextIndex

        override fun previous(): Long = get(--_nextIndex)

        override fun previousIndex(): Int = _nextIndex - 1
    }
}

fun Primes.binarySearch(fromIndex: Int = 0, toIndex: Int = -1, comparison: (Long) -> Int): Int =
    (this as List<Long>).binarySearch(
        fromIndex,
        if (toIndex == -1) comparison(0) * 2 else toIndex,
        comparison
    )
