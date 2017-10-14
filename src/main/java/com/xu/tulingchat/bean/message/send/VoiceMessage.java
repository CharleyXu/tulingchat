package com.xu.tulingchat.bean.message.send;

public class VoiceMessage extends BaseMessage {

  //语音消息 通过素材管理中的接口上传多媒体文件，得到的id。
  private String MediaId;

  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }
}
