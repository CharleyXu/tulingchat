package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Xu 获取access_token 有效期目前为2个小时，需定时刷新
 */
@Component
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "wechat.token")
public class TokenUtil {

	//使用@Value的类如果被其他类作为对象引用，必须要使用注入的方式，而不能new
	//不加Component注解, 使用@Autowired 就不能注入进去了
	@Value("${wechat.token.tokenUrl}")
	private String tokenUrl;
	@Value("${wechat.token.appId}")
	private String appId;
	@Value("${wechat.token.appSecret}")
	private String appSecret;

	/**
	 * 正常情况  {"access_token":"ACCESS_TOKEN","expires_in":7200} 错误情况  {"errcode":40013,"errmsg":"invalid
	 * appid"}
	 */
	public String getToken() {
		String replaceURL = null;
		replaceURL = tokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
		String request = HttpClientUtil.sendGet(replaceURL, null, null);
		JSONObject object = JSON.parseObject(request);
		if (object.containsKey("access_token")) {
			return object.getString("access_token");
		}
		return null;
	}


}
