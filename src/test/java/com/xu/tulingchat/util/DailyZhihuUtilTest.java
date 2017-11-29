package com.xu.tulingchat.util;

import com.xu.tulingchat.bean.message.send.Article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyZhihuUtilTest {
	@Autowired
	private DailyZhihuUtil dailyZhihuUtil;

	@Test
	public void test() {
		List<Article> articleList = dailyZhihuUtil.getStories("20171130");
		System.out.println(articleList);
	}
}
