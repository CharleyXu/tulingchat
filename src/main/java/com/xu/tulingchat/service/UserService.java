package com.xu.tulingchat.service;

import com.xu.tulingchat.bean.UserBean;
import com.xu.tulingchat.mapper.UserBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserBeanMapper userBeanMapper;

  public void insert(UserBean userBean) {
    userBeanMapper.insert(userBean);
  }
}
