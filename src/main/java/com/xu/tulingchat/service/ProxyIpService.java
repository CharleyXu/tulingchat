package com.xu.tulingchat.service;

import com.xu.tulingchat.mapper.ProxyIpMapper;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyIpService {

  @Autowired
  private ProxyIpMapper proxyIpMapper;

  public void getProxyIp() {

  }

  public static void main(String[] args) {
    Document document = null;
    try {
      document = Jsoup.connect("http://www.xicidaili.com/nt/")
          .header("Referer", "http://www.xicidaili.com/nt/")
          .header("User-Agent",
              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
          .ignoreHttpErrors(true).get();
      Elements select = document.select("tbody").select("tr");
      Iterator<Element> iterator = select.iterator();
      while (iterator.hasNext()) {
        Element next = iterator.next();
        System.out.println("Ip：" + next.child(1).text() + ",端口:" + next.child(2).text());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
