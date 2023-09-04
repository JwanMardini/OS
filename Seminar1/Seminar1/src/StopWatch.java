public class StopWatch implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        Thread thread = new Thread(stopWatch);
        System.out.println("Main thread. Waiting for stopwatch thread...");
        thread.start();
        thread.join();
        System.out.println("Main thread. Finished stopwatch thread");

    }

    @Override
    public void run() {
        System.out.println("Stopwatch thread. Stopwatch starts now!");
        double elapsedTime = 0; //start time
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            elapsedTime = elapsedTime + 0.01; // add a millisecond for every iteration
            String formattedString = String.format("%.2f", elapsedTime); // shows the result with only two digits
            System.out.println("Stopwatch thread. Elapsed: " + formattedString);

            if(elapsedTime >= 2.99){
                break;
            }
        }
    }
}

/* How did you implement the task?
        Shown in code above

    Why did you solve it in the way you did it?
        It may not be the right solution but that what I understood of the task description

    What difference in behaviour did you notice?
        checking if the elapsed time has reach a certain time was a challenge
 */
