package com.xu.tulingchat.user;

import com.xu.tulingchat.bean.UserBean;
import com.xu.tulingchat.mapper.UserBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserMapperTest {
	@Autowired
	private UserBeanMapper userBeanMapper;

	@Test
	public void testInsert() {
		userBeanMapper.insert(new UserBean(10, "amy"));
		//	Assert.assertEquals(2, userBeanMapper.findAll().size());
	}


}
