package com.xu.tulingchat.entity;

/**
 * 歌曲实体类
 */
public class Song {
	private String name;//名称
	private String singer;//歌手
	private String url;//链接
	private String source;//歌曲来源

	public Song() {
	}

	public Song(String name, String singer, String url, String source) {
		this.name = name;
		this.singer = singer;
		this.url = url;
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
