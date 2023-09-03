import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner implements Runnable{
    private String command;
    private static List<String> errorLog = new ArrayList<>();

    public Runner(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        List<String> input = Arrays.asList(command.split(" "));

        ProcessBuilder processBuilder = new ProcessBuilder(input);
        BufferedReader bufferReader = null;
        try {
            Process proc = processBuilder.start();
            InputStream inputStream = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(isr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferReader.close();
        } catch (java.io.IOException ioe) {
            if(!command.equals("showerrlog")){
                System.out.println(ioe);
                errorLog.add(command);
            }

        }finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public static List<String> getErrorLog() {
        return errorLog;
    }
}
