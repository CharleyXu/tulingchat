package com.xu.tulingchat.bean.message.send;

/**
 * 语音消息
 */
public class VoiceMessage extends BaseMessage {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		this.Voice = voice;
	}
}
