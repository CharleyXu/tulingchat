package com.xu.tulingchat.entity;

import com.alibaba.fastjson.JSON;

/**
 * MongoDB 测试实体类
 */
public class Comment {
	private String sendMessage;//用户发送的消息
	private String replyMessage;//服务回复的消息
	private String createTime;//创建时间

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
