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
        long startTime = System.currentTimeMillis();
        while (true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long currentTime = System.currentTimeMillis();
            double elapsedTime = (currentTime - startTime) / 1000.0;
            String formattedString = String.format("%.2f", elapsedTime); // shows the result with only two digits
            System.out.println("Stopwatch thread. Elapsed: " + formattedString);

            if(elapsedTime > 60){
                break;
            }
        }
    }
}

/*  1. What does the Thread.join() do? What happens if we remove it from the main method?
        Thread.join makes the threads wait for each other to be excuted.
    2. Compare the real elapsed time using a stopwatch on your phone with your Java implementation. Does the elapsed time differ, why?
        It depends on the implamention of the stopWatch. If you use the System.currentTimeMillis it will not differ,
        but if you try to do manually it will differ a lot
 */



    // Another way
    /*@Override
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
            System.out.println(formattedString);

            if(elapsedTime >= 59.99){
                break;
            }

        }
    }*/

    // Second example


/* How did you implement the task?
        Shown in code above

    Why did you solve it in the way you did it?
        It may not be the right solution but that what I understood of the task description

    What difference in behaviour did you notice?
        checking if the elapsed time has reach a certain time was a challenge
 */
