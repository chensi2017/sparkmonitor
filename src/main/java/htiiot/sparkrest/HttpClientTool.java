package htiiot.sparkrest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientTool {
    private static Logger log = Logger.getLogger(HttpClientTool.class);
    private static CloseableHttpClient c = HttpClients.createDefault();
    public static String get(String url){
        HttpGet get  = new HttpGet(url);
        CloseableHttpResponse r = null;
        String str = null;
        try {
            r = c.execute(get);
            HttpEntity e = r.getEntity();
            str = EntityUtils.toString(e,"utf-8");
        }catch (Exception e){
            log.error(e);
        }
        return str;
    }
}
