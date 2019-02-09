import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerTest {

    private String fileName;

    @Before
    public void setUp(){
        SimpleDateFormat ft = new SimpleDateFormat("yy_MM_dd'_'hh_mm");
        Date date = new Date();
        fileName = ft.format(date).toString() + ".txt";
    }
    @Test
    public void testLoggerFileCreation() throws Exception{
        Logger logger = Logger.getInstance();
        logger.finalize();

        File tmpDir = new File("logs/" + fileName);
        assert(tmpDir.exists());
        assert(tmpDir.length() > 20);
    }

    @After
    public void tearDown(){
        File tmpDir = new File("logs/" + fileName);
        if(tmpDir.exists()){
            tmpDir.delete();
        }
    }
}
