import org.junit.jupiter.api.Assertions.assertEquals

internal class SolverKtTest {

    @org.junit.jupiter.api.Test
    fun solve() {
        assertEquals(
            "[LEFT, ABOVE, RIGHT, BELOW, LEFT, BELOW, RIGHT, RIGHT, ABOVE, ABOVE, LEFT, ABOVE, " +
                    "RIGHT, BELOW, BELOW, LEFT, ABOVE, ABOVE, LEFT, BELOW, BELOW, RIGHT, RIGHT, RIGHT, BELOW]",
            solve(
                Grid(
                    arrayOf(
                        intArrayOf(2, 9, 6, 4),
                        intArrayOf(10, 1, 7, 8),
                        intArrayOf(3, 0, 15, 11),
                        intArrayOf(5, 13, 14, 12)
                    ), Pair(2, 1)
                )
            ).toString()
        )
    }
}