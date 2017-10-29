package com.xu.tulingchat.user;

import com.xu.tulingchat.TulingchatApplication;
import com.xu.tulingchat.entity.UserBean;
import com.xu.tulingchat.mapper.UserBeanMapper;
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
	private UserBeanMapper userBeanMapper;

	@Test
	public void testInsert() {
		userBeanMapper.insert(new UserBean(20, "rony"));
		//	Assert.assertEquals(2, userBeanMapper.findAll().size());
	}


}
