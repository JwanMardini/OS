public class StopWatch implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        Thread thread = new Thread(stopWatch);
        thread.start();
        thread.join();

    }

    @Override
    public void run() {
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

            if(elapsedTime > 9.99){
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
