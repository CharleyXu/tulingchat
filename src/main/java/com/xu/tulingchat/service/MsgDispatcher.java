package com.xu.tulingchat.service;

import com.xu.tulingchat.bean.message.send.Article;
import com.xu.tulingchat.bean.message.send.Music;
import com.xu.tulingchat.bean.message.send.MusicMessage;
import com.xu.tulingchat.bean.message.send.NewsMessage;
import com.xu.tulingchat.bean.message.send.TextMessage;
import com.xu.tulingchat.util.DailyZhihuUtil;
import com.xu.tulingchat.util.MailUtil;
import com.xu.tulingchat.util.MessageUtil;
import com.xu.tulingchat.util.MusicUtil;
import com.xu.tulingchat.util.TulingRobotUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息业务处理分发器
 */
@Service
public class MsgDispatcher {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TulingRobotUtil tulingRobotUtil;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private DailyZhihuUtil dailyZhihuUtil;
	@Autowired
	private MusicUtil musicUtil;
	@Value("${netease.cloud.music.base}")
	private String NeteaseUrl;

	public String progressMsg(Map<String, String> map) {
		String toUserName = map.get("ToUserName");//开发者微信号 公众号
		String fromUserName = map.get("FromUserName");//发送方帐号（一个 OpenID）粉丝号
		String msgType = map.get("MsgType");
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) { // 文本消息
			System.out.println("==============这是文本消息！");
			String content = map.get("Content");

			if ("知乎日报".equals(content) || "新闻".equals(content)) {
				NewsMessage newsMessage = new NewsMessage();
				List<Article> articles = dailyZhihuUtil.sendRequest(null);
				newsMessage.setToUserName(fromUserName);// 粉丝号
				newsMessage.setFromUserName(toUserName);//公众号
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setArticleCount(articles.size());
				newsMessage.setArticles(articles);
				return MessageUtil.newsMessageToXml(newsMessage);
			}

			if ("音乐".equals(content)) {
				MusicMessage musicMessage = new MusicMessage();
				musicMessage.setToUserName(fromUserName);// 粉丝号
				musicMessage.setFromUserName(toUserName);//公众号
				musicMessage.setCreateTime(new Date().getTime());
				musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
				String artist = "周杰伦";
//				String mediaId = musicUtil.uploadThumb();//thumbMediaId

				String thumbMediaId = "WHCiZd854wV-4k2KmjZbyINGDsvhLsoOa_GM-UOrrOXwwYC4WI_fk_sSK-eIIdfd";

				String[] songs = musicUtil.getSongs(artist);
				System.out.println("mediaId:" + thumbMediaId + "\n songs:" + Arrays.toString(songs));
				Music music = new Music();
				if (thumbMediaId != null && songs != null) {
					String songName = songs[0];
					String songUrl = songs[1];
					music.setTitle(songName);
					music.setDescription(artist);
					music.setThumbMediaId(thumbMediaId);
					String outchainUrl = "http://www.music.163.com/outchain/player?type=2&id=31134194";
					//	https://music.163.com/song?id=5250116
					//	http://music.163.com/#/song?id=5250116
//					music.setMusicURL(NeteaseUrl+"/#"+songUrl);
					music.setMusicUrl(outchainUrl);
//					music.setHQMusicUrl(NeteaseUrl+songUrl);
				}
				musicMessage.setMusic(music);
				return MessageUtil.musicMessageToXml(musicMessage);
			}

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);// 粉丝号
			textMessage.setFromUserName(toUserName);//公众号
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			if (content.length() > 3 && content.substring(0, 3).startsWith("邮件")) {
				handleEmail(content, textMessage);
			} else {
				//向tuling接口发送请求   自动聊天
				String request = tulingRobotUtil.sendRequest(content, fromUserName);
				String result = tulingRobotUtil.processTypeResult(request);
				textMessage.setContent(result);
			}
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

	/**
	 * 处理邮件
	 */
	public void handleEmail(String content, TextMessage textMessage) {
		String[] strings = content.split(" ");
		if (strings.length < 3) {
			textMessage.setContent("发送邮件格式错误");
			return;
		}
		String receiver = strings[1];
		// 验证邮箱的正则表达式
		String pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		if (!receiver.matches(pattern)) {
			textMessage.setContent("收件人邮箱格式错误");
		} else {
			String emailContent = strings[2];
			String subject = "你好，这是一封重要的邮件";
			mailUtil.sendSimpleMail(receiver, subject, emailContent);
			textMessage.setContent("邮件发送成功");
		}
	}
}
