public class zadaca3 {

    public class BlockingQueue<T> {

        T[] contents;
        int capacity;

        public BlockingQueue(int capacity) {
            contents = (T[]) new Object[capacity];
            this.capacity = capacity;
        }

        public void enqueue(T item) {
        }

        public T dequeue() {
        }
    }


}
