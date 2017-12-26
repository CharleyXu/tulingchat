package com.xu.tulingchat.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.xu.tulingchat.bean.message.send.Article;
import com.xu.tulingchat.bean.message.send.Music;
import com.xu.tulingchat.bean.message.send.MusicMessage;
import com.xu.tulingchat.bean.message.send.NewsMessage;
import com.xu.tulingchat.bean.message.send.TextMessage;
import com.xu.tulingchat.util.DailyZhihuUtil;
import com.xu.tulingchat.util.DateUtil;
import com.xu.tulingchat.util.MailUtil;
import com.xu.tulingchat.util.MessageUtil;
import com.xu.tulingchat.util.TulingRobotUtil;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 消息业务处理分发器
 */
@Service
public class MsgDispatcher {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${netease.cloud.music.song}")
	private String song;
	@Value("${wechat.permanent.material.mediaid}")
	private String mediaid;
	@Autowired
	private TulingRobotUtil tulingRobotUtil;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private DailyZhihuUtil dailyZhihuUtil;
	@Autowired
	private NetEaseMusicService netEaseMusicService;

	public String progressMsg(Map<String, String> map) {
		String reResult = "";
		String toUserName = map.get("ToUserName");//开发者微信号 公众号
		String fromUserName = map.get("FromUserName");//发送方帐号（一个 OpenID）粉丝号
		String msgType = map.get("MsgType");
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) { // 文本消息
			String content = map.get("Content").trim();
//			System.out.println("==============\n" + content);

			if ("咨询".startsWith(content)) {
				NewsMessage newsMessage = null;
				if (content.equals("咨询")) {//咨询
					List<Article> articles = dailyZhihuUtil.getStories(null);
					newsMessage = handleNews(fromUserName, toUserName, articles);
				} else if (content.split(" ").length == 2) {//咨询 20171129
					String date = content.substring(3);
					if (DateUtil.isValidDate(date, DateUtil.yyyyMMdd)) {
						List<Article> articles = dailyZhihuUtil.getStories(date);
						newsMessage = handleNews(fromUserName, toUserName, articles);
					} else {
						List<Article> articles = dailyZhihuUtil.getStories(null);
						newsMessage = handleNews(fromUserName, toUserName, articles);
					}
				}
				if (Strings.isNullOrEmpty(MessageUtil.newsMessageToXml(newsMessage))) {
					reResult = MessageUtil.newsMessageToXml(newsMessage);
				}
				return reResult;
			}

			if (content.startsWith("音乐")) {
				MusicMessage musicMessage = null;
				if (content.equals("音乐")) {
					String artist = "李玖哲";
					com.xu.tulingchat.entity.Music music = netEaseMusicService.getMusic(artist);
					if (null != null) {
						int id = music.getId();
						String name = music.getName();
						String songId = song.replace("SONG_ID", id + "");
						musicMessage = handleMusic(fromUserName, toUserName, name, artist, songId);
						return MessageUtil.musicMessageToXml(musicMessage);
					}
				} else if (content.indexOf(" ") == 2) {
					String[] split = content.split(" ");
					if (split.length == 2) {
						String artistOrSong = split[1];
						com.xu.tulingchat.entity.Music music = netEaseMusicService.getMusic(artistOrSong);//artist
						if (null != music) {
							String name = music.getName();
							int id = music.getId();
							String songId = song.replace("SONG_ID", id + "");
							musicMessage = handleMusic(fromUserName, toUserName, name, artistOrSong, songId);
							return MessageUtil.musicMessageToXml(musicMessage);
						} else {
							String[] strings = netEaseMusicService.getMusicIdBySongName(artistOrSong);//songName
							if (strings != null) {
								String musicId = strings[0];
								String songId = song.replace("SONG_ID", musicId);
								String artist = strings[1];
								musicMessage = handleMusic(fromUserName, toUserName, artistOrSong, artist, songId);
								return MessageUtil.musicMessageToXml(musicMessage);
							}
						}
					} else if (split.length == 3) {
						String artist = content.split(" ")[1];
						String name = content.split(" ")[2];
						com.xu.tulingchat.entity.Music music = netEaseMusicService.getMusicByCondition(artist, name);
						if (null != music) {
							int id = music.getId();
							String songId = song.replace("SONG_ID", id + "");
							musicMessage = handleMusic(fromUserName, toUserName, name, artist, songId);
							return MessageUtil.musicMessageToXml(musicMessage);
						}
					}
				}
			}

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);// 粉丝号
			textMessage.setFromUserName(toUserName);//公众号
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			if (content.length() > 3 && content.substring(0, 3).startsWith("邮件")) {
				handleEmail(content, textMessage);
				return MessageUtil.textMessageToXml(textMessage);
			} else {
				//向tuling接口发送请求   自动聊天
				String request = tulingRobotUtil.sendRequest(content, fromUserName);
				String result = tulingRobotUtil.processTypeResult(request);
				if (checkjson(result)) {
					NewsMessage newsMessage = handleNews(fromUserName, toUserName, JSON.parseArray(result, Article.class));
					return MessageUtil.newsMessageToXml(newsMessage);
				} else {
					textMessage.setContent(result);
					return MessageUtil.textMessageToXml(textMessage);
				}
			}
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
	 * 回复音乐消息
	 */
	public MusicMessage handleMusic(String fromUserName, String toUserName, String songName, String artist, String songUrl) {
		Music music = new Music();
		music.setTitle(songName);
		music.setDescription(artist);
		music.setMusicUrl(songUrl);
		music.setHQMusicUrl(songUrl);
		music.setThumbMediaId(mediaid);
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);// 粉丝号
		musicMessage.setFromUserName(toUserName);//公众号
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		musicMessage.setMusic(music);
		return musicMessage;
	}

	/**
	 * 新闻消息回复
	 */
	public NewsMessage handleNews(String fromUserName, String toUserName, List<Article> articles) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);// 粉丝号
		newsMessage.setFromUserName(toUserName);//公众号
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articles.size());
		newsMessage.setArticles(articles);
		return newsMessage;
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


	public boolean checkjson(String jsonstr) {
		try {
			JSON.parseArray(jsonstr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
