package com.xu.tulingchat.bean.message.send;

/**
 * 音乐消息
 */
public class MusicMessage extends BaseMessage {
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		this.Music = music;
	}
}
