package com.xu.tulingchat.tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * HttpClient工具类
 */
public class HttpClientUtil {

    //public static LogUtil log = new LogUtil(HttpclientUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }


    /**
     * 发送GET请求（HTTP），K-V形式
     *
     * @author Charlie.chen；
     */
    public static String doGet(String url, Map<String, String> params) {

        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;

        // 创建默认的HttpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

        // 定义一个get请求方法
            HttpGet httpget = new HttpGet(apiUrl);

        // 执行get请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            CloseableHttpResponse httpResponse = httpclient.execute(httpget);

        // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
        // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
        // 将响应实体转为字符串
                String response = EntityUtils.toString(entity, "utf-8");
                return response;

            } else {
                // log.error("访问失败"+statusCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接, 和释放资源
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送POST请求（HTTP），K-V形式
     *
     * @author Charlie.chen
     */
    public static String doPost(String url, Map<String, String> params) {

        // 创建默认的HttpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 定义一个get请求方法
            HttpPost httppost = new HttpPost(url);

            // List parameters = new ArrayList();
            // parameters.add(new BasicNameValuePair("username", userName));
            // parameters.add(new BasicNameValuePair("password", password));

            // 定义post请求的参数
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            List list = new ArrayList();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                httppost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            }
            // httppost.setHeader("Content-type","application/json,charset=utf-8");
            // httppost.setHeader("Accept", "application/json");
            // 执行post请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            CloseableHttpResponse httpResponse = httpclient.execute(httppost);
            // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
            // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
            // 将响应实体转为字符串
                String response = EntityUtils.toString(entity, "utf-8");
                return response;
            } else {
                // log.error("访问失败"+statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接, 和释放资源
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 发送 SSL POST请（HTTPS），K-V形式
     *
     * @author Charlie.chen
     */
    public static String doPostSSL(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConn()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            httpPost.setConfig(requestConfig);
            List pairList = new ArrayList(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 创建SSL安全连接
     *
     * @author Charlie.chen
     */
    private static SSLConnectionSocketFactory createSSLConn() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
                public void verify(String host, SSLSocket ssl) throws IOException {
                }
                public void verify(String host, X509Certificate cert) throws SSLException {
                }
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
            // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
            // sslsf = new SSLConnectionSocketFactory(sslContext,
            //       SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    /**
     * 发送post请求,并上传文件
     */
    public String postWithFile(String url, String filePath) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            FileBody bin = null;
            File file = new File(filePath);
            if (file.exists()) {
                bin = new FileBody(file);
            } else {
                System.out.println("Can&#39;t find " + filePath);
            }
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
            //请求实体
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();
            httppost.setEntity(reqEntity);
            //执行post请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
            CloseableHttpResponse httpResponse = httpclient.execute(httppost);
            // 使用响应对象, 获得状态码, 处理内容
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 使用响应对象获取响应实体
                HttpEntity entity = httpResponse.getEntity();
                //将响应实体转为字符串
                String response = EntityUtils.toString(entity, "utf-8");
                return response;
            } else {
                System.out.println("访问失败" + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接, 和释放资源
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

	public static void main(String[] args) {
		String url = "http://p1.music.126.net/cUTk0ewrQtYGP2YpPZoUng==/3265549553028224.jpg?param=130y130";
		String path = "D:/test.jpg";
		try {
			int i = doDownload(url, path);
			System.out.println("code:" + i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过httpClient get 下载文件
	 *
	 * @param url      网络文件全路径
	 * @param savePath 保存文件全路径
	 * @return 状态码 200表示成功
	 */
	public static int doDownload(String url, String savePath) throws ClientProtocolException, IOException {
		// 创建默认的HttpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream in = entity.getContent();
			FileOutputStream out = new FileOutputStream(savePath);
			IOUtils.copy(in, out);
			EntityUtils.consume(entity);
			response.close();
		}
		int code = response.getStatusLine().getStatusCode();
		httpClient.close();
		return code;
	}

	/**
	 * 通过HttpClint post 上传文件
	 *
	 * @param url        上传请求地址
	 * @param fileBody   文件类型input Map
	 * @param stringBody 普通类型input Map
	 * @return 状态码 200表示成功
	 */
	public static int doUpload(String url, Map<String, String> fileBody, Map<String, String> stringBody) throws ClientProtocolException, IOException {
		// 创建默认的HttpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (fileBody != null) {
			Iterator<String> fileIter = fileBody.keySet().iterator();
			while (fileIter.hasNext()) {
				String key = (String) fileIter.next();
				String value = fileBody.get(key);
				builder.addPart(key, new FileBody(new File(value)));
			}
		}
		if (stringBody != null) {
			Iterator<String> stringIter = stringBody.keySet().iterator();
			while (stringIter.hasNext()) {
				String key = (String) stringIter.next();
				String value = stringBody.get(key);
				builder.addPart(key, new StringBody(value, ContentType.TEXT_PLAIN));
			}
		}
		post.setEntity(builder.build());
		CloseableHttpResponse response = httpClient.execute(post);
		int code = response.getStatusLine().getStatusCode();
		httpClient.close();
		return code;
	}

}
