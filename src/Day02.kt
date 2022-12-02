class Day02 {
    private val scoreMap1 = mapOf<String, Int>(
        "A X" to 1 + 3,
        "A Y" to 2 + 6,
        "A Z" to 3 + 0,
        "B X" to 1 + 0,
        "B Y" to 2 + 3,
        "B Z" to 3 + 6,
        "C X" to 1 + 6,
        "C Y" to 2 + 0,
        "C Z" to 3 + 3,
    )

    private val scoreMap2 = mapOf(
        "A X" to 3 + 0,
        "A Y" to 1 + 3,
        "A Z" to 2 + 6,
        "B X" to 1 + 0,
        "B Y" to 2 + 3,
        "B Z" to 3 + 6,
        "C X" to 2 + 0,
        "C Y" to 3 + 3,
        "C Z" to 1 + 6,
    )

    fun part1(input: List<String>): Int = input.fold(0) { acc, round ->
        acc + scoreMap1[round]!!
    }


    fun part2(input: List<String>): Int = input.fold(0) { acc, round ->
        acc + scoreMap2[round]!!
    }
}

fun main() {
    val day02 = Day02()
    val input = readInput("input02_1")
    println(day02.part1(input))
    println(day02.part2(input))
}
