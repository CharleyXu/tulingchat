package com.xu.tulingchat.mapper;

import com.xu.tulingchat.entity.ProxyIp;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface ProxyIpMapper {
	@Insert("INSERT INTO proxyips(ip,port,created,updated) VALUES (#{ip},#{port},#{created},#{updated})")
	void insert(ProxyIp proxyIp);

	@Select("SELECT ip,port FROM proxyips order by created DESC limit 1")
	ProxyIp findNewestOne();

	@Select("select 1 from proxyips where ip = #{ip} limit 1")
	Integer findOne(String ip);

	@Delete("delete from proxyips where ip = #{ip}")
	void deleteByIp(String ip);

	/**
	 * 批量删除 LIKE CONCAT(CONCAT('%', #{created}), '%')
	 */
	//@Delete("delete from proxyips where created LIKE CONCAT('%', #{created}, '%')")
	@Delete("DELETE from proxyips where created < DATE_SUB(NOW(),INTERVAL #{created} MINUTE)")
	void batchDelete(String created);

}
