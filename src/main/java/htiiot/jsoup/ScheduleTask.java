package htiiot.jsoup;

import htiiot.jsoup.util.MessageSendUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleTask extends TimerTask{
    private static Logger log = Logger.getLogger(ScheduleTask.class);
    private Properties prop;
    public ScheduleTask(Properties prop){
        this.prop = prop;
    }
    @Override
    public void run() {
        try {
            int batchCount = Integer.parseInt(prop.getProperty("batchMax"));
            Document d = Jsoup.parse(new URL(prop.getProperty("url")), 10000);
            //获取正在处理的batch数量
            Elements e = d.select("#active");
            String activeBatch = e.text();
            String r = ".*\\((\\d+)\\)";
            Pattern pattern = Pattern.compile(r);
            Matcher matcher = pattern.matcher(activeBatch);
            if (matcher.find()) {
                String result = matcher.group(1);
                if (Integer.parseInt(result) >= batchCount) {
                    //进行预警
                    Map<String, String> sender = new HashMap<>();
                    sender.put("email_name", prop.getProperty("emailUserName"));
                    sender.put("email_password", prop.getProperty("emailPassword"));
                    List<String> list = new ArrayList<String>();
                    list.add(prop.getProperty("noticeEmail"));
                    MessageSendUtil.sendEMAIL(sender, list, prop.getProperty("email.content"), prop.getProperty("email.topic"));
                    log.info("当前未处理的批次数量: "+result+" ,已达到预警阈值,已向管理员: "+prop.getProperty("noticeEmail")+" 发送短信!!!");
                }else {
                    log.info("当前未处理的批次数量: "+result+" ,未达到预警阈值.");
                }
            }
        }catch (Exception e){
            log.error(e);
        }
    }
}
