package com.xu.tulingchat.service;

import com.xu.tulingchat.entity.User;
import com.xu.tulingchat.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;

	public void insert(User user) {
		userMapper.insert(user);
	}
}
