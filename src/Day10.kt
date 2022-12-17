import kotlin.math.abs

data class State(
    val cycle: Int,
    val X: Int,
    val signalStrengths: MutableMap<Int, Int>
)

class Day10 {
    fun part1(instructions: List<String>): Int {
        var state = State(1, 1, mutableMapOf())
        for (instruction in instructions) {
            val commandWithOptionalParam = instruction.split(' ')
            val param = if (commandWithOptionalParam.size > 1) commandWithOptionalParam[1] else null
            state = execute(commandWithOptionalParam[0], param, state)
        }
        return state.signalStrengths.entries
            .filter { (cycle, _) -> (cycle - 20) % 40 == 0 }
            .sumOf { (cycle, x) -> cycle * x }
    }

    fun execute(command: String, param: String?, state: State): State {
        var cycle = state.cycle
        var X = state.X
        val signalStrengths = state.signalStrengths
        when (command) {
            "noop" -> {
                signalStrengths[cycle] = X
                val newCycle = cycle + 1
                return State(newCycle, X, signalStrengths)
            }

            "addx" -> {
                signalStrengths[cycle] = X
                signalStrengths[cycle + 1] = X
                val newCycle = cycle + 2
                val newX = X + param!!.toInt()
                return State(newCycle, newX, signalStrengths)
            }
        }
        return State(cycle, X, signalStrengths)
    }

    fun part2(instructions: List<String>) {
        var state = State(1, 1, mutableMapOf())
        for (instruction in instructions) {
            val commandWithOptionalParam = instruction.split(' ')
            val param = if (commandWithOptionalParam.size > 1) commandWithOptionalParam[1] else null
            state = execute(commandWithOptionalParam[0], param, state)
        }
        state.signalStrengths.entries
            .forEach { (cycle, X) ->
                val position = (cycle - 1) % 40
                if (abs(position-X) <= 1) print("#") else print('.')
                if (cycle % 40 == 0) {
                    println()
                }
            }
    }
}

fun main() {
    val day10 = Day10()
    val input = readInput("input10_1")
    println(day10.part1(input))
    println(day10.part2(input))
}