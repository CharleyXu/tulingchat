package com.xu.tulingchat.bean.message.receive;

/**
 * 图片消息
 */
public class ImageMessage extends BaseMessage {
    //图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
