class MonitorExample {
    private int sharedData = 0;
    private boolean dataReady = false;

    public synchronized void produce(int data) {
        while (dataReady) {
            // Wait if data is already available
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Produce data and notify waiting consumers
        sharedData = data;
        dataReady = true;
        notify();
    }

    public synchronized int consume() {
        while (!dataReady) {
            // Wait if data is not available
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Consume data and notify producers
        int data = sharedData;
        dataReady = false;
        notify();

        return data;
    }
}

public class MonitorExampleApp {
    public static void main(String[] args) {
        MonitorExample monitor = new MonitorExample();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                monitor.produce(i);
                System.out.println("Produced: " + i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                int data = monitor.consume();
                System.out.println("Consumed: " + data);
            }
        });

        producer.start();
        consumer.start();
    }
}
