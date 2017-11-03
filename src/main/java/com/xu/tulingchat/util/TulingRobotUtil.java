package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tuling")
/**
 * tuling 机器人接口类
 */
public class TulingRobotUtil {
	@Value("${tuling.url}")
	private String url;
	@Value("${tuling.key}")
	private String key;

	public static final String TEXT_CODE = "100000";//文本 标识码
	public static final String LINK_CODE = "200000";//链接
	public static final String NEWS_CODE = "302000";//新闻
	public static final String CARTE_CODE = "308000";//菜谱

	public static final String RESULT = "text";//结果

	/**
	 *发送post请求
	 * @param info
	 * @param userid
	 * @return
	 */
	public String sendRequest(String info,String userid){
		System.out.println("info:"+info+"，userid:"+userid);
		Map<String,String> map = new HashMap<>();
		map.put("key",key);
		map.put("info",info);
		map.put("userid",userid);
		return HttpRequestUtil.postRequest(url, map);
	}

	/**
	 * 对返回值进行处理
	 * @param str
	 * @return text
	 */
	public String processTypeResult(String str){
		System.out.println("textTypeResult:"+str);
		JSONObject jsonObject = JSONObject.parseObject(str);
		String code = jsonObject.getString("code");
		if(TEXT_CODE.equals(code)){
			return jsonObject.getString("text");
		}else if(LINK_CODE.equals(code)){
			return jsonObject.getString("text")+"\n"+jsonObject.getString("url");
		}else{
			return "对不起，没听清楚，再说一遍吧。";
		}
	}

}
