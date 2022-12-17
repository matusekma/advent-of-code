class Day07 {
    fun part1(input: List<String>): Int {
        val directoryFileSizes = mutableMapOf<String, Int>()
        var currentDirectoryTree = mutableListOf<String>()
        var currentDirectoryPath = ""
        for (line in input) {
            if (line == "$ cd ..") {
                currentDirectoryTree.removeLast()
                currentDirectoryPath = currentDirectoryTree.joinToString("-")
            } else if (line.contains(" cd ")) {
                currentDirectoryTree.add(line.split(' ')[2])
                currentDirectoryPath = currentDirectoryTree.joinToString("-")
            } else if (line.contains("dir")) {
                val dirPath = currentDirectoryPath + "-" + line.split(' ')[1]
                directoryFileSizes[dirPath] = directoryFileSizes.getOrDefault(dirPath, 0)
            } else if (line.matches(Regex("[0-9]+ .*"))) {
                directoryFileSizes[currentDirectoryPath] =
                    directoryFileSizes.getOrDefault(currentDirectoryPath, 0) + line.split(' ')[0].toInt()
            }
        }
        val directorySizes = mutableMapOf<String, Int>()
        directoryFileSizes.entries.forEach { (dirPath, dirSize) ->
            directorySizes[dirPath] = dirSize
            directoryFileSizes.entries.forEach { (otherDirPath, otherDirSize) ->
                if (otherDirPath.contains("$dirPath-")) {
                    directorySizes[dirPath] = directorySizes[dirPath]!! + otherDirSize
                }
            }
        }
        return directorySizes.filterValues { size -> size <= 100000 }.values.sum()
    }

    fun part2(input: List<String>): Int {
        val directoryFileSizes = mutableMapOf<String, Int>()
        var currentDirectoryTree = mutableListOf<String>()
        var currentDirectoryPath = ""
        for (line in input) {
            if (line == "$ cd ..") {
                currentDirectoryTree.removeLast()
                currentDirectoryPath = currentDirectoryTree.joinToString("-")
            } else if (line.contains(" cd ")) {
                currentDirectoryTree.add(line.split(' ')[2])
                currentDirectoryPath = currentDirectoryTree.joinToString("-")
            } else if (line.contains("dir")) {
                val dirPath = currentDirectoryPath + "-" + line.split(' ')[1]
                directoryFileSizes[dirPath] = directoryFileSizes.getOrDefault(dirPath, 0)
            } else if (line.matches(Regex("[0-9]+ .*"))) {
                directoryFileSizes[currentDirectoryPath] =
                    directoryFileSizes.getOrDefault(currentDirectoryPath, 0) + line.split(' ')[0].toInt()
            }
        }
        val directorySizes = mutableMapOf<String, Int>()
        directoryFileSizes.entries.forEach { (dirPath, dirSize) ->
            directorySizes[dirPath] = dirSize
            directoryFileSizes.entries.forEach { (otherDirPath, otherDirSize) ->
                if (otherDirPath.contains("$dirPath-")) {
                    directorySizes[dirPath] = directorySizes[dirPath]!! + otherDirSize
                }
            }
        }
        val neededSpace = 30000000 - (70000000 - directorySizes["/"]!!)
        return directorySizes.filterValues { size -> size >= neededSpace }.values.min()
    }
}

fun main() {
    val day07 = Day07()
    val input = readInput("input07_1")
    println(day07.part1(input))
    println(day07.part2(input))
}