package com.xu.tulingchat.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaterialUtil {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenUtil tokenUtil;

	/**
	 * 上传图片到微信服务器(本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下)
	 */
	public JSONObject addMaterialEver(String appid, String secret, File file) throws Exception {
		try {
			System.out.println("开始上传图文消息内的图片---------------------");

			//开始获取证书
			String accessToken = tokenUtil.getToken();
			if (Strings.isNullOrEmpty(accessToken)) {
				logger.info("accessToken is null");
				return null;
			}

			//上传图片素材
			String path = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + accessToken;
			String result = connectHttpsByPost(path, file);
			result = result.replaceAll("[\\\\]", "");
			System.out.println("result:" + result);
			JSONObject resultJSON = JSONObject.parseObject(result);
			if (resultJSON != null) {
				if (resultJSON.get("url") != null) {
					System.out.println("上传图文消息内的图片成功");
					return resultJSON;
				} else {
					System.out.println("上传图文消息内的图片失败");
				}
			}
			return null;
		} catch (Exception e) {
			logger.error("程序异常", e);
			throw e;
		} finally {
			System.out.println("结束上传图文消息内的图片---------------------");
		}
	}

	public static String connectHttpsByPost(String path, File file)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		URL urlObj = new URL(path);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		String result = null;
		con.setDoInput(true);

		con.setDoOutput(true);

		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type",
				"multipart/form-data; boundary="
						+ BOUNDARY);

		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
				+ "\";filename=\""

				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charsets.UTF_8));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return result;
	}

	/**
	 * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
	 */
	public JSONObject addMaterialEver(String appid, String secret, File file, String type)
			throws Exception {
		try {
			System.out.println("开始上传" + type + "永久素材---------------------");

			//开始获取证书
			String accessToken = tokenUtil.getToken();
			if (Strings.isNullOrEmpty(accessToken)) {
				logger.info("accessToken is null");
				return null;
			}

			//上传素材
			String path =
					"https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken
							+ "&type=" + type;
			String result = connectHttpsByPost(path, file);
			result = result.replaceAll("[\\\\]", "");
			System.out.println("result:" + result);
			JSONObject resultJSON = JSONObject.parseObject(result);
			if (resultJSON != null) {
				if (resultJSON.get("media_id") != null) {
					System.out.println("上传" + type + "永久素材成功");
					return resultJSON;
				} else {
					System.out.println("上传" + type + "永久素材失败");
				}
			}

			return null;
		} catch (Exception e) {
			logger.error("程序异常", e);
			throw e;
		} finally {
			System.out.println("结束上传" + type + "永久素材---------------------");
		}
	}
}
