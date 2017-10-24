package com.xu.tulingchat;

import com.xu.tulingchat.listener.PropertiesListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.xu.tulingchat.mapper")
@EnableScheduling
/**
 * 注册监听器(`Listeners`) + `PropertiesLoaderUtils`的方式
 */
public class TulingchatApplication {
	public static void main(String[] args) {
//		SpringApplication.run(TulingchatApplication.class, args);

		SpringApplication application = new SpringApplication(TulingchatApplication.class);
		//注册监听器
		application.addListeners(new PropertiesListener("application.properties"));
		application.run(args);
	}
}

//@SpringBootApplication
//public class TulingchatApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(TulingchatApplication.class);
//    }
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(TulingchatApplication.class, args);
//    }
//
//}
