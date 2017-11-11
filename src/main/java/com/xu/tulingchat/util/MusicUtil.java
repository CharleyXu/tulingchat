package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSONObject;
import com.xu.tulingchat.mapper.ArtistMapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Netease Cloud Music  工具类
 */
@Component
public class MusicUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String[] ARTISTS = new String[]{"1001", "1002", "1003", "2001", "2002", "2003", "6001", "6002", "6003", "7001", "7002", "7003", "4001", "4002", "4003"};
	public static List<Integer> InitialsList = new ArrayList<>();
	@Autowired
	private TokenUtil tokenUtil;
	@Value("${wechat.material.temporary}")
	@Autowired
	private ArtistMapper artistMapper;
	private String temporaryUrl;
	@Value("${netease.cloud.music.song}")
	private String songUrl;
	@Value("${netease.cloud.music}")
	private String netease;

	/**
	 * 获取歌名+Url
	 *
	 * @param artistName 歌手名称
	 */
	public String[] getSongs(String artistName) {
		String[] retArray = new String[2];
		Elements elements = null;
		try {
			String urlId = artistMapper.findone(artistName);
			elements = Jsoup.connect(netease + urlId)
					.header("Referer", "http://music.163.com/")
					.header("Host", "music.163.com").get().select("ul[class=f-hide] a");
			Element first = elements.first();
			String text = first.text();
			String href = first.attr("href");
			retArray[0] = text;
			retArray[1] = songUrl.replace("SONG_ID", href.substring(href.lastIndexOf('=') + 1));
			logger.info("歌名+歌曲Url获取成功");
			return retArray;
		} catch (IOException e) {
			logger.error("歌名+歌曲Url获取错误",e);
		}
		return null;

	}

	/**
	 * 上传缩略图
	 *
	 * @return media_id
	 */
	public String uploadThumb() {
		try {
			downLoadPict();
			logger.info("下载图片成功");
		} catch (IOException e) {
			logger.error("下载图片失败", e);
		}
		String access_token = tokenUtil.getToken();
		System.out.println("--当前的access_token是:" + access_token);
		String replaceUrl = temporaryUrl.replace("ACCESS_TOKEN", access_token).replace("TYPE", "thumb");
		try {
			Map<String, String> fileBody = new HashMap<>();
			fileBody.put("media", "/home/xuzc/temp.jpg");
			Map<String, String> stringBody = new HashMap<>();
			stringBody.put("access_token", access_token);
			stringBody.put("image", "thumb");
			String result = HttpRequestUtil.doUpload(replaceUrl, fileBody, stringBody);
			logger.info("result:" + result);
			logger.info("上传Thumb成功");
			return JSONObject.parseObject(result).getString("thumb_media_id");
		} catch (IOException e) {
			logger.error("上传Thumb失败", e);
		}
		return null;
	}

	/**
	 * 下载图片到临时路径
	 */
	public void downLoadPict() throws IOException {
		Elements select = Jsoup.connect("http://music.163.com/song?id=418603077")
				.header("Referer", "http://music.163.com/")
				.header("Host", "music.163.com").get().select("img[src]");
		System.out.println(select.size());
		Element first = select.first();
		String attr = first.attr("abs:src");
		System.out.println("first:" + attr);
		// 连接url
		URL url;
		url = new URL(attr);
		URLConnection uri = url.openConnection();
		// 获取数据流
		InputStream is = uri.getInputStream();
		// 写入数据流
		OutputStream os = new FileOutputStream(new File(
				"/home/xuzc", "temp.jpg"));

		byte[] buf = new byte[3072];
		int i = 0;
		while ((i = is.read()) != -1) {
			os.write(i);
		}
		os.close();
	}

	public static void main(String[] args) throws Exception {
//		Jsoup.connect("http://music.163.com/playlist?id=317113395")
//				.header("Referer", "http://music.163.com/")
//				.header("Host", "music.163.com").get().select("ul[class=f-hide] a")
//				.stream().map(w-> w.text() + "-->" + w.attr("href"))
//				.forEach(System.out::println);
		//http://music.163.com/discover/artist/         .select("ul[class=f-hide] a")
		Document document = Jsoup.connect("http://music.163.com/discover/artist/cat?id=1001&initial=65")
				.header("Referer", "http://music.163.com/")
				.header("Host", "music.163.com").get();
		document.select("a.f-thide").stream().map(w -> w.text() + "-->" + w.attr("href")).forEach(System.out::println);
	}


}
