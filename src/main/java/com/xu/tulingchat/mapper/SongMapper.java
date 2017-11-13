package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.Song;

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
public interface SongMapper {
	@Select("select name,singer,url,source from songs")
	@Results({@Result(property = "name", column = "name"),
			@Result(property = "url", column = "url")})
	List<Song> findAll();

	@Select("SELECT url from songs where name=#{name}")
	String findOneByName(@Param("name") String name);

	@Select("SELECT url from songs where singer=#{singer}")
	String findOneBySinger(@Param("singer") String singer);

	@Insert("INSERT INTO songs(name,singer,url,source) VALUES (#{name},#{singer},#{url},#{source})")
	void insert(Song song);

	@Update("UPDATE songs SET url=#{url} where name=#{name}")
	void update(Song song);

	@Delete("DELETE FROM songs WHERE id =#{id}")
	void delete(Long id);
}
