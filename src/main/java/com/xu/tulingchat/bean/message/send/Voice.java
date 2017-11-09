package com.xu.tulingchat.bean.message.send;

/**
 * 语音消息回复体
 */
public class Voice {

  //语音消息 通过素材管理中的接口上传多媒体文件，得到的id。
  private String MediaId;

  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
}
