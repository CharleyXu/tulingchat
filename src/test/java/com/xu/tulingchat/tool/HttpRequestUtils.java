package com.xu.tulingchat.tool;

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
import java.util.Map;

public class HttpRequestUtils {
    private final static String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static String postRequest(String url, Map<String, Object> param) throws ClientProtocolException, IOException, IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        String parameter = JSON.toJSONString(param);
        StringEntity se = null;
        se = new StringEntity(parameter);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(se);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");

        return result;
    }

    public static void main(String[] args){
        //创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //创建请求Get实例
        //http://101.200.44.47/test02?id=456&name=abc   https://www.baidu.com
        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxea74e83f59baa421&secret=43e503703085b8ce0853b3e5cb9eb5f0");

        //设置头部信息进行浏览器模拟行为
//        httpGet.setHeader("Accept", "text/html,application/xhtml+xml," +
//                "application/xml;q=0.9,image/webp,*/*;q=0.8");
//        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
//        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        //httpGet.setHeader("Cookie", "......");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
//                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //得到服务响应状态码
            if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = closeableHttpResponse.getEntity();
                //得到响应实体
                String s = EntityUtils.toString(entity, "utf-8");
                System.out.println("--success--\n"+s);
            }else{
                System.out.println("http get error");
            }
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
