package com.xu.tulingchat.service;

import com.xu.tulingchat.bean.message.send.TextMessage;
import com.xu.tulingchat.util.MessageUtil;
import com.xu.tulingchat.util.TulingRobotUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 消息业务处理分发器
 */
@Service
public class MsgDispatcher {
	@Autowired
	TulingRobotUtil tulingRobotUtil;

	public String progressMsg(Map<String, String> map) {
		String toUserName = map.get("ToUserName");//开发者微信号 公众号
		String fromUserName = map.get("FromUserName");//发送方帐号（一个 OpenID）粉丝号
		String msgType = map.get("MsgType");
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) { // 文本消息
			System.out.println("==============这是文本消息！");
			String content = map.get("Content");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);// 粉丝号
			textMessage.setFromUserName(toUserName);//公众号
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			//向tuling接口发送请求
			String request = tulingRobotUtil.sendRequest(content,fromUserName);
			String result = tulingRobotUtil.processTypeResult(request);
			textMessage.setContent(result);

			return MessageUtil.textMessageToXml(textMessage);
		}


		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
			System.out.println("==============这是图片消息！");
		}

		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
			System.out.println("==============这是链接消息！");
		}

		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
			System.out.println("==============这是位置消息！");
		}

		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
			System.out.println("==============这是视频消息！");
		}

		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
			System.out.println("==============这是语音消息！");
		}

		return null;
	}
}
