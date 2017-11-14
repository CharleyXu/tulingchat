package com.xu.tulingchat.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {

	/**
	 * Http Get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String sendGet(String url, Map<String, String> headers, Map<String, String> params) {
		//创建客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return sendRequest(httpClient, url, headers, params);
	}

	/**
	 * HttpClient 设置代理
	 * @param ip
	 * @param port
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String sendProxyGet(String ip, int port, String url, Map<String, String> headers, Map<String, String> params) {
		HttpHost proxy = new HttpHost(ip, port);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(15000).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return sendRequest(httpClient, url, headers, params);
	}

	/**
	 * 发送请求体
	 */
	public static String sendRequest(CloseableHttpClient closeableHttpClient, String url, Map<String, String> headers, Map<String, String> params) {
		StringBuilder reqUrl = new StringBuilder(url);
		//设置param参数
		if (params != null && params.size() > 0) {
			reqUrl.append("?");
			for (Map.Entry<String, String> param : params.entrySet()) {
				reqUrl.append(param.getKey() + "=" + param.getValue() + "&");
			}
			url = reqUrl.subSequence(0, reqUrl.length() - 1).toString();
		}
		//创建请求Get实例
		HttpGet httpGet = new HttpGet(url);
		// 设置头部
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				httpGet.addHeader(header.getKey(), header.getValue());
			}
		}
		//接受到的响应
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			//客户端执行httpGet方法，返回响应
			closeableHttpResponse = closeableHttpClient.execute(httpGet);
			//得到服务响应状态码
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				//得到响应实体
				HttpEntity entity = closeableHttpResponse.getEntity();
				return EntityUtils.toString(entity, "utf-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			closeSource(closeableHttpClient, closeableHttpResponse);
		}
		return null;
	}

	/**
	 * Http Post请求
	 */
	public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
		// 创建默认的HttpClient实例.
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			// 定义一个post请求方法
			HttpPost httpPost = new HttpPost(url);
			//设置param参数
			List list = new ArrayList();
			if (params != null && params.size()>0){
				for (Map.Entry<String, String> param : params.entrySet()) {
					list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			// 模拟表单提交
			if (list.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			}
			//设置头部
			if (headers != null && headers.size() > 0) {
				if (headers != null && headers.size() > 0) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						httpPost.addHeader(header.getKey(), header.getValue());
					}
				}
			}
			// 执行post请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
			closeableHttpResponse = closeableHttpClient.execute(httpPost);
			// 使用响应对象, 获得状态码, 处理内容
			if ( closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				// 使用响应对象获取响应实体
				HttpEntity entity = closeableHttpResponse.getEntity();
				// 将响应实体转为字符串
				String response = EntityUtils.toString(entity, "utf-8");
				return response;
			} else {
				System.out.println("Post请求失败,状态码:"+closeableHttpResponse.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSource(closeableHttpClient, closeableHttpResponse);
		}
		return null;
	}

	//关闭资源
	public static void closeSource(CloseableHttpClient closeableHttpClient, CloseableHttpResponse closeableHttpResponse){
		try {
			if (closeableHttpResponse != null) {
				closeableHttpResponse.close();
			}
			// 关闭连接, 和释放资源
			closeableHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * HttpClient post 上传文件
	 *
	 * @param url        上传请求地址
	 * @param fileBody   文件类型 builder.addBinaryBody
	 * @param stringBody 普通类型 builder.addTextBody
	 * @return 状态码    200表示成功
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

	/**
	 * 通过httpClient get 下载文件
	 *
	 * @param url      网络文件全路径
	 * @param savePath 保存文件全路径
	 * @return 状态码 200表示成功
	 */
	public static int doDownload(String url, String savePath) {
		// 创建默认的HttpClient实例.
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			closeableHttpResponse = closeableHttpClient.execute(get);
			HttpEntity entity = closeableHttpResponse.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				FileOutputStream out = new FileOutputStream(savePath);
				IOUtils.copy(in, out);
				EntityUtils.consume(entity);
				closeableHttpResponse.close();
			}
			int code = closeableHttpResponse.getStatusLine().getStatusCode();
			closeableHttpClient.close();
			return code;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeSource(closeableHttpClient, closeableHttpResponse);
		}
		return 0;
	}

	public static void main(String[] args) {
		//http://101.200.44.47/test02?id=456&name=abc   https://www.baidu.com
		//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
//        String request = getRequest("http://101.200.44.47/demo01");
		Map<String, String> map = new HashMap<>();
		map.put("key", "1ba986e0d8664d4099121844c6ba0317");
		map.put("info", "你好");
		map.put("userid", "123456");
		String s = sendPost("http://www.tuling123.com/openapi/api", null,map);
		System.out.println(s);
	}

}
