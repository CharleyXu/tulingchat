package com.xu.tulingchat.bean.message.send;

import java.util.List;

/**
 * 回复图文消息
 */
public class NewsMessage extends BaseMessage {

	//图文消息 图文消息个数，限制为8条以内
	private int ArticleCount;
	//多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}