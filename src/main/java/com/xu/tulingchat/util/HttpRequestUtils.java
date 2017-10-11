package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpRequestUtils {
    private final static String CONTENT_TYPE_TEXT_JSON = "text/json";

    //一般用get
    public static String getRequest(String url) {
        //创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        //创建请求Get实例
        HttpGet httpGet = new HttpGet(url);
        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            //得到服务响应状态码
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                //得到响应实体
                HttpEntity entity = closeableHttpResponse.getEntity();
                String s = EntityUtils.toString(entity, "utf-8");
                System.out.println("--get request success--\n" + s);
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //post暂时不太好使
    public static String postRequest(String url, Map<String, Object> param){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //text/html;charset=UTF-8       application/json;charset=UTF-8
        httpPost.setHeader("Content-Type", "text/html;charset=UTF-8");
        String parameter = JSON.toJSONString(param);
        StringEntity se = null;
        try {
            se = new StringEntity(parameter);
            //CONTENT_TYPE_TEXT_JSON        text/html;charset=UTF-8
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            httpPost.setEntity(se);
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("--post request success--\n" + result);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //http://101.200.44.47/test02?id=456&name=abc   https://www.baidu.com
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
        String request = getRequest("http://101.200.44.47/demo01");
        Map<String,Object> map = new HashMap<>();
        map.put("id","89");
        String s = postRequest("http://101.200.44.47/demo01", null);
    }

}
