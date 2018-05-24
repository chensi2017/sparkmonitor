package htiiot.jsoup;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Timer;

public class JsoupAnalysis {
    private static Logger log = Logger.getLogger(JsoupAnalysis.class);
    public static void main(String[] args){
//        File confFile = new File("conf/sparkmonitor.properties");
        File confFile = new File(log.getClass().getResource("/conf/sparkmonitor.properties").getPath());
        try {
            if (confFile.exists()) {
                Properties prop = new Properties();
                prop.load(new FileInputStream(confFile));
                Timer timer = new Timer();
                timer.schedule(new ScheduleTask(prop),0,Long.parseLong(prop.getProperty("monitor.interval")));
            } else {
                log.error("文件不存在");
            }
        }catch (Exception e){
            log.error(e);
        }
    }
}
