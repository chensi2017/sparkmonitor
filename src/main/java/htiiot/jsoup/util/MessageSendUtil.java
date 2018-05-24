package htiiot.jsoup.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预警消息发送中心工具类
 * @author Administrator
 *
 */
public class MessageSendUtil{

	private static Logger log = Logger.getLogger(MessageSendUtil.class);
	
	/**
	 * 发送邮件
	 * @param sender	发件人
	 * 格式如下:
	 * sender.put("email_name", "邮箱账号");
	 * sender.put("email_password", "邮箱密码");
	 * @param receiver	收件人列表;
	 * @param content	通知内容
	 * @param subject	邮件通知的主题
	 * @return	true发送成功 false发送失败
	 */
	public static boolean sendEMAIL(Map<String, String> sender,List<String> receiver,String content,String subject){
		try {

			String email_name = (String) sender.get("email_name");
			String email_password = (String) sender.get("email_password");
			//写一个工具类,获取发件人邮箱对于的发送协议和端口;
			Map<String, Object> email_detail = getEmailDetail(email_name);
			
			//开始准备发送邮件通知
			SimpleEmail semial = new SimpleEmail();
	    	semial.setHostName((String) email_detail.get("email_smtp"));
	    	semial.setSmtpPort(25);
	    	semial.setSSL(true);
	    	
	    	/**
	    	 * smtp服务器是smtp.live.com，有要求安全连接(SSL)
	    	 * 在配置JavaMail的Properties事，不要指定“mail.smtp.socketFactory.class”，因为TLS使用的是普通的Socket。
	    	 * 然后指定属性“mail.smtp.starttls.enable”为“true”。
	    	 */
	    	//这句非常重要,否则有时候会报一个ssl安全的错误;
	    	semial.setStartTLSEnabled(true);
	    	
	    	semial.setSslSmtpPort((String) email_detail.get("email_port"));
	    	semial.setAuthentication(email_name, email_password);
	    	semial.setCharset("UTF-8");
	    	
	    	semial.setFrom(email_name);
			String[] receiver_array = receiver.toArray(new String[0]);
			//设置群发;
			semial.addTo(receiver_array);
			semial.setSubject(subject);
			semial.setMsg(content);
			semial.send();
			
		} catch (Exception e) {
			e.printStackTrace();
			String em = "";
			if(receiver!=null && receiver.size() ==1){
				em = receiver.get(0);
			}
			log.error("收件人:"+em+",邮件发送出错:",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取发件人的邮箱协议和SSL端口
	 * @param email_name
	 * @return
	 */
	public static Map<String, Object> getEmailDetail(String email_name){
		Map<String, Object> email_detail = new HashMap<String, Object>();
		
		Map<String, Object> nomail_email = getNomalEmail();
		
		if(StringUtils.isNotBlank(email_name)){
			String eamil_type = email_name.substring(email_name.lastIndexOf("@")+1,email_name.length());
			String email_smtp = (String) nomail_email.get(eamil_type);
			email_detail.put("email_smtp", email_smtp);
			email_detail.put("email_port", "465");
		}
		return email_detail;
	}
	
	/**
	 * 获取常见的邮箱后缀及对应的smtp协议;
	 * @return
	 */
	public static Map<String, Object> getNomalEmail(){
		/**
		 * 定义常见的邮箱后缀以及对应的smtp协议
		 * 这部分内容,后期可以考虑做到属性或配置文件或者缓存中;
		 */
		Map<String, Object> nomail_email = new HashMap<String, Object>();
		//key:为邮箱后缀; value:为所对应的smtp协议
		nomail_email.put("longshine.com", "smtp.longshine.com");
		nomail_email.put("qq.com", "smtp.qq.com");
		nomail_email.put("163.com", "smtp.163.com");
		nomail_email.put("gmail.com", "smtp.gmail.com");
		nomail_email.put("126.com", "smtp.126.com");
		nomail_email.put("sina.com", "smtp.sina.com");
		nomail_email.put("sohu.com", "smtp.sohu.com");
		nomail_email.put("139.com", "smtp.139.com");
		
		//增加订阅通知的生产环境邮箱
		nomail_email.put("htdata.com", "smtp.ym.163.com");
		
		return nomail_email;
	}
	
/*	*//**
	 * 发送短信
	 * @param freesignname	短信签名,如果为null则默认为订阅系统的短信签名
	 * @param templatecode	短信模板id,如果为null则默认为订阅系统的短信签名
	 * @param phone_num		发件人的手机号码;
	 * @param param			对应的短信模板id对应的模板内容中需要的参数替换;
	 * @return
	 *//*
	public static boolean sendSMS(String freesignname, String templatecode, String phone_num, String param){
		try {
			
			ProUtil proUtil = ProUtil.getInstance();
			String appkey = proUtil.getValue("subscription.sms.appkey");
			String secret = proUtil.getValue("subscription.sms.secret");
			if(StringUtils.isBlank(freesignname)){
				freesignname = proUtil.getValue("subscription.sms.freesignname");
			}
			if(StringUtils.isBlank(templatecode)){
				templatecode = proUtil.getValue("subscription.sms.templatecode");
			}
			
	        String url = "http://gw.api.taobao.com/router/rest";
	        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
	        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
	        req.setExtend("sms");
	        req.setSmsType("normal");
	        req.setSmsFreeSignName(freesignname);
	        req.setRecNum(phone_num);
	        //模板
	        req.setSmsTemplateCode(templatecode);
	        //模板中的参数，按照实际情况去
	        req.setSmsParamString(param);

	        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
	            
	        String send_result = rsp.getBody();
	        log.info("手机号:"+phone_num+"订阅通知的短信发送结果send_result:"+send_result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("手机号:"+phone_num+"订阅通知的短信发送出错:", e);
			return false;
		}
		return true;
	}*/
	
}
