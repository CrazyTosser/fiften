import kotlin.math.abs

class Grid {
    val grid: Array<IntArray>
    private var emptyPos: Pair<Int, Int>
    private var _low: Int? = null

    fun setEmpty(e: Pair<Int, Int>) {
        emptyPos = e
    }

    constructor(grid: Array<IntArray> = initGrid, emptyPos: Pair<Int, Int> = Pair(3, 3)) {
        this.grid = grid.clone()
        this.emptyPos = emptyPos.copy()
    }


    fun validMoves(): List<Direction> {
        val result = mutableListOf<Direction>()
        if (emptyPos.second != 0) result.add(Direction.LEFT)
        if (emptyPos.second != 3) result.add(Direction.RIGHT)
        if (emptyPos.first != 0) result.add(Direction.ABOVE)
        if (emptyPos.first != 3) result.add(Direction.BELOW)
        return result
    }

    fun positionMove(num: Pair<Int, Int>): Direction? {
        if (num.first == emptyPos.first)
            if (num.second == emptyPos.second - 1)
                return Direction.LEFT
        if (num.second == emptyPos.second + 1)
            return Direction.RIGHT
        if (num.second == emptyPos.second) {
            if (num.first == emptyPos.first - 1)
                return Direction.ABOVE
            if (num.first == emptyPos.first + 1)
                return Direction.BELOW
        }
        return null
    }

    fun applyMove(source: Direction): Grid {
        val target = this.emptyPos
        val delta = directionToDelta(source)
        val emptyPos = Pair(
            target.first + delta.first,
            target.second + delta.second
        )
        val grid = this.grid.clone()
        grid[target.first][target.second] = grid[emptyPos.first][emptyPos.second]
        grid[emptyPos.first][emptyPos.second] = 0

        val next = Grid(grid, emptyPos)
        val num = grid[target.first][target.second]
        next._low = lower() -
                rectilinearDistance(num, emptyPos.first, emptyPos.second) +
                rectilinearDistance(num, target.first, target.second)
        return next
    }

    fun applyMove(source: List<Direction>): Grid {
        var next = this
        for (dir in source)
            next = next.applyMove(dir)
        return next
    }

    fun lower(): Int {
        var move = 0
        grid.forEachIndexed { row, ints ->
            ints.forEachIndexed { col, i ->
                if (i != 0)
                    move += rectilinearDistance(i, row, col)
            }
        }
        _low = move
        return _low as Int
    }

    fun isSolve() = lower() == 0
    fun copy(): Grid = Grid(this)

    constructor(grid: Grid) {
        val tmp = grid.grid.map { it.clone() }.toTypedArray()
        this.grid = tmp
        this.emptyPos = grid.emptyPos.copy()
        this._low = grid._low
    }

    override fun toString(): String {
        val res = StringBuilder()
        grid.forEach { v ->
            v.forEach {
                res.append(it).append(" ")
            }
            res.append("\n")
        }
        return res.toString()
    }
}

fun originalPos(num: Int): Pair<Int, Int> = Pair((num - 1) / 4, (num - 1) % 4)

fun rectilinearDistance(num: Int, curRow: Int, curCol: Int) =
    with(originalPos(num)) {
        abs(first - curRow) + abs(second - curCol)
    }


fun directionToDelta(direction: Direction) = when (direction) {
    Direction.ABOVE -> Pair(-1, 0)
    Direction.RIGHT -> Pair(0, 1)
    Direction.BELOW -> Pair(1, 0)
    Direction.LEFT -> Pair(0, -1)
}

fun directOpposit(a: Direction, b: Direction) =
    directionToDelta(a).first + directionToDelta(b).first == 0 &&
            directionToDelta(a).second + directionToDelta(b).second == 0

val initGrid =
    arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 14, 15, 0)
    )