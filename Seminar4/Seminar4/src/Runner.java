import java.io.*;
import java.nio.Buffer;
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
        if(command.toLowerCase().startsWith("filedump ")) {
            String filename = command.substring("filedump ".length());
            System.out.println("Filedump of file: " + filename);
            readFile(filename);

        } else if (command.toLowerCase().startsWith("copyfile")) {
            String[] input = command.split(" ");
            String srcFile = input[1];
            String desFile = input[2];
            System.out.println("Copy file: " + srcFile + " to " + desFile);
            copyFile(srcFile, desFile);

        } else {
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
                if (!command.equals("showerrlog")) {
                    System.out.println(ioe);
                    errorLog.add(command);
                }

            } finally {
                if (bufferReader != null) {
                    try {
                        bufferReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


    }

    public static List<String> getErrorLog() {
        return errorLog;
    }

    private void readFile(String filename) {
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "/" + filename);

        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }

    }

    private void copyFile(String srcFile, String DesFile){
        String currentDir = System.getProperty("user.dir");
        File src = new File(currentDir + "/" + srcFile);
        String des = currentDir + "/" + DesFile;
        try(BufferedReader br = new BufferedReader(new FileReader(src))){
            String line;
            BufferedWriter bw = new BufferedWriter(new FileWriter(des));
            while ((line = br.readLine()) != null){
                bw.write(line + "\n");
            }
            bw.close();

        }catch (IOException e){
            System.out.println(e);

        }

    }




}
