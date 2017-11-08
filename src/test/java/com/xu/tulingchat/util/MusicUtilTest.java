package com.xu.tulingchat.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicUtilTest {
	//		Jsoup.connect("http://music.163.com/playlist?id=317113395")
//				.header("Referer", "http://music.163.com/")
//				.header("Host", "music.163.com").get().select("ul[class=f-hide] a")
//				.stream().map(w-> w.text() + "-->" + w.attr("href"))
//				.forEach(System.out::println);
	//http://music.163.com/discover/artist/         .select("ul[class=f-hide] a")
	@Test
	public void getAllArtists() throws IOException {
		Document document = Jsoup.connect("http://music.163.com/discover/artist/cat?id=1001&initial=65")
				.header("Referer", "http://music.163.com/")
				.header("Host", "music.163.com").get();
		document.select("a.f-thide").stream().map(w -> w.text() + "-->" + w.attr("href")).forEach(System.out::println);
	}

	//     阿杜--> /artist?id=1876
	@Test
	public void getAllSongs() throws IOException {
		Jsoup.connect("http://music.163.com/artist?id=1876")
				.header("Referer", "http://music.163.com/")
				.header("Host", "music.163.com").get().select("ul[class=f-hide] a")
				.stream().map(w -> w.text() + "-->" + w.attr("href"))
				.forEach(System.out::println);
	}
}
