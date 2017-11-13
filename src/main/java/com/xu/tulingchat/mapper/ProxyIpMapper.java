package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.ProxyIp;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

@Component
public interface ProxyIpMapper {
	@Insert("INSERT INTO proxyips(ip,port) VALUES (#{ip},#{port})")
	void insert(ProxyIp proxyIp);
}
