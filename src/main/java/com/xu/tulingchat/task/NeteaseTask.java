package com.xu.tulingchat.task;

import com.google.common.base.Strings;
import com.xu.tulingchat.entity.ProxyIp;
import com.xu.tulingchat.mapper.ProxyIpMapper;
import com.xu.tulingchat.util.IpUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NeteaseTask {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${netease.cloud.music}")
	private String neteaseMusic;
	@Value("${netease.cloud.music.artist.cat}")
	private String neteaseArtistCatUrl;
	@Value("${netease.cloud.music.song}")
	private String songUrl;

	//歌手列表  https://music.163.com/#/discover/artist/cat?id=1001&initial=65
	@Autowired
	private ProxyIpMapper proxyIpMapper;

	@Scheduled(fixedRate = 1000 * 60 * 60 * 12L)
	public void crawlerTask() {
//		//初始化MusicUtil的InitialsList
//		for (int i = 65; i <= 90; i++) {
//			MusicUtil.InitialsList.add(i);
//		}
//		String ip = null;
//		int port = 80;
//		String[] artists = MusicUtil.ARTISTS;
//		System.out.printf("%d --- %d%n", artists.length, MusicUtil.InitialsList.size());
//		//https://music.163.com/	discover/artist/cat?id=ID&initial=INITIAL
//		Document document = null;
//		Elements elements = null;
//		Element element = null;
//		for (int i = 0, length = artists.length; i < length; i++) {
//			String artist = artists[i];//{"1001", "1002", "1003", "2001", "2002", "2003", "6001", "6002", "6003", "7001", "7002", "7003", "4001", "4002", "4003"}
//			for (int j = 0, size = MusicUtil.InitialsList.size(); j < size; j++) {
//				String letter = MusicUtil.InitialsList.get(j).toString();//[65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90]
//				String artistUrl = (neteaseMusic + neteaseArtistCatUrl)
//						.replace("ID", artist).replace("INITIAL", letter);
//				System.out.println("artistUrl:" + artistUrl);
//				document = getDocument(artistUrl);
//				elements = document.select("a.f-thide");
//				Iterator<Element> iterator = elements.iterator();
//				while (iterator.hasNext()) {
//					element = iterator.next();
//					String singer = element.text();//歌手名称
//					String href = element.attr("href").trim();//歌手Url
//					System.out.printf("%s- -%s \n", singer, href);
//					document = getDocument(neteaseMusic + href);
//					elements = document.select("ul[class=f-hide] a");
//					Iterator<Element> it = elements.iterator();
//					while (it.hasNext()) {
//						element = it.next();
//						String name = element.text();//歌名
//						String url = element.attr("href");
//						url = songUrl.replace("SONG_ID", url.substring(url.lastIndexOf('=') + 1));//歌曲Url  http://music.163.com/song/SONG_ID
//						Song song = new Song(name, singer, url, "NeteaseMusic");//name, String singer, String url, String source
//						songMapper.insert(song);
//					}
//				}
//			}
//		}
	}

	/**
	 * 解析URL,获取Document
	 */
	public Document getDocument(String url) {
		Document document = null;
		ProxyIp newestOne = proxyIpMapper.findNewestOne();
		String ip = newestOne.getIp();
		int port = newestOne.getPort();
		while (!IpUtil.checkValidIP(ip, port)) {
			proxyIpMapper.deleteByIp(ip);
			newestOne = proxyIpMapper.findNewestOne();
			ip = newestOne.getIp();
			port = newestOne.getPort();
		}
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
		try {
			document = Jsoup.connect(url).proxy(proxy)
					.header("Referer", "http://music.163.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
					.header("Host", "music.163.com").ignoreHttpErrors(true).get();
			if (Strings.isNullOrEmpty(document.html())) {
				proxyIpMapper.deleteByIp(ip);
				getDocument(url);
			}
		} catch (IOException e) {
			proxyIpMapper.deleteByIp(ip);
			logger.error("Get Document Error!!!", e);
			getDocument(url);
		}
		return document;
	}
}
