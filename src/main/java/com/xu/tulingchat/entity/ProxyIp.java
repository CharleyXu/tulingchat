package com.xu.tulingchat.entity;

/**
 * 代理Ip实体类,反爬虫
 */
public class ProxyIp {
	private String ip;//IP
	private int port;//端口
	private String created;//创建时间
	private String updated;//更新时间

	public ProxyIp() {
	}

	public ProxyIp(String ip, int port, String created, String updated) {
		this.ip = ip;
		this.port = port;
		this.created = created;
		this.updated = updated;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
