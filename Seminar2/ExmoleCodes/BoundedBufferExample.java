import java.util.LinkedList;

class BoundedBuffer {
    private LinkedList<Integer> buffer;
    private int maxSize;

    public BoundedBuffer(int size) {
        buffer = new LinkedList<>();
        maxSize = size;
    }

    // Producer attempts to add an item to the buffer
    public void produce(int item) {
        // Check if the buffer is full
        if (buffer.size() == maxSize) {
            System.out.println("Buffer is full. Producer is waiting.");
            return; // Do not add more items when the buffer is full
        }

        // Add the item to the buffer
        buffer.add(item);
        System.out.println("Produced: " + item);
    }

    // Consumer attempts to remove an item from the buffer
    public int consume() {
        // Check if the buffer is empty
        if (buffer.isEmpty()) {
            System.out.println("Buffer is empty. Consumer is waiting.");
            return -1; // Return a dummy value when the buffer is empty
        }

        // Remove and return an item from the buffer
        int item = buffer.removeFirst();
        System.out.println("Consumed: " + item);
        return item;
    }
}

class Producer extends Thread {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            buffer.produce(i);
            try {
                sleep(100); // Simulate some production time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            buffer.consume();
            try {
                sleep(150); // Simulate some consumption time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BoundedBufferExample {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5); // Buffer size is 5

        Producer producer1 = new Producer(buffer);
        Producer producer2 = new Producer(buffer);
        Consumer consumer1 = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);

        // Start producer and consumer threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}
