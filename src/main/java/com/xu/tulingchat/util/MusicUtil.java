package com.xu.tulingchat.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Netease Cloud Music  工具类
 */
@Component
public class MusicUtil {
	public static final String[] ARTISTS = new String[]{"1001", "1002", "1003", "2001", "2002", "2003", "6001", "6002", "6003", "7001", "7002", "7003", "4001", "4002", "4003"};
	public static List<Integer> InitialsList = new ArrayList<>();

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

	public String[] getSongs(String artist) {
		Elements elements = null;
		String[] retArray = new String[2];
		try {
			elements = Jsoup.connect("http://music.163.com/artist?id=1876")
					.header("Referer", "http://music.163.com/")
					.header("Host", "music.163.com").get().select("ul[class=f-hide] a");
			Element first = elements.first();
			String text = first.text();
			String href = first.attr("href");
			retArray[0] = text;
			retArray[1] = href;
			return retArray;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}


}
