package com.xu.tulingchat.entity;

/**
 * 代理Ip实体类,反爬虫
 */
public class ProxyIp {
	private String ip;
	private String port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
