import kotlin.random.Random

class Shuffle<T> {
    val rnd = Random(37 + 37 * (System.currentTimeMillis() % 37))
    fun mix(array: Array<T>) {
        for (i in 0 until array.size) {
            val pos = rnd.nextInt(0, i)
            val tmp = array[pos]
            array[pos] = array[i]
            array[i] = tmp
        }
    }
}