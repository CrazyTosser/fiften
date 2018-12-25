import java.io.File
import java.io.FileNotFoundException
import javax.management.relation.InvalidRelationTypeException

fun main(arg: Array<String>) {
    val arr: Array<IntArray> = Array(4) { IntArray(4) }
    var n: Pair<Int, Int> = Pair(0, 0)
    var work = Grid(initGrid, Pair(3, 3))
    if (arg.isEmpty()) {
        work.applyMove(shffle(work, 10, mutableListOf()))
        work.grid.forEachIndexed { i, ints ->
            ints.forEachIndexed { j, ii ->
                if (ii == 0)
                    n = Pair(i, j)
            }
        }
        work.setEmpty(n)
    } else {
        try {
            n = when (arg[0]) {
                "-s" -> {
                    parseLine(arr, arg.sliceArray(1 until arg.size))
                }
                else -> {
                    parseLine(arr, File(arg[0]).readLines()[0].split(" ").toTypedArray())
                }
            }
            work = Grid(arr, n)
        } catch (ex: IllegalArgumentException) {
            if (arg[0] == "-s") {
                println("Incorrect sequence")
            } else {
                println("Incorrect sequence")
            }
            return
        } catch (ex: FileNotFoundException) {
            println("File not available")
        } catch (ex: InvalidRelationTypeException) {
            println("Corrupted sequence. Duples or chars may be...")
            return
        }
    }
    if (isSolvable(arr)) {
        print(work)
        println(n.toString())
        val res = solve(work)
        res.forEach {
            print("${it.value} ")
        }
    } else
        println("This sequence isn`t solvable")
}

private fun isSolvable(arr: Array<IntArray>): Boolean {
    var inv = 0
    var tmp = intArrayOf()
    arr.forEach { tmp += it }
    tmp.forEachIndexed { index, i ->
        if (i != 0)
            tmp.slice(0 until index).forEach { j ->
                if (j > i)
                    inv++
            }
    }
    arr.forEachIndexed { index, ints ->
        if (ints.contains(0))
            inv += 1 + index
    }
    return inv and 1 != 1
}

private fun shffle(grid: Grid, moves: Int, lDir: MutableList<Direction>): List<Direction> {
    if (lDir.size == moves)
        return lDir
    var valid = grid.validMoves()
    if (lDir.size > 0) {
        val last = lDir.last()
        valid = valid.filter { !directOpposit(it, last) }
    }
    val source = valid.shuffled()[0]
    val next = grid.copy().applyMove(source)
    lDir.add(source)
    return shffle(next, moves, lDir)
}

private fun parseLine(arr: Array<IntArray>, arg: Array<String>): Pair<Int, Int> {
    if (arg.size != 16)
        throw IllegalArgumentException()
    val bits = BooleanArray(16)
    var i = 0
    var j = 0
    var c = 0
    var res = Pair(0, 0)
    while (c < 16) {
        if (!arg[c].all { it.isDigit() })
            throw InvalidRelationTypeException()
        arr[i][j] = arg[c++].toInt()

        if (!bits[arr[i][j]])
            bits[arr[i][j]] = true
        else
            throw InvalidRelationTypeException()
        if (arr[i][j] == 0)
            res = Pair(i, j)
        j++
        if (j == 4) {
            i++; j = 0
        }
    }
    return res
}
