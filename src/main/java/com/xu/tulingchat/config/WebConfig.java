package com.xu.tulingchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 开启CORS支持
 */
@Configuration
public class WebConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// 配置CorsInterceptor的CORS参数
				registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedHeaders("*").allowedMethods("*").maxAge(3600);
			}
		};
	}
}

