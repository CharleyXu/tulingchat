package com.xu.tulingchat;

import com.google.common.base.Strings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

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


@SpringBootApplication
public class TulingchatApplication {
    @RequestMapping("/")
    public String index(){
        return "Hello SpringBoot";
    }

    @RequestMapping("/test01")
    //@RequestParam
    public String test01(@RequestParam String id){
        if(Strings.isNullOrEmpty(id)){
            id="12345";
        }
        return id;
    }

	public static void main(String[] args) {
		SpringApplication.run(TulingchatApplication.class, args);
	}
}
