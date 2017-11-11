package com.xu.tulingchat.bean.message.send;

/**
 * 视频消息
 */
public class VideoMessage extends BaseMessage {
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		this.Video = video;
	}
}
