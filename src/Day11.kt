class Day11 {
    private val monkey0 = Monkey11(
        mutableListOf(77, 69, 76, 77, 50, 58),
        { item -> item * 11 },
        { worryLevel -> if (worryLevel % 5 == 0L) 1 else 5 },5)
    private val monkey1 = Monkey11(
        mutableListOf(75, 70, 82, 83, 96, 64, 62),
        { item -> item + 8 },
        { worryLevel -> if (worryLevel % 17 == 0L) 5 else 6 }, 17)
    private val monkey2 = Monkey11(
        mutableListOf(53),
        { item -> item * 3 },
        { worryLevel -> if (worryLevel % 2 == 0L) 0 else 7 }, 2)
    private val monkey3 = Monkey11(
        mutableListOf(85, 64, 93, 64, 99),
        { item -> item + 4 },
        { worryLevel -> if (worryLevel % 7 == 0L) 7 else 2 }, 7)
    private val monkey4 = Monkey11(
        mutableListOf(61, 92, 71),
        { item -> item * item },
        { worryLevel -> if (worryLevel % 3 == 0L) 2 else 3 },3)
    private val monkey5 = Monkey11(
        mutableListOf(79, 73, 50, 90),
        { item -> item + 2 },
        { worryLevel -> if (worryLevel % 11 == 0L) 4 else 6 },11)
    private val monkey6 = Monkey11(
        mutableListOf(50, 89),
        { item -> item + 3 },
        { worryLevel -> if (worryLevel % 13 == 0L) 4 else 3 }, 13)
    private val monkey7 = Monkey11(
        mutableListOf(83, 56, 64, 58, 93, 91, 56, 65),
        { item -> item + 5 },
        { worryLevel -> if (worryLevel % 19 == 0L) 1 else 0 }, 19)
    private val monkeyOperator11 =
        MonkeyOperator11(listOf(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7))

    fun part1(): Long {
        for (round in 1..20) {
            monkeyOperator11.doRoundPart1()
        }
        return monkeyOperator11.getMonkeyBusiness()
    }


    fun part2(): Long {
        for (round in 1..10_000) {
            monkeyOperator11.doRoundPart2()
        }
        return monkeyOperator11.getMonkeyBusiness()
    }
}

fun main() {
    println(Day11().part1())
    println(Day11().part2())
}