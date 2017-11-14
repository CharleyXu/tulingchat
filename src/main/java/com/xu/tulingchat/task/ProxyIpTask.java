package com.xu.tulingchat.task;

import com.xu.tulingchat.entity.ProxyIp;
import com.xu.tulingchat.mapper.ProxyIpMapper;
import com.xu.tulingchat.util.DateUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * 定时任务 @Scheduled的使用
 */
@Component
public class ProxyIpTask {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${crawler.agent.xicidaili.webpage}")
	private String crawlerAgent;
	@Autowired
	private ProxyIpMapper proxyIpMapper;


	@Scheduled(fixedRate = 1000 * 60 * 30)   // 大概半小时抓一次代理Ip	单位:ms
	//@Scheduled(cron = "0 00 00 * * ?")
	public void crawlerJob() {
		Document document = null;
		try {
			document = Jsoup.connect(crawlerAgent)
					.header("Referer", crawlerAgent)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
					.ignoreHttpErrors(true).get();
			Elements select = document.select("tbody").select("tr");
			Iterator<Element> iterator = select.iterator();
			for (int i = 1, size = select.size(); i < size; i++) {
				Element element = select.get(i);
				String ip = element.child(1).text();
				int port = Integer.parseInt(element.child(2).text());
//				System.out.println("ip:"+ip+"-- port:"+port);
				String date = DateUtil.dateToString(new Date(), DateUtil.YYYYMMDDHHMMSS);
				if (proxyIpMapper.findOne(ip) == null) {
					ProxyIp proxyIp = new ProxyIp(ip, port, date, date);
					proxyIpMapper.insert(proxyIp);
				}
			}
			proxyIpMapper.batchDelete("30");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
