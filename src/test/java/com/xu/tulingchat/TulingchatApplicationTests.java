package com.xu.tulingchat;

import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TulingchatApplicationTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void testHttp(){
        HttpGet httpGet = new HttpGet("http://101.200.44.47/test02?id=456&name=abc");
        System.out.println(httpGet.getRequestLine());
    }

}
