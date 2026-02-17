package datastructures;
import java.util.NoSuchElementException;

/**
 * A generic Circular Queue implementation using a resizing array.
 * This implementation is optimized for O(1) operations and memory efficiency.
 */
@SuppressWarnings("unchecked")
public class CircularQueue<T> {

    private T[] elements;
    private int head;
    private int tail;
    private int size;

    // Initial capacity of 10 is standard for small buffers
    private static final int DEFAULT_CAPACITY = 10;

    public CircularQueue() {
        // We cannot create generic arrays directly in Java (new T[]),
        // so we create an Object array and cast it.
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void enqueue(T item) {
        // If the queue is full, we must grow the array
        if (size == elements.length) {
            resize();
        }

        elements[tail] = item;

        // Circular Logic: If tail is at the end, wrap it back to 0
        tail = (tail + 1) % elements.length;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        T item = elements[head];

        // Critical: Nullify the reference to help Garbage Collector
        elements[head] = null;

        // Circular Logic: Move head forward, wrapping to 0 if needed
        head = (head + 1) % elements.length;
        size--;

        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elements[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Resizes the internal array when full.
     * This method "unwraps" the circular buffer into a straight line.
     */
    private void resize() {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity * 2;

        T[] newElements = (T[]) new Object[newCapacity];

        // --- THE UNWRAPPING LOGIC ---

        // Scenario:
        // Old Array (Size 5): [ D, E, A, B, C ]
        // Head is at index 2 (Value 'A')
        // Tail is at index 2 (Where next value goes)
        // We need the New Array to look like: [ A, B, C, D, E, null, ... ]

        // Step 1: Copy from Head to the End of the array (A, B, C)
        // logic: length(5) - head(2) = 3 elements
        int elementsRightOfHead = oldCapacity - head;

        System.arraycopy(elements, head, newElements, 0, elementsRightOfHead);

        // Step 2: Copy from Start of array to Tail (D, E)
        // logic: tail is at 2, so we copy indices 0 and 1
        System.arraycopy(elements, 0, newElements, elementsRightOfHead, tail);

        // Update pointers
        elements = newElements;
        head = 0;       // Head is now at the very start
        tail = size;    // Tail is exactly at the end of the data
    }
}