class BluePrint(
    val id: Int,
    val oreRobotOreCost: Int,
    val clayRobotOreCost: Int,
    val obsidianRobotOreCost: Int,
    val obsidianRobotClayCost: Int,
    val geodeRobotOreCost: Int,
    val geodeRobotObsidianCost: Int,
) {
    val maxOreNeeded = listOf(
        geodeRobotOreCost,
        obsidianRobotOreCost,
        clayRobotOreCost
    ).max()
}

data class RobotsState(
    val bluePrintId: Int,
    val ore: Int,
    val clay: Int,
    val obsidian: Int,
    val geode: Int,
    val oreRobots: Int,
    val clayRobots: Int,
    val obsidianRobots: Int,
    val geodeRobots: Int,
    val timeLeft: Int
)

class Day19 {

    private val seenStates = mutableSetOf<RobotsState>()

    fun part1(input: List<String>): Int {
        val bluePrints = input.mapIndexed { i, line -> parseBluePrint(i + 1, line) }
        var qualityProducts = 0
        for (bluePrint in bluePrints) {
            val currentQuality = searchOptimalExecution(
                bluePrint,
                RobotsState(
                    bluePrintId = bluePrint.id,
                    ore = 0,
                    clay = 0,
                    obsidian = 0,
                    geode = 0,
                    oreRobots = 1,
                    clayRobots = 0,
                    obsidianRobots = 0,
                    geodeRobots = 0,
                    timeLeft = 24
                )
            )
            qualityProducts += currentQuality * bluePrint.id
            println(qualityProducts)
            println(bluePrint.id)
        }
        return qualityProducts
    }

    private fun searchOptimalExecution(
        bluePrint: BluePrint,
        state: RobotsState
    ): Int {
        seenStates.add(state)

        if (state.timeLeft == 0) {
//            val nextCurrentResources = currentConfiguration.resources.toMutableMap()
//            for ((robot, value) in currentConfiguration.robots.entries) {
//                nextCurrentResources[robot] = nextCurrentResources[robot]!! + value
//            }
            return state.geode
        }

        // build robot from previous resources - calculate all possibilities or don't build
        val possibleNextStates =
            getNextPossibleStates(bluePrint, state)

        // collect resources with robots of current config
        val results = possibleNextStates
            .map {
                var ore = it.ore + state.oreRobots
                var clay = it.clay + state.clayRobots
                var obsidian = it.obsidian + state.obsidianRobots
                val maxOreNeeded = bluePrint.maxOreNeeded * (it.timeLeft) - state.oreRobots * (it.timeLeft - 1)
                if (ore >= maxOreNeeded) {
                    ore = maxOreNeeded
                }
                val maxClayNeeded = bluePrint.obsidianRobotClayCost * (it.timeLeft) - state.clayRobots * (it.timeLeft - 1)
                if (clay >= maxClayNeeded) {
                    clay = maxClayNeeded
                }
                val maxObsidianNeeded = bluePrint.geodeRobotObsidianCost * (it.timeLeft) - state.obsidianRobots * (it.timeLeft - 1)
                if (obsidian >= maxObsidianNeeded) {
                    obsidian = maxObsidianNeeded
                }
                it.copy(
                    ore = ore,
                    clay = clay,
                    obsidian = obsidian,
                    geode = it.geode + state.geodeRobots,
                    timeLeft = it.timeLeft - 1
                )
            }
            .filter { !seenStates.contains(it) }
            .map {
                searchOptimalExecution(
                    bluePrint,
                    it
                )
            }
        if (results.isNotEmpty()) return results.max()
        return 0
    }

    private fun getNextPossibleStates(
        bluePrint: BluePrint,
        state: RobotsState
    ): List<RobotsState> {
        val possibleNextStates = mutableListOf(state) // add "do nothing" state
        if (bluePrint.maxOreNeeded > state.oreRobots && bluePrint.oreRobotOreCost <= state.ore) {
            possibleNextStates.add(
                state.copy(
                    ore = state.ore - bluePrint.oreRobotOreCost,
                    oreRobots = state.oreRobots + 1
                )
            )
        }
        if (bluePrint.obsidianRobotClayCost > state.clayRobots && bluePrint.clayRobotOreCost <= state.ore) {
            possibleNextStates.add(
                state.copy(
                    ore = state.ore - bluePrint.clayRobotOreCost,
                    clayRobots = state.clayRobots + 1
                )
            )
        }
        // obsidian
        if (bluePrint.geodeRobotObsidianCost > state.oreRobots && bluePrint.obsidianRobotOreCost <= state.ore && bluePrint.obsidianRobotClayCost <= state.clay) {
            possibleNextStates.add(
                state.copy(
                    ore = state.ore - bluePrint.obsidianRobotOreCost,
                    clay = state.clay - bluePrint.obsidianRobotClayCost,
                    obsidianRobots = state.obsidianRobots + 1
                )
            )
        }
        // geode
        if (bluePrint.geodeRobotOreCost <= state.ore && bluePrint.geodeRobotObsidianCost <= state.obsidian) {
            possibleNextStates.add(
                state.copy(
                    ore = state.ore - bluePrint.geodeRobotOreCost,
                    obsidian = state.obsidian - bluePrint.geodeRobotObsidianCost,
                    geodeRobots = state.geodeRobots + 1
                )
            )
        }
        return possibleNextStates
    }

    private fun parseBluePrint(id: Int, line: String): BluePrint {
        val destructuredRegex =
            "Blueprint .+: Each ore robot costs ([0-9]+) ore\\. Each clay robot costs ([0-9]+) ore\\. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay\\. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian\\.".toRegex()

        return destructuredRegex.matchEntire(line)
            ?.destructured
            ?.let { (oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost) ->
                return BluePrint(
                    id,
                    oreRobotOreCost.toInt(),
                    clayRobotOreCost.toInt(),
                    obsidianRobotOreCost.toInt(),
                    obsidianRobotClayCost.toInt(),
                    geodeRobotOreCost.toInt(),
                    geodeRobotObsidianCost.toInt()
                )
            }
            ?: throw IllegalArgumentException("Bad input '$line'")
    }

    fun part2(input: List<String>): Int {
        val bluePrints = input.mapIndexed { i, line -> parseBluePrint(i + 1, line) }
        var geodeCountProduct = 1
        for (bluePrint in bluePrints.subList(0, 3)) {
            val currentGeodeCount = searchOptimalExecution(
                bluePrint,
                RobotsState(
                    bluePrintId = bluePrint.id,
                    ore = 0,
                    clay = 0,
                    obsidian = 0,
                    geode = 0,
                    oreRobots = 1,
                    clayRobots = 0,
                    obsidianRobots = 0,
                    geodeRobots = 0,
                    timeLeft = 32
                )
            )
            geodeCountProduct *= currentGeodeCount
            println(geodeCountProduct)
            println(bluePrint.id)
        }
        return geodeCountProduct
    }
}

fun main() {
    val input = readInput("input19_1")
    println(Day19().part1(input))
    //println(Day19().part2(input))
}