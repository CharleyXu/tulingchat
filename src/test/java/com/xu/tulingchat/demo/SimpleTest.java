package com.xu.tulingchat.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${mail.fromMail.addr}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void test() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo("770287339@qq.com");
		message.setSubject("要事。。。。测试3");
		message.setText("这是一封重要的邮件");

		try {
			mailSender.send(message);
			logger.info("简单邮件已经发送。");
		} catch (Exception e) {
			logger.error("发送简单邮件时发生异常！", e);
		}
	}
}

