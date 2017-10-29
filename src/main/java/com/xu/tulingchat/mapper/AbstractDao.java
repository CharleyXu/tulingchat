package com.xu.tulingchat.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 公用DAO
 */
public class AbstractDao extends SqlSessionDaoSupport {

  /**
   * Autowired 必须要有 Mybatis3依赖的jar位 mybatis-spring-1.2.0.jar,这个版本及以上的版本中对SqlSessionDaoSupport类
   * 中的'sqlSessionFactory'或'sqlSessionTemplate'注入方式进行了调整
   */
  @Autowired
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {

    super.setSqlSessionFactory(sqlSessionFactory);
  }

}
