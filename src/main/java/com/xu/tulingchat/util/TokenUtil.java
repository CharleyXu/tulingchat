package com.xu.tulingchat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 获取access_token
 * 有效期目前为2个小时，需定时刷新
 */
@Component  //不加这个注解的话, 使用@Autowired 就不能注入进去了
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")
@ConfigurationProperties(prefix = "wechat.token")
public class TokenUtil {

//    //单例      这里不能使用单例
//    private TokenUtil(){
//    }
//    private static class TokenUtilHolder{
//        private static TokenUtil instance = new TokenUtil();
//    }
//    public static final TokenUtil getInstance(){
//        return  TokenUtilHolder.instance;
//    }
    //使用@Value的类如果被其他类作为对象引用，必须要使用注入的方式，而不能new
    @Value("${wechat.token.tokenUrl}")
    private String tokenUrl;
    @Value("${wechat.token.appId}")
    private String appId;
    @Value("${wechat.token.appSecret}")
    private String appSecret;

    //正常情况  {"access_token":"ACCESS_TOKEN","expires_in":7200}
    //错误情况  {"errcode":40013,"errmsg":"invalid appid"}
    public String getToken(){
        System.out.println(tokenUrl);
        String replaceURL = null;
        replaceURL = tokenUrl.replace("APPID", appId).replace("APPSECRET", appSecret);
        String request = HttpRequestUtils.getRequest(replaceURL);
        return request;
    }


}
