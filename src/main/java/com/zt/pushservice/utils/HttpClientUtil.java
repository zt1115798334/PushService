package com.zt.pushservice.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangjianchun
 */
public class HttpClientUtil {
    private HttpClientUtil() {

    }

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final HttpClientUtil INSTANCE = new HttpClientUtil();
    /**
     * 最大尝试次数
     */
    private static final int MAX_RETRY = 3;

    public static HttpClientUtil getInstance() {
        return INSTANCE;
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    public String httpPost(String url, Map<String, String> paramMap) {
        return httpPost(url, paramMap, "UTF-8");
    }

    public String httpPost(String url, Map<String, String> paramMap, String charset) {
        // 配置传递的参数
        List<BasicNameValuePair> parameters = new ArrayList<>();
        if (paramMap != null) {
            for (String key : paramMap.keySet()) {
                parameters.add(new BasicNameValuePair(key, paramMap.get(key) + ""));
            }
        }
        parameters.add(new BasicNameValuePair("livedCode", String.valueOf(System.currentTimeMillis())));
        return _httpPost(url, parameters, charset, 0);
    }

    /**
     * HttpPost请求，控制尝试次数
     *
     * @param url
     * @param parameters
     * @param charset
     * @param reTry
     * @return
     */
    private String _httpPost(String url, List<BasicNameValuePair> parameters, String charset, int reTry) {
        String result = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader(new BasicHeader("Content-Type", "application/json; charset=" + charset));
            HttpEntity paramEntity = new UrlEncodedFormEntity(parameters, charset);
            httpPost.setEntity(paramEntity);
            // 执行请求访问
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 302
                logger.error("访问地址已经改变请更新访问地址");
            } else {
                logger.error("操作失败，Request URL：{}, params：{}", url, parameters.toString());
            }
        } catch (Exception e) {
            if (reTry < MAX_RETRY) {
                reTry++;
                logger.error("请求失败，尝试再次请求：{},Request URL：{}, params：{}", reTry, url, parameters.toString());
                return _httpPost(url, parameters, charset, reTry);
            } else {
                logger.error("请求异常，已超出最大尝试次数：{}，Request URL：{}, params：{},Exceptipn:{}", MAX_RETRY, url, parameters.toString(), e);
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("关闭httpclient连接出错，异常信息：{}", e);
            }
        }
        return result;
    }

    private static RequestConfig requestConfig = RequestConfig
            .custom()
            .setConnectTimeout(15000) // 设置连接超时时间
            .setConnectionRequestTimeout(30000) // 设置请求超时时间
            .setSocketTimeout(60000)// 设置读数据超时时间(单位毫秒)
            .setRedirectsEnabled(true)// 默认允许自动重定向
            .build();

}
