package htiiot.sparkrest;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparkMonitorContext {
    private static Logger log = Logger.getLogger(SparkMonitorContext.class);
    public static void main(String[] args) {
        String host = "http://localhost:4444";
        String s = HttpClientTool.get(host+"/api/v1/applications");
        String regex = ".*\"id\"\\s:\\s\"(.+)\".*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        if (m.find()){
            String appId = m.group(1);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(host,appId),0,300000);
        }else {
            log.info("该地址: "+host+" 无正在运行的spark应用！！！");
        }
    }
}
