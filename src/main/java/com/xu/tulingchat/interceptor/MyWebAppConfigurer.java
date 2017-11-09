package com.xu.tulingchat.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

  /**
   * 添加拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // addInterceptor方法 可以加入多个拦截器组成一个拦截器链
    // addPathPatterns 用于添加拦截规则
    // excludePathPatterns 用户排除拦截规则
	  registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
	  super.addInterceptors(registry);
  }

	/**
	 * 把我们的拦截器注入为bean
	 */
	@Bean
	public MyInterceptor getMyInterceptor() {
		return new MyInterceptor();
	}

}
