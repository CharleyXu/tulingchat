package com.xu.tulingchat.bean.message.send;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 发送消息基本类
 */
@XmlRootElement
public class BaseMessage {

	//接收方帐号（收到的OpenID）
	private String ToUserName;
	//开发者微信号
	private String FromUserName;
	//消息创建时间 （整型）
	private long CreateTime;
	//消息类型（text/image/voice/video/music/news）
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	@XmlElement
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	@XmlElement
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	@XmlElement
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	@XmlElement
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
