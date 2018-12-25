import java.util.*

enum class Direction(val value: String) {
    ABOVE("↑"), RIGHT("→"), LEFT("←"), BELOW("↓")
}

class SolverState(grid: Grid, steps: List<Direction>) : Comparable<SolverState> {
    val grid: Grid = grid.copy()
    val steps: List<Direction> = steps.toList()
    override fun compareTo(other: SolverState): Int = value.compareTo(other.value)

    val value: Int = grid.lower() + steps.size
    val solved = grid.isSolve()

    override fun toString(): String {
        val str = StringBuilder()
        grid.grid.forEach { str.append(it.joinToString()).append("\n") }
        return "${value}>${steps}\n${str}"
    }

}

fun solve(start: Grid): List<Direction> {
    val startState = SolverState(start, listOf())
    val fr = PriorityQueue<SolverState>(compareBy { it.value })
    fr.add(startState)
    while (!fr.isEmpty()) {
        val curState = fr.poll()
        if (curState.solved)
            return curState.steps
        val grid = curState.grid.copy()
        val steps = curState.steps
        val candidate = grid.validMoves().shuffled()
        val lastStep = if (steps.isNotEmpty()) steps.last() else null
        if (lastStep != null)
            candidate.filter { !directOpposit(it, lastStep) }
        for (source in candidate) {
            fr.add(SolverState(grid.copy().applyMove(source), steps + source))
        }
    }
    return listOf()
}