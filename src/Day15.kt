import java.util.SortedSet
import kotlin.math.abs

class PositionLong(val x: Long, val y: Long) {
    val posKey: String
        get() = "${this.x},${this.y}"
}

fun PositionLong.getManhattanDistance(otherPos: PositionLong) =
    abs(this.x - otherPos.x) + abs(this.y - otherPos.y)

class Day15 {
    val occupiedPositionLongsInRow = mutableSetOf<String>()

    val maxCoord = 4_000_000

    fun part1(input: List<String>, row: Long): Long {
        for (line in input) {
            val (sensor, beacon) = parseLine(line)
            val distance = sensor.getManhattanDistance(beacon)
            val yDistanceFromRow = abs(sensor.y - row)
            if (yDistanceFromRow <= distance) {
                for (x in 0..distance - yDistanceFromRow) {
                    occupiedPositionLongsInRow.add(PositionLong(sensor.x + x, row).posKey)
                    occupiedPositionLongsInRow.add(PositionLong(sensor.x - x, row).posKey)
                }
            }
            occupiedPositionLongsInRow.remove(sensor.posKey)  // remove sensor
            occupiedPositionLongsInRow.remove(beacon.posKey)  // remove beacon
        }
        return occupiedPositionLongsInRow.size.toLong()
    }


    fun part2(input: List<String>): Long {
        val sensorsWithDistance = mutableMapOf<PositionLong, Long>()
        for (line in input) {
            val (sensor, beacon) = parseLine(line)
            val distance = sensor.getManhattanDistance(beacon)
            sensorsWithDistance[sensor] = distance
        }
        for ((sensor, distance) in sensorsWithDistance.entries) {
            val searchedDistance = distance + 1 // it must be in distance + 1
            for (x in 0..searchedDistance) {
                val y = searchedDistance - x
                val positions = listOf(
                    PositionLong(sensor.x + x, sensor.y + y),
                    PositionLong(sensor.x - x, sensor.y + y),
                    PositionLong(sensor.x + x, sensor.y - y),
                    PositionLong(sensor.x - x, sensor.y - y)
                )
                for (position in positions) {
                    if(position.x !in 0..maxCoord || position.y !in 0..maxCoord) continue
                    var foundPositionLong = true
                    for ((otherSensor, otherDistance) in sensorsWithDistance.entries) {
                        if (otherSensor.getManhattanDistance(position) <= otherDistance) {
                            foundPositionLong = false;
                        }
                    }
                    if (foundPositionLong) {
                        println(position.x)
                        println(position.y)
                        return position.x * 4_000_000 + position.y
                    }
                }
            }
        }
        return -1
    }

    fun parseLine(line: String): List<PositionLong> {
        val destructuredRegex =
            "Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)".toRegex()

        return destructuredRegex.matchEntire(line)
            ?.destructured
            ?.let { (sensorX, sensorY, beaconX, beaconY) ->
                listOf(
                    PositionLong(sensorX.toLong(), sensorY.toLong()),
                    PositionLong(beaconX.toLong(), beaconY.toLong())
                )
            }
            ?: throw IllegalArgumentException("Bad input '$line'")
    }
}

fun main() {
    val input = readInput("input15_1")
    // println(Day15().part1(input, 2000000))
    println(Day15().part2(input))
}