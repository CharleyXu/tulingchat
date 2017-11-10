package com.xu.tulingchat.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpRequestUtil {
	private final static String CONTENT_TYPE_TEXT_JSON = "text/json";
	//快递接口：http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
	//:快递公司编码:申通=”shentong” EMS=”ems” 顺丰=”shunfeng”
	// 圆通=”yuantong” 中通=”zhongtong” 韵达=”yunda” 天天=”tiantian”
	//汇通=”huitongkuaidi” 全峰=”quanfengkuaidi” 德邦=”debangwuliu” 宅急送=”zhaijisong”

	/**
	 * get请求
	 */
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
				return EntityUtils.toString(entity, "utf-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送POST请求（HTTP），K-V形式
	 */
	public static String postRequest(String url, Map<String, String> params) {
		// 创建默认的HttpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 定义一个get请求方法
			HttpPost httppost = new HttpPost(url);

			// List parameters = new ArrayList();
			// parameters.add(new BasicNameValuePair("username", userName));
			// parameters.add(new BasicNameValuePair("password", password));

			// 定义post请求的参数
			// 建立一个NameValuePair数组，用于存储欲传送的参数
			List list = new ArrayList();
			Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> elem = iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				httppost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			}
			// httppost.setHeader("Content-type","application/json,charset=utf-8");
			// httppost.setHeader("Accept", "application/json");
			// 执行post请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
			CloseableHttpResponse httpResponse = httpclient.execute(httppost);
			// 使用响应对象, 获得状态码, 处理内容
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				// 使用响应对象获取响应实体
				HttpEntity entity = httpResponse.getEntity();
				// 将响应实体转为字符串
				String response = EntityUtils.toString(entity, "utf-8");
				return response;
			} else {
				// log.error("访问失败"+statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接, 和释放资源
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 通过HttpClient post 上传文件
	 *
	 * @param url        上传请求地址
	 * @param fileBody   文件类型 builder.addBinaryBody
	 * @param stringBody 普通类型 builder.addTextBody
	 * @return 状态码 200表示成功
	 */
	public static String doUpload(String url, Map<String, String> fileBody, Map<String, String> stringBody) throws ClientProtocolException, IOException {
		// 创建默认的HttpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 获得utf-8编码的mbuilder
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setBoundary(getBoundaryStr("7da2e536604c8")).setCharset(Charset.forName("utf-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		if (fileBody != null) {
			Iterator<Map.Entry<String, String>> iterator = fileBody.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> fileEntry = iterator.next();
				String param = fileEntry.getKey();
				String value = fileEntry.getValue();
				builder.addPart(param, new FileBody(new File(value)));
			}
		}
		if (stringBody != null) {
			Iterator<Map.Entry<String, String>> iterator = stringBody.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> stringEntry = iterator.next();
				String param = stringEntry.getKey();
				String value = stringEntry.getValue();
				builder.addPart(param, new StringBody(value, ContentType.TEXT_PLAIN));
			}
		}
		// 创建我们的http多媒体对象
		HttpEntity entity = builder.build();
		// post请求体
		HttpPost post = new HttpPost(url);

		post.addHeader("Connection", "keep-alive");
		post.addHeader("Accept", "*/*");
		post.addHeader("Content-Type", "multipart/form-data;boundary="
				+ getBoundaryStr("7da2e536604c8"));
		post.addHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		post.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(post);
//		int code = response.getStatusLine().getStatusCode();
//		System.out.println("code："+code);
		//将响应实体转为字符串
		String s = EntityUtils.toString(response.getEntity(), "utf-8");
		httpClient.close();
		return s;
	}

	private static String getBoundaryStr(String str) {
		return "------------" + str;
	}

	public static void main(String[] args) {
		//http://101.200.44.47/test02?id=456&name=abc   https://www.baidu.com
		//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
//        String request = getRequest("http://101.200.44.47/demo01");
		Map<String, String> map = new HashMap<>();
		map.put("key", "1ba986e0d8664d4099121844c6ba0317");
		map.put("info", "你好");
		map.put("userid", "123456");
		String s = postRequest("http://www.tuling123.com/openapi/api", map);
		System.out.println(s);
	}

}
