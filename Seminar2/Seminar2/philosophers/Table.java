package philosophers;

import java.util.concurrent.Semaphore;

public class Table {

	private int nbrOfChopsticks;
	private Semaphore[] chopstick;

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new Semaphore[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = new Semaphore(1); // Initialize each chopstick semaphore with a limit of 1
		}
	}

	public void getChopsticks(int n) throws InterruptedException {
		int leftChopstick = n;
		int rightChopstick = (n + 1) % nbrOfChopsticks;

		// Ensure that philosophers always try to acquire chopsticks in a consistent order
		if (leftChopstick > rightChopstick) {
			int temp = leftChopstick;
			leftChopstick = rightChopstick;
			rightChopstick = temp;
		}

		chopstick[leftChopstick].acquire(); // Acquire the left chopstick
		chopstick[rightChopstick].acquire(); // Acquire the right chopstick
	}

	public void releaseChopsticks(int n) {
		int leftChopstick = n;
		int rightChopstick = (n + 1) % nbrOfChopsticks;

		chopstick[leftChopstick].release(); // Release the left chopstick
		chopstick[rightChopstick].release(); // Release the right chopstick
	}
}



/*package philosophers;

public class Table {

	private int nbrOfChopsticks;
	private boolean chopstick[]; // true if chopstick[i] is available

	public Table(int nbrOfSticks) {
		nbrOfChopsticks = nbrOfSticks;
		chopstick = new boolean[nbrOfChopsticks];
		for (int i = 0; i < nbrOfChopsticks; i++) {
			chopstick[i] = true;
		}
	}


	public synchronized void getLeftChopstick(int n) {
		while (!chopstick[n]){
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		chopstick[n] = false;
	}

	public synchronized void getRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;

		while (!chopstick[pos]){
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		chopstick[pos] = false;
	}

	public synchronized void releaseLeftChopstick(int n) {
		chopstick[n] = true;
		notifyAll();
	}

	public synchronized void releaseRightChopstick(int n) {
		int pos = n + 1;
		if (pos == nbrOfChopsticks)
			pos = 0;
		chopstick[pos] = true;
		notifyAll();
	}
}*/
