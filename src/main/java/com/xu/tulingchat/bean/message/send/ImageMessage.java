package com.xu.tulingchat.bean.message.send;

/**
 * 图片消息
 */
public class ImageMessage extends BaseMessage {

  private Image Image;

  public Image getImage() {
    return Image;
  }

  public void setImage(Image image) {
    this.Image = image;
  }
}
