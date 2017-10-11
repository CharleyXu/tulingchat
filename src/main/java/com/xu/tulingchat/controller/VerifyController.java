package com.xu.tulingchat.controller;

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
            System.out.println("============================="+map.get("Content"));
            return map.get("Content");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
