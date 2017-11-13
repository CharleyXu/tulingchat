package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

  //@InsertProvider(type = UserMapperProvider.class, method = "insert")

	@Select("select * from users")
	@Results({@Result(property = "id", column = "userid"),
			@Result(property = "name", column = "username")})
	List<User> findAll();

	@Select("SELECT * from users where username=#{username}")
	User findone(@Param("username") String username);

	@Insert("INSERT INTO users(userid,username) VALUES(#{id},#{name})")
	void insert(User user);

	@Update("UPDATE users SET username=#{username} where userid=#{id}")
	void update(User user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);


}
