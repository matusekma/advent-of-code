class MonkeyOperator11(private val monkeys: List<Monkey11>) {

    fun doRoundPart1() {
        for(monkey in monkeys) {
            val results = monkey.doRoundPart1()
            processMonkeyResults(results)
        }
    }

    fun doRoundPart2() {
        for(monkey in monkeys) {
            val results = monkey.doRoundPart2()
            processMonkeyResults(results)
        }
    }

    private fun processMonkeyResults(results: List<Map<String, Long>>) {
        for(result in results) {
            throwToMonkey(result["target"]!!.toInt(), result["item"]!!)
        }
    }

    private fun throwToMonkey(targetMonkeyNumber: Int, item: Long) {
        monkeys[targetMonkeyNumber].catchItem(item)
    }

    fun getMonkeyBusiness(): Long {
        val sortedMonkeys = monkeys.sortedBy { it.inspectionTimes }
        return sortedMonkeys[sortedMonkeys.size - 1].inspectionTimes * sortedMonkeys[sortedMonkeys.size - 2].inspectionTimes
    }
}