package com.xu.tulingchat.controller;

import com.xu.tulingchat.entity.XmlTest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlTestController {
	@RequestMapping(value = "/xmltest01", produces = {"application/xml"})
	public XmlTest test01() {
		return new XmlTest("1", "ab", 10);
	}

	@RequestMapping(value = "/xmltest02", produces = {
			"application/xml"})
	public XmlTest test02(@RequestBody XmlTest bean) {
		return bean;
	}
}
