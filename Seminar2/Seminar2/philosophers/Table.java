package philosophers;


// Task 3 First Part
/*public class Table {

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

// Task 3 Second Part
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


    public synchronized void getChopsticks(int n) {
        while (!chopstick[n] && chopstick[(n + 1) % 5]) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        chopstick[n] = false;
        chopstick[(n + 1) % 5] = false;
    }

    public synchronized void releaseChopsticks(int n) {
        chopstick[n] = true;
        chopstick[(n + 1) % 5] = true;
        notifyAll();
    }
}
