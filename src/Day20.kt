data class Number(val value: Int, val priority: Int)
data class LongNumber(val value: Long, val priority: Int)

class Day20 {
    fun part1(input: List<String>): Int {
        val numberArray = input.mapIndexed { i, value -> Number(value.toInt(), i + 1) }.toMutableList()
        val size = numberArray.size
        for (priority in 1..size) {
            val i = numberArray.indexOfFirst { it.priority == priority }
            val number = numberArray[i]
            val n = number.value
            if (n == 0) continue

            mixNumber(i, n, size, numberArray, number)
            println(numberArray.joinToString(",") { it.value.toString() })
        }
        val indexOfZero = numberArray.indexOfFirst { it.value == 0 }
        val i1 = getNthIndexAfter(indexOfZero, 1000, size)
        val i2 = getNthIndexAfter(indexOfZero, 2000, size)
        val i3 = getNthIndexAfter(indexOfZero, 3000, size)
        return numberArray[i1].value + numberArray[i2].value + numberArray[i3].value
    }

    fun mixNumber(
        i: Int,
        n: Int,
        size: Int,
        numberArray: MutableList<Number>,
        number: Number
    ) {
        val moveBy = n % (size-1)
        if (moveBy < 0) {
            var currentIndex = i
            repeat(-moveBy) {
                numberArray.removeAt(currentIndex)
                if (currentIndex == 0) {        // rotate
                    currentIndex = size - 2
                } else {
                    currentIndex -= 1
                }
                numberArray.add(currentIndex, number)
            }
        } else {
            var currentIndex = i
            repeat(moveBy) {
                numberArray.removeAt(currentIndex)
                if (currentIndex == size - 1) {     // rotate
                    currentIndex = 1
                } else {
                    currentIndex += 1
                }
                numberArray.add(currentIndex, number)
            }
        }
    }

    fun mixNumberLong(
        i: Int,
        n: Long,
        size: Int,
        numberArray: MutableList<LongNumber>,
        number: LongNumber
    ) {
        val moveBy = n % (size-1)
        if (moveBy < 0) {
            var currentIndex = i
            repeat(-moveBy.toInt()) {
                numberArray.removeAt(currentIndex)
                if (currentIndex == 0) {        // rotate
                    currentIndex = size - 2
                } else {
                    currentIndex -= 1
                }
                numberArray.add(currentIndex, number)
            }
        } else {
            var currentIndex = i
            repeat(moveBy.toInt()) {
                numberArray.removeAt(currentIndex)
                if (currentIndex == size - 1) {     // rotate
                    currentIndex = 1
                } else {
                    currentIndex += 1
                }
                numberArray.add(currentIndex, number)
            }
        }
    }

    private fun getNthIndexAfter(i: Int, after: Int, size: Int): Int {
        return (i + after) % size
    }

    fun part2(input: List<String>): Long {
        val numberArray = input.mapIndexed { i, value -> LongNumber(value.toLong() * 811589153, i + 1) }.toMutableList()
        val size = numberArray.size
        repeat(10) {
            for (priority in 1..size) {
                val i = numberArray.indexOfFirst { it.priority == priority }
                val number = numberArray[i]
                val n = number.value
                if (n == 0L) continue

                mixNumberLong(i, n, size, numberArray, number)
                //println(numberArray.joinToString(",") { it.value.toString() })
            }
        }
        val indexOfZero = numberArray.indexOfFirst { it.value == 0L }
        val i1 = getNthIndexAfter(indexOfZero, 1000, size)
        val i2 = getNthIndexAfter(indexOfZero, 2000, size)
        val i3 = getNthIndexAfter(indexOfZero, 3000, size)
        return numberArray[i1].value + numberArray[i2].value + numberArray[i3].value
    }
}

fun main() {
    val input = readInput("input20_1")
    println(Day20().part1(input))
    println(Day20().part2(input))
}