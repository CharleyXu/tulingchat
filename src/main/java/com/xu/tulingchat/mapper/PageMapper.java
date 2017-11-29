package com.xu.tulingchat.mapper;


import com.xu.tulingchat.entity.PageRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @param <T>
 */
@Component
public interface PageMapper<T> {
	//分页查询

	List<T> findAll(@Param("page") PageRequest request);

	//查询数量
	Long countAll();
}
