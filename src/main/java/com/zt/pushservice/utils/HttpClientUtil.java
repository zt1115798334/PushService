package com.zt.pushservice.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author wangjianchun
 */
public class HttpClientUtil {
    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    public static byte[] buildFlumeData(Map<String, Object> headers, List<Map<String, Object>> datas) throws IOException {
        return buildFlumeData(headers, datas, false);
    }

    public static byte[] buildFlumeData(Map<String, Object> headers, List<Map<String, Object>> datas, boolean isCompress) throws IOException {

        if (headers == null || headers.isEmpty())
            throw new IllegalArgumentException("headers is null or empty.");
        if (headers.get("topic") == null || "".equals(headers.get("topic")))
            throw new IllegalArgumentException("headers.topic is null or empty.");
        if (datas == null || datas.isEmpty())
            throw new IllegalArgumentException("data is null or empty.");

        List<Object> ldata = new ArrayList<>();
        for (Map<String, Object> data : datas) {
            Map<String, Object> $data = new HashMap<>();
            $data.put("headers", new HashMap<>(headers));
            $data.put("body", JSON.toJSONString(data));
            ldata.add($data);
        }
        String rs = JSON.toJSONString(ldata);
        return isCompress ? compress(rs) : rs.getBytes("UTF-8");
    }

    public static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        return out.toByteArray();
    }

    // 解压缩
    public static byte[] uncompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return bytes;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String getResult(HttpRequestBase request) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                logger.info("发送成功啦");
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    return result;
                }
            }
            return "error";
        } catch(ClientProtocolException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

}
