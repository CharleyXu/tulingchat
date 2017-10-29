package com.xu.tulingchat.controller;

import com.google.common.base.Strings;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xu.tulingchat.entity.UserBean;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
	static Map<Integer, UserBean> users = Collections.synchronizedMap(new HashMap<Integer, UserBean>());


    @RequestMapping("/test01")
    //@RequestParam
    public String test01(@RequestParam(value = "",required = false) String id){
        if(Strings.isNullOrEmpty(id)){
            id="12345";
        }
        return id;
    }

    @RequestMapping(value = "/test02",method = RequestMethod.GET)
    public String test02(@ModelAttribute UserBean user){
		users.put(user.getId(), user);
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(users));
        return jsonObject.toString();
    }
}
