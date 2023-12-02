import java.awt.Color

class Day02 {

    sealed class GameCube(val id: Int, val value: Int) {
        class Red(id: Int,value: Int) : GameCube(id, value) {
        }
        class Green(id: Int, value: Int): GameCube(id, value)

        class Blue(id: Int, value: Int) : GameCube(id,value)

        var color: String = when (this) {
            is Red -> "red"
            is Green -> "green"
            is Blue -> "blue"
        }
        fun isPossibleGame(): Boolean {
            return when (this) {
                is Red -> value in 0..12
                is Green -> value in 0..13
                is Blue -> value in 0..14
            }
        }
    }
    fun solvePart1(input: List<String>): Int {
        var sum = 0
        input.forEach { row ->
            val (id, games) = row.gamesById()
            if (games.allGameCubes().all { game -> game.isPossibleGame() }) { sum += id }
        }
        return sum
    }
    fun solvePart2(input: List<String>): Int {
        var sum = 0
        input.forEach { raw ->
            val (_, game) = raw.gamesById()
            game.allGameCubes().groupBy { it.color }.apply {
               sum += keys.map { key -> getValue(key).maxBy { it.value }.value }.reduce(Int::times)
            }

        }
        return sum
    }

    private fun String.allGameCubes() =
        substring(indexOfFirst { it == ':' } + 2)
        .split(";").map {
                it.split(',')
            }.mapIndexed() { index,cubes ->
                cubes.map { cube ->
                    with(cube.split(" ")) {
                        when (get(2).trim()) {
                            "red" -> GameCube.Red(index,get(1).toInt())
                            "green" -> GameCube.Green(index,get(1).toInt())
                            "blue" -> GameCube.Blue(index,get(1).toInt())
                            else -> throw Exception("Unknown cube color")
                        }
                    }
                }
            }.flatten()

    private fun String.gamesById() = substring(indexOf(" ") + 1, indexOf(":")).toInt() to
            " " + substring(indexOfFirst { it == ':' } + 1)

}

fun main() {
    fun part1(input: List<String>): Int {
        val day02 = Day02()
        return day02.solvePart1(input)
    }

    fun part2(input: List<String>): Int {
        val day02 = Day02()
        return day02.solvePart2(input)
    }

    // test if implementation meets criteria from the description, like:

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}