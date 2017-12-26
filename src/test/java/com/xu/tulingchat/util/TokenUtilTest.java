package com.xu.tulingchat.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * token测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TokenUtilTest {

	@Autowired
	private TokenUtil tokenUtil;
	@Value("${wechat.material.list}")
	private String materialUrl;
	@Value("${wechat.material.temporary}")
	private String temporaryUrl;
	@Value("${wechat.material.permanent}")
	private String permanentUrl;

	@Test
	public void test() {
		String access_token = null;
//	access_token = "Xk4tXBsHqNLDsWSPgLqbv6bVS61l3IAkdQxpPl3g9mL8Q39V_07uXisPgn4zvZLNBkDnnOlvjRqoS31rFMIpD9H42iljjd071IyI4PB_bDAP3JnoIiZC7kXEFw8ZP9IjGSTdAFAVHI";
		access_token = tokenUtil.getToken();
		System.out.println("--当前的access_token是:" + access_token);
		String replaceUrl = permanentUrl.replace("ACCESS_TOKEN", access_token).replace("TYPE", "thumb");
		try {
			Map<String, String> fileBody = new HashMap<>();
			fileBody.put("media", "D:/temp.jpg");
			Map<String, String> stringBody = new HashMap<>();
			stringBody.put("access_token", access_token);
			stringBody.put("image", "thumb");
			String result = HttpClientUtil.doUpload(replaceUrl, fileBody, stringBody);
			System.out.println("result:" + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getMaterials() {
		String access_token = tokenUtil.getToken();
		System.out.println("--当前的access_token是:" + access_token);
		String replace = materialUrl.replace("ACCESS_TOKEN", access_token);
		HttpClientUtil.sendPost(replace, null, null);
	}

}
