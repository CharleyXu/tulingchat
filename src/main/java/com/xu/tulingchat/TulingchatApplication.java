package com.xu.tulingchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class TulingchatApplication {
	public static void main(String[] args) {
		SpringApplication.run(TulingchatApplication.class, args);
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
