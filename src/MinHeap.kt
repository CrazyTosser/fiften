// Java implementation of Min heap
class MinHeap<T : Comparable<T>>(private val heap: Array<T>) {
    private val front = 1
    private var size: Int = 0
    fun isEmpty() = size == 0

    // Function to return the position of
    // the parent for the node currently
    // at pos
    private fun parent(pos: Int): Int {
        return pos / 2
    }

    // Function to return the position of the
    // left child for the node currently at pos
    private fun leftChild(pos: Int): Int {
        return 2 * pos
    }

    // Function to return the position of
    // the right child for the node currently
    // at pos
    private fun rightChild(pos: Int): Int {
        return 2 * pos + 1
    }

    // Function that returns true if the passed
    // node is a leaf node
    private fun isLeaf(pos: Int): Boolean {
        return pos >= (size / 2) && pos <= size
    }

    // Function to swap two nodes of the heap
    private fun swap(fpos: Int, spos: Int) {
        val tmp = heap[fpos]
        heap[fpos] = heap[spos]
        heap[spos] = tmp
    }

    // Function to heapify the node at pos
    private fun minHeapify(pos: Int) {

        // If the node is a non-leaf node and greater
        // than any of its child
        if (!isLeaf(pos)) {
            if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {

                // Swap with the left child and heapify
                // the left child
                if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
                    swap(pos, leftChild(pos))
                    minHeapify(leftChild(pos))
                } else {
                    swap(pos, rightChild(pos))
                    minHeapify(rightChild(pos))
                }// Swap with the right child and heapify
                // the right child
            }
        }
    }

    // Function to insert a node into the heap
    fun insert(element: T) {
        heap[++size] = element
        var current = size

        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current))
            current = parent(current)
        }
    }

    // Function to print the contents of the heap
    fun print() {
        for (i in 1..size / 2) {
            print(
                " PARENT : " + heap[i]
                        + " LEFT CHILD : " + heap[2 * i]
                        + " RIGHT CHILD :" + heap[2 * i + 1]
            )
            println()
        }
    }

    // Function to build the min heap using
    // the minHeapify
    fun minHeap() {
        for (pos in (size / 2) downTo 1) {
            minHeapify(pos)
        }
    }

    // Function to remove and return the minimum
    // element from the heap
    fun remove(): T {
        val popped = heap[front]
        heap[front] = heap[size--]
        minHeapify(front)
        return popped
    }
} 
