package com.yao.feicui.newsapp.common;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by 16245 on 2016/06/04.
 */
public class HttpURLConnectionUtil {
    //    初始化网络连接对象
    private static HttpURLConnection sConnection = null;
    //设置超时时间
    private static int TimeOut = 10000;
    //设置最大连接数量为8
    private static int MaxTotalConnections = 8;

    /**
     * 设置HttpURLConnection对象的超时时间，最大连接数量
     */
    public static synchronized HttpURLConnection getHttpURLConnection(String url) {
        HttpURLConnection httpURLConnection = null;
        if (sConnection == null) {
            try {
                //建立连接对象
                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                //设置超时时间
                httpURLConnection.setConnectTimeout(TimeOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpURLConnection;
    }

    /**
     * 从网络上获取json数据
     */
    public static String getHttpJson(String url) {
        String jsonData = null;
        HttpURLConnection httpURLConnection = getHttpURLConnection(url);
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, length);
                }
                jsonData = outputStream.toString();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null && outputStream != null) {
                    inputStream.close();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return jsonData;
    }
}
