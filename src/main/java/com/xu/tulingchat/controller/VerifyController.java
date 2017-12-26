package com.xu.tulingchat.controller;

import com.xu.tulingchat.service.EventDispatcher;
import com.xu.tulingchat.service.MsgDispatcher;
import com.xu.tulingchat.util.MessageUtil;
import com.xu.tulingchat.util.SignUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xu 认证校验，服务器接入公众号
 */
@RestController
public class VerifyController {

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String index(@RequestParam String signature, @RequestParam String timestamp,
			@RequestParam String nonce, @RequestParam String echostr) {
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		} else {
			return "Hello SpringBoot";
		}
	}

	@Autowired
	MsgDispatcher msgDispatcher;
	@Autowired
	EventDispatcher eventDispatcher;

	/**
	 * post 方法用于接收  {"application/xml;charset=utf-8"}
	 *
	 * @param request 微信服务端消息
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
	public String getInfo(HttpServletRequest request) {
		try {
			Map<String, String> map = MessageUtil.parseXml(request);
//		System.out.println("--请求体--" + JSON.toJSONString(map));
			String msgType = map.get("MsgType");
			if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
				String result = eventDispatcher.processEvent(map);
//				System.out.println("--返回体--" + result);
				return result;
			} else {
				String result = msgDispatcher.progressMsg(map);
//				System.out.println("--返回体--" + result);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
