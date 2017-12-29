package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Netease Cloud Music  工具类
 */
@Component
public class MusicUtil {

	public static final Random RANDOM = new Random();
	public static final String[] ARTISTS = new String[]{"1001", "1002", "1003", "2001", "2002", "2003", "6001", "6002", "6003", "7001", "7002", "7003", "4001", "4002", "4003"};
	private static final Logger LOGGER = LoggerFactory.getLogger(MusicUtil.class);
	public static List<Integer> initialsList = new ArrayList<>();
	@Value("${wechat.material.temporary}")
	private String temporary;//临时素材
	@Value("${wechat.material.permanent}")
	private String permanent;//永久素材
	@Value("${netease.cloud.music.song}")
	private String songUrl;
	@Value("${netease.cloud.music}")
	private String netease;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private RedisUtil redisUtil;
	private static String FILE_PATH = "D:\\temp.jpg";        //	D:/temp.jpg		/home/xuzc/temp.jpg
	/**
	 * 获取歌名+Url
	 *
	 * @param artistName 歌手名称
	 */
	public String[] getSongs(String artistName) {
		String[] retArray = new String[2];
		Elements elements = null;
		try {
			String urlId = "/artist?id=6452";
//			String encrypt = Md5Util.encrypt(artistName);
			urlId = redisUtil.get(artistName);
			System.out.println("urlId:" + urlId);
			System.out.println("ConnectUrl:" + netease + urlId);
			elements = Jsoup.connect(netease + urlId)
					.header("Referer", "http://music.163.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
					.header("Host", "music.163.com")
					.get().select("ul[class=f-hide] a");
			//
			int size = elements.size();
			Element element = elements.get(RANDOM.nextInt(size));
//			Element first = elements.first();
			String text = element.text();
			String href = element.attr("href");
			retArray[0] = text;
			retArray[1] = songUrl.replace("SONG_ID", href.substring(href.lastIndexOf('=') + 1));
			LOGGER.info("歌名+歌曲Url获取成功");
			return retArray;
		} catch (IOException e) {
			LOGGER.error("歌名+歌曲Url获取错误", e);
		} catch (Exception e) {
			LOGGER.error("歌名+歌曲Url获取错误", e);
		}
		return null;

	}

	/**
	 * 上传缩略图
	 *
	 * @return media_id
	 */
	public String uploadThumb(String songId) {
		System.out.println("-----songId：" + songId);
//		downLoadPict(songId);
		String access_token = null;
//		access_token = redisUtil.get("access_token");
		if (access_token == null) {
			access_token = tokenUtil.getToken();
		}
		System.out.println("--当前的access_token是:" + access_token);
		//	temporary	临时	permanent	永久
		String replaceUrl = permanent.replace("ACCESS_TOKEN", access_token).replace("TYPE", "thumb");

		try {
			Map<String, String> fileBody = new HashMap<>();
			fileBody.put("media", FILE_PATH);
			Map<String, String> stringBody = new HashMap<>();
			stringBody.put("access_token", access_token);
			stringBody.put("image", "thumb");
			String result = HttpClientUtil.doUpload(replaceUrl, fileBody, stringBody);
			LOGGER.info("result:" + result);
			LOGGER.info("上传Thumb成功");
			return JSONObject.parseObject(result).getString("thumb_media_id");
		} catch (IOException e) {
			LOGGER.error("上传Thumb失败", e);
		}
		return null;
	}

	/**
	 * 下载图片到临时路径
	 */
	public void downLoadPict(String songId) {
		String downUrl = "http://music.163.com/song/29802464";
		if (songId != null) {
			downUrl = songId;
		}
		int i = 0;
		Elements select = null;
		try {
			select = Jsoup.connect(downUrl)
					.header("Referer", "http://music.163.com/")
					.header("Host", "music.163.com").get().select("img[src]");
			Element first = select.first();
			String attr = first.attr("abs:src");
			i = HttpClientUtil.doDownload(attr, FILE_PATH);
			LOGGER.info("图片下载成功" + i);
		} catch (IOException e) {
			LOGGER.error("图片下载失败", e);
		}
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
