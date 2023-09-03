import java.util.*;

public class TestProcessBuilder {
    static void createProcess(String command) throws java.io.IOException {
        Runner runner = new Runner(command);
        Thread thread = new Thread(runner);
        thread.start();
    }

    public static void main(String[] args) throws java.io.IOException {
        String commandLine;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");
    
        while (true) {
            System.out.print("jsh>");
            commandLine = scanner.nextLine();
            // if user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            if (commandLine.toLowerCase().equals("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            if(commandLine.toLowerCase().equals("showerrlog")){
                List<String> errLog = Runner.getErrorLog();
                if(errLog.isEmpty()){
                    System.out.println("No error occurred during you session");
                }else{
                    System.out.println("Commands that gave an error:");
                    for (String e : errLog){
                        System.out.println(e);
                    }
                }

            }
            createProcess(commandLine);
        }   
    }


}
