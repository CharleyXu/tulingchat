package com.xu.tulingchat.bean.message.send;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TextMessage extends BaseMessage {

	//文本内容（换行：在content中能够换行，微信客户端支持换行显示）
	private String Content;

	public String getContent() {
		return Content;
	}

	@XmlElement
	public void setContent(String content) {
		Content = content;
	}
}
