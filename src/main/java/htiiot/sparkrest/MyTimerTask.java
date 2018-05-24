package htiiot.sparkrest;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTimerTask extends TimerTask {
    private String host = null;
    private String appId = null;
    public MyTimerTask(String host,String appId){
        this.host = host;
        this.appId = appId;
    }
    @Override
    public void run() {
        String url = host+"/api/v1/applications/"+appId+"/jobs/status=succeeded";
        String r = HttpClientTool.get(url);
        String regex = ".*/batch/\\?id=([0-9]+).*\n.*submissionTime\"\\s:\\s\"(.+)\".*\n.*completionTime\"\\s:\\s\"(.+)\".*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(r);
        String initBatchID = null;
        int index = 0;
        while(m.find()){
            if(index == 0) {
                initBatchID = m.group(1);

            }else {
                String batchId = m.group(1);
                if(!batchId.equals(initBatchID))break;

            }
            index++;
        }
        System.out.println(url);
        System.out.println(r);
    }

}
