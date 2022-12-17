class Day05 {
    
    private val stacks = listOf(
            ArrayDeque("T,V,J,W,N,R,M,S".split(',').reversed()), ArrayDeque(("V," +
                    "C," +
                    "P," +
                    "Q," +
                    "J," +
                    "D," +
                    "W," +
                    "B").split(',').reversed())
            , ArrayDeque(("P," +
                    "R," +
                    "D," +
                    "H," +
                    "F," +
                    "J," +
                    "B").split(',').reversed()), ArrayDeque(("D," +
                    "N," +
                    "M," +
                    "B," +
                    "P," +
                    "R," +
                    "F").split(',').reversed()), ArrayDeque(("B," +
                    "T," +
                    "P," +
                    "R," +
                    "V," +
                    "H").split(',').reversed()), ArrayDeque(("T," +
                    "P," +
                    "B," +
                    "C").split(',').reversed()), ArrayDeque(("L," + "P," +
                    "R," +
                    "J," +
                    "B").split(',').reversed()), ArrayDeque(("W," +
                    "B," +
                    "Z," +
                    "T," +
                    "L," +
                    "S," +
                    "C," +
                    "N").split(',').reversed()),
            ArrayDeque(("G," +
                    "S," +
                    "L").split(',').reversed())
    )
    private val stacks2 = listOf(
        ArrayDeque("T,V,J,W,N,R,M,S".split(',').reversed()), ArrayDeque(("V," +
                "C," +
                "P," +
                "Q," +
                "J," +
                "D," +
                "W," +
                "B").split(',').reversed())
        , ArrayDeque(("P," +
                "R," +
                "D," +
                "H," +
                "F," +
                "J," +
                "B").split(',').reversed()), ArrayDeque(("D," +
                "N," +
                "M," +
                "B," +
                "P," +
                "R," +
                "F").split(',').reversed()), ArrayDeque(("B," +
                "T," +
                "P," +
                "R," +
                "V," +
                "H").split(',').reversed()), ArrayDeque(("T," +
                "P," +
                "B," +
                "C").split(',').reversed()), ArrayDeque(("L," + "P," +
                "R," +
                "J," +
                "B").split(',').reversed()), ArrayDeque(("W," +
                "B," +
                "Z," +
                "T," +
                "L," +
                "S," +
                "C," +
                "N").split(',').reversed()),
        ArrayDeque(("G," +
                "S," +
                "L").split(',').reversed())
    )

    fun part1(input: List<String>): String {
        for(line in input) {
            val (quantity, from, to) = getOperation(line);
            for(i in 1 .. quantity) {
                stacks[to-1].add(stacks[from - 1].removeLast())
            }
        }
        var top = ""
        stacks.forEach {
            top += it.last()
        }
        return top
    }

    fun getOperation(op: String): List<Int> {
        val rest = op.split("move ")[1]
        val quantity = rest.split(" from ")[0].toInt()
        val to = rest.split(" to ")[1].toInt()
        val from = rest.split(" from ")[1].split(" to ")[0].toInt()
        return listOf(quantity, from, to)
    }

    fun part2(input: List<String>): String {
        for(line in input) {
            val (quantity, from, to) = getOperation(line);
            val toMove = mutableListOf<String>()
            for(i in 1 .. quantity) {
                toMove.add(stacks2[from - 1].removeLast())
            }
            stacks2[to-1].addAll(toMove.reversed())
        }
        var top = ""
        stacks2.forEach {
            top += it.last()
        }
        return top
    }
}

fun main() {
    val day05 = Day05()
    val input = readInput("input05_1")
    println(day05.part1(input))
    println(day05.part2(input))
}