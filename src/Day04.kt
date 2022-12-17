class Day04 {
    fun part1(input: List<String>): Int {
        var sum = 0
        for (row in input) {
            val (first, second) = row.split(',')
                .map { interval -> interval.split('-').map { it.toInt() } }
            if((first[0] <= second[0] && first[1] >= second[1]) ||
                (first[0] >= second[0] && first[1] <= second[1])) {
                sum++
            }
        }
        return sum
    }


    private fun itemToValue(it: Char) = if (it.isUpperCase()) it.code - 38 else it.code - 96

    fun part2(input: List<String>): Int {
        var sum = 0
        for (row in input) {
            val (first, second) = row.split(',')
                .map { interval -> interval.split('-').map { it.toInt() } }
            if(!(first[0]..first[1]).toSet().intersect((second[0]..second[1]).toList().toSet()).isEmpty()) {
                sum++
            }
        }
        return sum
    }
}

fun main() {
    val day04 = Day04()
    val input = readInput("input04_1")
    println(day04.part1(input))
    println(day04.part2(input))
}