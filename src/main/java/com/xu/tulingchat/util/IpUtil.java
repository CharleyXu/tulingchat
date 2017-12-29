package com.xu.tulingchat.util;

import com.google.common.base.Strings;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ip工具类
 */
public class IpUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);
	/**
	 * 获取访问Ip
	 */
	public static String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 校验代理IP的有效性，测试地址为：http://www.baidu.com
	 *
	 * @param ip   代理IP地址
	 * @param port 代理IP端口
	 * @return 此代理IP是否有效
	 */
	public static boolean checkValidIP(String ip, Integer port) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL("http://www.baidu.com");
			//代理服务器
			InetSocketAddress proxyAddr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
			connection = (HttpURLConnection) url.openConnection(proxy);
			connection.setReadTimeout(4000);
			connection.setConnectTimeout(4000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 200) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("checkValidIP Error", e.toString());
			return false;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		boolean b = checkValidIP("219.136.172.180", 9797);
		System.out.println(b);
		if (b == false) {
			return;
		}
//		Map<String, String> headers = new HashMap<>();
//		headers.put("Referer", "http://music.163.com/discover/artist");
//		headers.put("Host", "music.163.com");
//		headers.put("Origin", "http://music.163.com");
//		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
//		String result = HttpClientUtil.sendProxyGet("113.83.240.249", 53281, "http://music.163.com/discover/artist/cat?id=1001&initial=65", headers, null);
//		System.out.println(result);
//		headers.clear();
//		headers.put("Referer", "https://www.baidu.com/");
//		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
////		headers.put("","");
//		String s = HttpClientUtil.sendGet("https://www.baidu.com/", null, null);
//		System.out.println(s);

//		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("219.136.172.180", 9797));
		Document document = null;
		try {
			document = Jsoup.connect("http://music.163.com/discover/artist/cat?id=1001&initial=65")
					.header("Referer", "http://music.163.com/discover/artist")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
					.header("Host", "music.163.com")
					.header("Origin", "http://music.163.com").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (document == null || Strings.isNullOrEmpty(document.head().data())) {
			System.out.println("-----");
		} else {
			System.out.println(document.body().text());
			document.select("a.f-thide").stream().map(w -> w.text() + "-->" + w.attr("href")).forEach(System.out::println);
		}

	}
}
