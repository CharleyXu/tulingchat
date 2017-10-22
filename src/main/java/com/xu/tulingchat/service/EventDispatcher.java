package com.xu.tulingchat.service;

import com.xu.tulingchat.bean.message.send.TextMessage;
import com.xu.tulingchat.util.MessageUtil;
import java.util.Date;
import java.util.Map;

/**
 * 事件消息业务分发器
 */
public class EventDispatcher {
	public static String processEvent(Map<String, String> map) {
		String toUserName = map.get("ToUserName");//开发者微信号 公众号
		String fromUserName = map.get("FromUserName");//发送方帐号（一个 OpenID）粉丝号
		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
			System.out.println("==============这是关注事件！");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);// 粉丝号
			textMessage.setFromUserName(toUserName);//公众号
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			//向tuling接口发送请求
      textMessage.setContent("欢迎关注tulingchat公众号！");
      return MessageUtil.textMessageToXml(textMessage);
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
			System.out.println("==============这是取消关注事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
			System.out.println("==============这是扫描二维码事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
			System.out.println("==============这是位置上报事件！");
		}

		if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
			System.out.println("==============这是自定义菜单点击事件！");
		}

		return null;
	}
}


