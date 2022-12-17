class Day03 {
    fun part1(input: List<String>): Int {
        var sum = 0
        for(rucksack in input) {
            var firstComp = rucksack.substring(0, rucksack.length / 2).toCharArray().toMutableSet()
            var secondComp = rucksack.substring(rucksack.length / 2).toCharArray().toMutableSet()
            firstComp.retainAll(secondComp.toSet())
            sum += firstComp.sumOf { itemToValue(it) }
        }
        return sum
    }

    private fun itemToValue(it: Char) = if (it.isUpperCase()) it.code - 38 else it.code - 96

    fun part2(input: List<String>): Int {
        var sum = 0
        for(i in 0 .. input.size - 3 step 3){
            var elfes = input.subList(i, i + 3)
            var common = elfes[0].toList().intersect(elfes[1].toList().toSet()).intersect(elfes[2].toList().toSet())
            sum += common.sumOf { itemToValue(it) }
        }
        return sum
    }
}

fun main() {
    val day03 = Day03()
    val input = readInput("input03_1")
    println(day03.part1(input))
    println(day03.part2(input))
}