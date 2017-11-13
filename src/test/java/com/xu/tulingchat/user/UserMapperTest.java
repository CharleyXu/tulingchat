package com.xu.tulingchat.user;

import com.xu.tulingchat.TulingchatApplication;
import com.xu.tulingchat.entity.User;
import com.xu.tulingchat.mapper.UserMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TulingchatApplication.class)
@WebAppConfiguration
public class UserMapperTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testInsert() {
		userMapper.insert(new User(20, "rony"));
		//	Assert.assertEquals(2, userBeanMapper.findAll().size());
	}


}
