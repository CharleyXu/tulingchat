package com.xu.tulingchat.bean.message;

/**
 * 语音消息
 */
public class VoiceMessage extends BaseMessage {

  // 媒体 ID
  private String MediaId;
  // 语音格式
  private String Format;
  //语音识别结果，UTF8编码
  private String Recognition;

  public String getMediaId() {
    return MediaId;
  }

  public void setMediaId(String mediaId) {
    MediaId = mediaId;
  }

  public String getFormat() {
    return Format;
  }

  public void setFormat(String format) {
    Format = format;
  }

  public String getRecognition() {
    return Recognition;
  }

  public void setRecognition(String recognition) {
    Recognition = recognition;
  }
}
