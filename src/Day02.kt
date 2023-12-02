class Day02 {
    fun solvePart1(input: List<String>): Int {
        var sum = 0
        input.forEach { row ->
            val (id, games) = row.gamesById()
            if (games.allGameCubes().all { game -> game.isPossibleGame() }) { sum += id }
        }
        return sum
    }

    private fun String.allGameCubes() =
        substring(indexOfFirst { it == ':' } + 2)
        .split(";").map {
                it.split(',')
            }.flatten()

    private fun String.gamesById() = substring(indexOf(" ") + 1, indexOf(":")).toInt() to
            " " + substring(indexOfFirst { it == ':' } + 1)

    private val cubes = mapOf("red" to 0..12, "green" to 0..13, "blue" to 0..14)
    private fun String.isPossibleGame():Boolean {
        with(split(" ")) {
            val cube = get(2) to get(1).toInt()
            return cubes[cube.first]?.contains(cube.second) ?: false
        }
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        val day02 = Day02()
        return day02.solvePart1(input)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:

    val input = readInput("Day02")
    part1(input).println()
}