package com.xu.tulingchat.controller;

import com.xu.tulingchat.util.SignUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyController {

    @RequestMapping("/verify")
    public String index(@RequestParam String signature,@RequestParam String timestamp,@RequestParam String nonce,@RequestParam String echostr){
        if(SignUtil.checkSignature(signature,timestamp,nonce)){
            return echostr;
        }else{
            return "Hello SpringBoot";
        }
    }

}
