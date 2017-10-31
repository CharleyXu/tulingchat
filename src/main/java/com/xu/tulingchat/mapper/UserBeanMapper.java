package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.UserBean;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface UserBeanMapper {

  //@InsertProvider(type = UserMapperProvider.class, method = "insert")

	@Select("select * from users")
	@Results({@Result(property = "id", column = "userid"),
			@Result(property = "name", column = "username")})
	List<UserBean> findAll();

	@Select("SELECT * from users where username=#{username}")
	UserBean findone(@Param("username") String username);

	@Insert("INSERT INTO users(userid,username) VALUES(#{id},#{name})")
	void insert(UserBean userBean);

	@Update("UPDATE users SET username=#{username} where userid=#{id}")
	void update(UserBean userBean);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);


}
