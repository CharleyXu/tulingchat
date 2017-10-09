package com.xu.tulingchat.controller;

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

}
