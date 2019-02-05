package Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logger {
    private static Logger logger = null;
    private FileWriter fileWriter;
    private PrintWriter printWriter;
    private static final String CONFIG_PATH = "logs/";

    private Logger(String path){
        path = path.replace(" ", "_");
        path += ".txt";
        try {
            fileWriter = new FileWriter( path );
            printWriter = new PrintWriter(fileWriter);
            printWriter.print("Log file created:" +  path);
        }catch(IOException e){

        }
    }

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger(CONFIG_PATH + new Date());
        }
        return logger;
    }

    public void log(String entry){
        Date date = new Date();
        String str = new String(date + ": " + entry + "\r\n");
        printWriter.print(str);
        System.out.println(str);
    }
}