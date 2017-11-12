package com.xu.tulingchat.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

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
    document.select("a.f-thide").stream().map(w -> w.text() + "-->" + w.attr("href"))
        .forEach(System.out::println);
  }

  /**
   * 下载
   * @throws IOException
   */
  @Test
  public void download() throws IOException {
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
            "D:/", "temp.jpg"));

    byte[] buf = new byte[3072];
    int i = 0;
    while ((i = is.read()) != -1) {
      os.write(i);
    }
    os.close();
  }

  /**
   * getAllPictures 提取图片
   */
  public static void main(String[] args) throws Exception {
    Elements elements = Jsoup.connect("http://music.163.com/artist?id=1876")
            .header("Referer", "http://music.163.com/")
            .header("Host", "music.163.com").get().select("ul[class=f-hide] a");
    Element first = elements.first();
    String text = first.text();
    String href = first.attr("href");
    System.out.println(text + "---" + href.substring(href.lastIndexOf('=') + 1));

  }
}
