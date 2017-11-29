package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.RelationArtlistMusic;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelationArtlistMusicMapper {

	/**
	 * 单个插入
	 */
	@Insert("INSERT IGNORE INTO relation_artlist_music(artlistId,musicId) value (#{artlistId},#{musicId})")
	void insert(RelationArtlistMusic relationArtlistMusic);


	/**
	 * 批量插入
	 */
	@InsertProvider(type = RelationArtlistMusicSqlProvider.class, method = "batchInsert")
	void batchInsert(@Param("list") List<RelationArtlistMusic> list);

	/**
	 * 查询
	 */
	@Select("select * from relation_artlist_music")
	List<RelationArtlistMusic> findAll();
}
