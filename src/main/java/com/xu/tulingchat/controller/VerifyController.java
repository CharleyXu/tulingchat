package com.xu.tulingchat.controller;

import com.alibaba.fastjson.JSON;
import com.xu.tulingchat.service.EventDispatcher;
import com.xu.tulingchat.service.MsgDispatcher;
import com.xu.tulingchat.util.MessageUtil;
import com.xu.tulingchat.util.SignUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
/**
 * 认证校验，服务器接入公众号
 */
public class VerifyController {

    @RequestMapping(value ="/verify",method = RequestMethod.GET)
    public String index(@RequestParam String signature,@RequestParam String timestamp,@RequestParam String nonce,@RequestParam String echostr){
        if(SignUtil.checkSignature(signature,timestamp,nonce)){
            return echostr;
        }else{
            return "Hello SpringBoot";
        }
    }

    // post 方法用于接收微信服务端消息
    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public String getInfo(HttpServletRequest request, HttpServletResponse response){
        System.out.println("这是 post 方法！");
        try{
            Map<String, String> map= MessageUtil.parseXml(request);
            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
                EventDispatcher.processEvent(map);
            }else{
                MsgDispatcher.progressMsg(map);
            }
            System.out.println("======\n"+JSON.toJSONString(map));
            return "";
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
