package com.xu.tulingchat.controller;

import com.alibaba.fastjson.JSON;
import com.xu.tulingchat.util.PropertiesLoaderUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ListenerController {
    @RequestMapping("/listener")
    public String testListener(){
        Map<String, String> allProperty = PropertiesLoaderUtil.getAllProperty();
        return JSON.toJSONString(allProperty);
    }
}
