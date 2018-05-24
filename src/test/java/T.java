import java.text.SimpleDateFormat;
import java.util.Date;

public class T {
    public static void main(String[] args) throws Exception{
        String s = "2018-05-23 08:50:35.083";
        long s1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(s).getTime();
        System.out.println(s1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(1527069650000l)));
    }
}
