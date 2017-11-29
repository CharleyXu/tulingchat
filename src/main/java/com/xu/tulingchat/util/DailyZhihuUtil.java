package com.xu.tulingchat.util;

import com.google.common.base.Strings;

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
	public List<Article> getStories(String date) {
		if (Strings.isNullOrEmpty(date)) {
			return getLatestNews();
		} else {
			return getBeforeNews(date);
		}
	}

	/**
	 * 最新
	 */
	public List<Article> getLatestNews() {
		String request = HttpClientUtil.sendGet(latestUrl, null, null);
		JSONObject jsonObject = JSONObject.parseObject(request);
		JSONArray top_stories = jsonObject.getJSONArray("top_stories");
		return getNewsListFromJsonArray(top_stories);
	}

	/**
	 * 过往消息
	 */
	public List<Article> getBeforeNews(String date) {
		beforenewsUrl = beforenewsUrl.replace("date", date);
		String request = HttpClientUtil.sendGet(beforenewsUrl, null, null);
		JSONObject jsonObject = JSONObject.parseObject(request);
		JSONArray stories = jsonObject.getJSONArray("stories");
		return getNewsListFromJsonArray(stories);
	}

	public List<Article> getNewsListFromJsonArray(JSONArray jsonArray) {
		List<Article> list = new ArrayList<>();
		int size = jsonArray.size();
		int lastindex = 0;
		if (size >= 5) {
			lastindex = 5;
		} else {
			lastindex = size;
		}
		for (int i = 0; i < lastindex; i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String id = object.getString("id");//消息内容Id
			String replaceUrl = newsUrl.replace("id", id);
			String s = HttpClientUtil.sendGet(replaceUrl, null, null);
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
