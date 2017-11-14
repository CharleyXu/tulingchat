package com.xu.tulingchat.util;

import com.xu.tulingchat.entity.ProxyIp;
import com.xu.tulingchat.mapper.ProxyIpMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyIpTest {
	@Autowired
	private ProxyIpMapper proxyIpMapper;

	@Test
	public void findOne() {
		ProxyIp newestOne = proxyIpMapper.findNewestOne();
		System.out.println("---" + newestOne.getIp());
	}
}
