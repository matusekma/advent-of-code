data class Monkey11(
    val itemWorryLevels: MutableList<Long>,
    val operation: (Long) -> Long,
    val targetTest: (Long) -> Long,
    val divisor: Long
) {
    var inspectionTimes = 0L

    fun doRoundPart1(): List<Map<String, Long>> {
        val result = itemWorryLevels.map { itemWorryLevel ->
            inspectAndGetNewTarget(itemWorryLevel)
        }
        itemWorryLevels.clear()
        return result
    }

    fun doRoundPart2(): List<Map<String, Long>> {
        val result = itemWorryLevels.map { item ->
            inspectAndGetNewTarget2(item)
        }
        itemWorryLevels.clear()
        return result
    }

    private fun inspectAndGetNewTarget(itemWorryLevel: Long): Map<String, Long> {
        inspectionTimes++
        var newItemWorryLevel = operation(itemWorryLevel)
        newItemWorryLevel /= 3
        return mapOf(
            "item" to newItemWorryLevel,
            "target" to targetTest(newItemWorryLevel)
        )
    }

    private fun inspectAndGetNewTarget2(itemWorryLevel: Long): Map<String, Long> {
        inspectionTimes++
        var newItemWorryLevel = operation(itemWorryLevel) % (2 * 3 * 5 * 7 * 11 * 13 * 17 * 19).toLong()
        return mapOf(
            "item" to newItemWorryLevel,
            "target" to targetTest(newItemWorryLevel)
        )
    }

//    private fun inspectAndGetNewTargetPart2(item: Item): Result {
//        inspectionTimes++
//        val newMap = mutableMapOf<Long, Long>()
//        item.itemModsToValue.entries
//            .forEach { (mod, value) -> newMap[mod] = operation(value) % mod }
//        item.itemModsToValue = newMap
//        item.value = operation(item.value)
//        return try {
//            Result(item, targetTest(item.itemModsToValue[divisor]!!))
//        } catch (error: Exception) {
//            throw error
//        }
//    }

    fun catchItem(item: Long) {
        itemWorryLevels.add(item)
    }

}