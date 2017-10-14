package com.xu.tulingchat.bean.message.send;

public class NewsMessage extends BaseMessage {

  //图文消息 图文消息个数，限制为8条以内
  private String ArticleCount;
  //多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
  private String Articles;
  //图文消息标题
  private String Title;
  //图文消息描述
  private String Description;
  //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
  private String PicUrl;
  //点击图文消息跳转链接
  private String Url;

  public String getArticleCount() {
    return ArticleCount;
  }

  public void setArticleCount(String articleCount) {
    ArticleCount = articleCount;
  }

  public String getArticles() {
    return Articles;
  }

  public void setArticles(String articles) {
    Articles = articles;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public String getPicUrl() {
    return PicUrl;
  }

  public void setPicUrl(String picUrl) {
    PicUrl = picUrl;
  }

  public String getUrl() {
    return Url;
  }

  public void setUrl(String url) {
    Url = url;
  }
}