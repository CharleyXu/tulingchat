package com.xu.tulingchat.controller;

import com.xu.tulingchat.bean.XmlTestBean;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlTestController {
	@RequestMapping(value = "/xmltest01", produces = {"application/xml"})
	public XmlTestBean test01() {
		return new XmlTestBean("1", "ab", 10);
	}

	@RequestMapping(value = "/xmltest02", produces = {
			"application/xml"})
	public XmlTestBean test02(@RequestBody XmlTestBean bean) {
		return bean;
	}
}
