package com.xu.tulingchat.task;

import com.xu.tulingchat.util.Md5Util;
import com.xu.tulingchat.util.MusicUtil;
import com.xu.tulingchat.util.RedisUtil;
import com.xu.tulingchat.util.TokenUtil;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务 @Scheduled的使用
 */
@Component
public class ScheduleTask {

  @Autowired
  private TokenUtil tokenUtil;

  @Value("${netease.cloud.music}")
  private String neteaseMusic;
  @Value("${netease.cloud.music.artist.cat}")
  private String neteaseArtistCatUrl;
  //歌手列表  https://music.163.com/#/discover/artist/cat?id=1001&initial=65
  @Autowired
  private RedisUtil redisUtil;

/*    @Scheduled(cron = "0 48 14 * * ?")
  public void task(){
        System.out.println("开启定时任务："+ new Date());
    }*/

  // Two hours 获取1次token放入缓存
  @Scheduled(fixedRate = 1000 * 60 * 60 * 2)    //单位:ms
  public void taskjob() {
//      String access_token = tokenUtil.getToken();
//      System.out.println("--启动定时任务--,\n 当前的access_token是:" + access_token);
//        redisUtil.set("access_token", access_token, 2 * 60 * 60L);
  }

  //定义一个按一定频率执行的定时任务，每隔1天执行一次
//	@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
//	public void job2() {
  //执行代码
//		System.out.println("Scheduled  TASK START ! ! !");
//	}

  /**
   * 把歌手名称和Url一一对应，存入Mysql
   * 每月1号更新
   */
//  @Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 2)
  @Scheduled(cron = "0 00 00 1 * ?")
  public void crawlerArtist() {
    System.out.println("------------1w243r-----------------");
    //初始化MusicUtil的InitialsList
    for (int i = 65; i <= 90; i++) {
      MusicUtil.InitialsList.add(i);
    }
//		System.out.println("InitialsList:" + MusicUtil.InitialsList);
    String[] artists = MusicUtil.ARTISTS;
    System.out.printf("%d --- %d%n", artists.length, MusicUtil.InitialsList.size());

    //https://music.163.com/	discover/artist/cat?id=ID&initial=INITIAL
    try {
      for (int i = 0, length = artists.length; i < length; i++) {
        String artist = artists[i];//{"1001", "1002", "1003", "2001", "2002", "2003", "6001", "6002", "6003", "7001", "7002", "7003", "4001", "4002", "4003"}
        for (int j = 0, size = MusicUtil.InitialsList.size(); j < size; j++) {
          String letter = MusicUtil.InitialsList.get(j)
              .toString();//[65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90]
          StringBuilder stringBuilder = new StringBuilder();
          String replaceUrl = stringBuilder.append(neteaseMusic).append(neteaseArtistCatUrl)
              .toString().replace("ID", artist).replace("INITIAL", letter);
          System.out.println("replaceUrl:" + replaceUrl);
          Document document = null;
          document = Jsoup.connect(replaceUrl)
              .header("Referer", "http://music.163.com/")
              .header("Host", "music.163.com").get();
          //			document.select("a.f-thide").stream().map(w -> w.text() + "-->" + w.attr("href")).forEach(System.out::println);
          Elements elements = document.select("a.f-thide");
          Iterator<Element> iterator = elements.iterator();
          while (iterator.hasNext()) {
            Element element = iterator.next();
            String text = element.text();//歌手名称
            String href = element.attr("href").trim();//歌手Url
//					System.out.println(text + "---" + href);
            String encrypt = Md5Util.encrypt(text);
            boolean set = redisUtil.set(encrypt, href, 60 * 60 * 24 * 2L);
            System.out.printf("%s -- %s -- %b -- %n", encrypt, href, set);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   *
   * //定义一个按时间执行的定时任务，在每天16:00执行一次。
   @Scheduled(cron = "0 0 16 * * ?")
   public void depositJob() {
   //执行代码
   }
   //定义一个按一定频率执行的定时任务，每隔1分钟执行一次
   @Scheduled(fixedRate = 1000 * 60)
   public void job2() {
   //执行代码
   }
   //定义一个按一定频率执行的定时任务，每隔1分钟执行一次，延迟1秒执行
   @Scheduled(fixedRate = 1000 * 60,initialDelay = 1000)
   public void updatePayRecords() {
   //执行代码
   }
   *
   *
   */
}
