package com.xu.tulingchat.bean.message.receive;

/**
 * 文本消息
 */
public class TextMessage extends BaseMessage {
    //文本内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
