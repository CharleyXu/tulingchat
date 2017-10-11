package com.xu.tulingchat.listener;

import com.xu.tulingchat.util.PropertiesLoaderUtil;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * 配置文件监听器，用来加载自定义配置文件
 */
public class PropertiesListener implements ApplicationListener<ApplicationStartingEvent> {
    private String propertyFileName;
    public PropertiesListener(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        PropertiesLoaderUtil.loadAllProperties(propertyFileName);
    }
}
