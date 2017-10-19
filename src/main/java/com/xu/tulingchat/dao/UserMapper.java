package com.xu.tulingchat.dao;

import com.xu.tulingchat.bean.UserBean;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

	@Select("select * from users")
	@Results({@Result(property = "id", column = "id"),
			@Result(property = "name", column = "name")})
	List<UserBean> findAll();

	@Select("SELECT * from users where username=#{username}")
	UserBean findone(@Param("username") String username);

	@Insert("INSERT INTO users(id,name) VALUES(#{id},#{name})")
	void insert(UserBean userBean);

	@Update("UPDATE users SET username=#{username} where id=#{id}")
	void update(UserBean userBean);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);


}
