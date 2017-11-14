package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xu.tulingchat.bean.message.send.Article;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 知乎日报工具类 # 1.最新消息        2.消息内容获取与离线下载     3.过往消息(11 月 18 日的消息，before 后的数字应为 20131119,要小于
 * 20130520) # 4.主题日报列表查看       5.主题日报内容查看
 */
@Component
public class DailyZhihuUtil {
	@Value("${daily.zhihu.latest}")
	private String latestUrl;
	@Value("${daily.zhihu.news}")
	private String newsUrl;
	@Value("${daily.zhihu.beforenews}")
	private String beforenewsUrl;
	@Value("${daily.zhihu.themes}")
	private String themesUrl;
	@Value("${daily.zhihu.themes.contents}")
	private String themesContentsUrl;

	/**
	 * get请求
	 */
	public List<Article> sendRequest(String name) {
		String request = HttpClientUtil.sendGet(latestUrl, null, null);
		JSONObject jsonObject = JSONObject.parseObject(request);
		JSONArray top_stories = jsonObject.getJSONArray("top_stories");
		List<Article> list = new ArrayList<>();
		for (int i = 0, size = top_stories.size(); i < size; i++) {
			JSONObject object = top_stories.getJSONObject(i);
			String id = object.getString("id");//消息内容Id
//			System.out.println("--id--"+id);
			String replaceUrl = newsUrl.replace("id", id);
			String s = HttpClientUtil.sendGet(replaceUrl, null, null);
//			System.out.println("content:"+s);
			JSONObject jo = JSONObject.parseObject(s);
			Article article = new Article();
			article.setTitle(jo.getString("title"));
			article.setDescription("");
			article.setPicUrl(jo.getString("image"));
			article.setUrl(jo.getString("share_url"));
			list.add(article);
		}
		return list;
	}
}
