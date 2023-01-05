import kotlin.math.max
import kotlin.math.pow

class Day25 {
    private val powerCountsMap = mutableMapOf<Int, Int>()
//    fun part1_(input: List<String>): Long {
//
//        while (currentCount !=) {
//            if (currentCount % 10000000L == 0L) println(currentCount)
//            var indexToIncrement: Int? = null
//            for (i in currentSnafu.size - 1 downTo 0) {
//                val c = currentSnafu[i]
//                if (getValue(c) < 2) {
//                    indexToIncrement = i
//                    break
//                }
//            }
//            if (indexToIncrement != null) {
//                currentSnafu[indexToIncrement] = increment(currentSnafu[indexToIncrement])
//                for (i in indexToIncrement + 1 until currentSnafu.size) currentSnafu[i] = '='
//            } else {
//                currentSnafu.add(0, '1')
//                for (i in 1 until currentSnafu.size) currentSnafu[i] = '='
//            }
//            currentCount++
//        }
//        println(currentSnafu)
//        println(currentCount)
//
////        var guess: Long = 0
////        while (guess != sum) {
////            var currentString = ""
////            for (i in 0..20) {
////                currentString +=
////            }
////        }
//        return sum
//    }

    private fun add(a: CharArray, b: CharArray): CharArray {
        val sum = mutableListOf<Char>()
        var remainder = 0
        val mappedA = a.map { if (it == '=') -2 else if (it == '-') -1 else it.digitToInt() }
        val mappedB = b.map { if (it == '=') -2 else if (it == '-') -1 else it.digitToInt() }
        var aIndex = mappedA.size - 1
        var bIndex = mappedB.size - 1
        while (aIndex >= 0 || bIndex >= 0 || remainder > 0) {
            val val1 = if (aIndex >= 0) mappedA[aIndex] else 0
            val val2 = if (bIndex >= 0) mappedB[bIndex] else 0
            val tempS = val1 + val2 + remainder
            var s: Int
            when (tempS) {
                3 -> {
                    s = -2
                    remainder = 1
                }

                4 -> {
                    s = -1
                    remainder = 1
                }
                5 -> {
                    s = 0
                    remainder = 1
                }
                -3 -> {
                    s = 2
                    remainder = -1
                }

                -4 -> {
                    s = 1
                    remainder = -1
                }
                -5 -> {
                    s = 0
                    remainder = -1
                }
                else -> {
                    s = tempS
                    remainder = 0
                }
            }
            sum.add(0, if (s == -1) '-' else if (s == -2) '=' else s.toString()[0])
            aIndex--
            bIndex--
        }
        return sum.toCharArray()
    }

    fun part1(input: List<String>) {
        var sum = "0".toCharArray()
        var sumLong = 0L
        for (line in input) {
            val currentValue = decode(line)
            println(currentValue)
            sumLong += currentValue
            sum = add(sum, line.toCharArray())
            println(sum)
        }
        println("Final")
        println(sum)
        println(decode(sum.joinToString("")))
        println(sumLong)
    }

    private fun decode(line: String): Long {
        var currentValue = 0L
        var power = 0
        for (c in line.length - 1 downTo 0) {
            val n = 5.0.pow(power)
            if (!powerCountsMap.containsKey(power)) {
                powerCountsMap[power] = 0
            }
            when (line[c]) {
                '=' -> {
                    currentValue -= (n.toLong() * 2)
                    powerCountsMap[power] = powerCountsMap[power]!! - 2
                }

                '-' -> {
                    currentValue -= n.toLong()
                    powerCountsMap[power] = powerCountsMap[power]!! - 1
                }

                else -> {
                    val count = line[c].digitToInt()
                    currentValue += (n.toLong() * count)
                    powerCountsMap[power] = powerCountsMap[power]!! + count
                }
            }
            power++
        }
        return currentValue
    }

    fun increment(c: Char): Char {
        return when (c) {
            '=' -> '-'
            '-' -> '0'
            '0' -> '1'
            '1' -> '2'
            else -> throw RuntimeException()
        }
    }

    fun getValue(c: Char): Int {
        return when (c) {
            '=' -> -2
            '-' -> -1
            else -> c.digitToInt()
        }
    }

}

fun main() {
    val input = readInput("input25_1")
    println(Day25().part1(input))
}