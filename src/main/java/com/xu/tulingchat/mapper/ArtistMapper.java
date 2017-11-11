package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.Artist;

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
public interface ArtistMapper {
	@Select("select * from artists")
	@Results({@Result(property = "name", column = "name"),
			@Result(property = "url", column = "url")})
	List<Artist> findAll();

	@Select("SELECT url from artists where name=#{name}")
	String findone(@Param("name") String name);

	@Insert("INSERT INTO artists(name,url) VALUES(#{name},#{url})")
	void insert(Artist artist);

	@Update("UPDATE artists SET url=#{url} where name=#{name}")
	void update(Artist artist);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);
}
