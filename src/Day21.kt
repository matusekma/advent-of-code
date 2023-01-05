data class Monkey(
    val name: String,
    val op: String,
    val monkey1: String,
    val monkey2: String
)

class Day21 {
    fun part1(input: List<String>): Long {
        val readyMonkeyMap = mutableMapOf<String, Long>()
        val monkeyMap = mutableMapOf<String, Monkey>()
        val remainingMonkeyNames = mutableListOf<String>()
        input.forEach {
            val splitLine = it.split(": ")
            val monkeyName = splitLine[0]
            val rest = splitLine[1]
            val number = rest.toLongOrNull()
            if (number == null) {
                val (monkey1, op, monkey2) = rest.split(" ")
                monkeyMap[monkeyName] = Monkey(monkeyName, op, monkey1, monkey2)
                remainingMonkeyNames.add(monkeyName)
            } else {
                readyMonkeyMap[monkeyName] = number
            }
        }

        while (remainingMonkeyNames.isNotEmpty() && !readyMonkeyMap.containsKey("root")) {
            val iterator = remainingMonkeyNames.listIterator()
            while (iterator.hasNext()) {
                val monkeyName = iterator.next()
                val monkey = monkeyMap[monkeyName]!!
                if (readyMonkeyMap.containsKey(monkey.monkey1) && readyMonkeyMap.containsKey(monkey.monkey2)) {
                    val monkey1Value = readyMonkeyMap[monkey.monkey1]!!
                    val monkey2Value = readyMonkeyMap[monkey.monkey2]!!
                    readyMonkeyMap[monkeyName] = monkey1Value.operation(monkey.op, monkey2Value)
                    iterator.remove()
                }
            }
        }

        return readyMonkeyMap["root"]!!
    }


//    fun part2(input: List<String>): Long {
//        var guess = 500000L
//        while (true) {
//            val readyMonkeyMap = mutableMapOf<String, Long>()
//            val monkeyMap = mutableMapOf<String, Monkey>()
//            val remainingMonkeyNames = mutableListOf<String>()
//            input.forEach {
//                val splitLine = it.split(": ")
//                val monkeyName = splitLine[0]
//                val rest = splitLine[1]
//                val number = rest.toLongOrNull()
//                if (number == null) {
//                    val (monkey1, op, monkey2) = rest.split(" ")
//                    monkeyMap[monkeyName] = Monkey(monkeyName, op, monkey1, monkey2)
//                    remainingMonkeyNames.add(monkeyName)
//                } else {
//                    readyMonkeyMap[monkeyName] = number
//                }
//            }
//            readyMonkeyMap["humn"] = guess
//
//            while (remainingMonkeyNames.isNotEmpty() && !readyMonkeyMap.containsKey("root")) {
//                val iterator = remainingMonkeyNames.listIterator()
//                while (iterator.hasNext()) {
//                    val monkeyName = iterator.next()
//                    val monkey = monkeyMap[monkeyName]!!
//                    if (readyMonkeyMap.containsKey(monkey.monkey1) && readyMonkeyMap.containsKey(monkey.monkey2)) {
//                        val monkey1Value = readyMonkeyMap[monkey.monkey1]!!
//                        val monkey2Value = readyMonkeyMap[monkey.monkey2]!!
//                        readyMonkeyMap[monkeyName] = monkey1Value.operation(monkey.op, monkey2Value)
//                        iterator.remove()
//                    }
//                }
//            }
//
//            println(guess)
//            if (readyMonkeyMap["root"]!! == 0L)
//                return guess
//            guess++
//        }
//    }

    fun part2(input: List<String>): String {
        val readyMonkeyMap = mutableMapOf<String, String>()
        val monkeyMap = mutableMapOf<String, Monkey>()
        val remainingMonkeyNames = mutableListOf<String>()
        input.forEach {
            val splitLine = it.split(": ")
            val monkeyName = splitLine[0]
            val rest = splitLine[1]
            if (rest.length == 11) {
                val (monkey1, op, monkey2) = rest.split(" ")
                monkeyMap[monkeyName] = Monkey(monkeyName, op, monkey1, monkey2)
                remainingMonkeyNames.add(monkeyName)
            } else {
                readyMonkeyMap[monkeyName] = rest
            }
        }

        while (remainingMonkeyNames.isNotEmpty() && !readyMonkeyMap.containsKey("root")) {
            val iterator = remainingMonkeyNames.listIterator()
            while (iterator.hasNext()) {
                val monkeyName = iterator.next()
                val monkey = monkeyMap[monkeyName]!!
                if (readyMonkeyMap.containsKey(monkey.monkey1) && readyMonkeyMap.containsKey(monkey.monkey2)) {
                    val monkey1Value = readyMonkeyMap[monkey.monkey1]!!
                    val monkey2Value = readyMonkeyMap[monkey.monkey2]!!
                    readyMonkeyMap[monkeyName] = "($monkey1Value ${monkey.op} $monkey2Value)"
                    iterator.remove()
                }
            }
        }

        return readyMonkeyMap["root"]!!
    }

}

private fun Long.operation(op: String, monkey2Value: Long): Long =
    when (op) {
        "*" -> this * monkey2Value
        "/" -> this / monkey2Value
        "+" -> this + monkey2Value
        "-" -> this - monkey2Value
        "=" -> if (this == monkey2Value) 0L else -1L
        else -> throw RuntimeException("Invalid operation!!!")
    }

fun main() {
    val input1 = readInput("input21_1")
    val input2 = readInput("input21_2")
    println(Day21().part1(input1))
    println(Day21().part2(input2))
}