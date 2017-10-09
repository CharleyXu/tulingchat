package com.xu.tulingchat.bean;

/**
 * springboot 仅测试
 */
public class UserBean {
    private String id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public String getId() {

        return id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
