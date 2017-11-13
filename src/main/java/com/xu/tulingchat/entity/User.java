package com.xu.tulingchat.entity;

import com.alibaba.fastjson.JSON;

/**
 * springboot 仅测试
 */
public class User {
	private Integer id;
	private String name;

	public User() {
	}

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
    return JSON.toJSONString(this);
  }
}
