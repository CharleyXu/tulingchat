package com.xu.tulingchat.user;

import com.xu.tulingchat.TulingchatApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TulingchatApplication.class)
@WebAppConfiguration
public class RedisTest {
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void redisTest() {
//		redisTemplate.opsForValue().set("k01", "xzc");
//		Assert.assertEquals("xzc", redisTemplate.opsForValue().get("k01"));
	}
}
