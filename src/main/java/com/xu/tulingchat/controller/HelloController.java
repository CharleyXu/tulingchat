package com.xu.tulingchat.controller;

import com.google.common.base.Strings;
import com.xu.tulingchat.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello Controller !";
    }

    @Value("${com.xu.tulingchat.testId}")
    private  String id;

    @Value("${com.xu.tulingchat.testName}")
    private  String name;

    @RequestMapping("/demo01")
    public String demo01(){
        return id+","+name;
    }

    @Autowired
    TokenUtil tokenUtil ;
    @RequestMapping("/demo02")
    public String demo02(){
        String token = tokenUtil.getToken();
        if(!Strings.isNullOrEmpty(token)){
            return token;
        }else{
            return "123";
        }
    }

}
