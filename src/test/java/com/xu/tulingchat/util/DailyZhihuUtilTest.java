package com.xu.tulingchat.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyZhihuUtilTest {
	@Autowired
	private DailyZhihuUtil dailyZhihuUtil;

	@Test
	public void test() {
		dailyZhihuUtil.sendRequest(null);
	}
}
