package com.xu.tulingchat.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回类型为xml 测试bean类
 */
@XmlRootElement
public class XmlTestBean {
	public XmlTestBean() {
	}

	public XmlTestBean(String id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	private String id;

	private String name;

	private int age;

	public String getId() {
		return id;
	}
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
