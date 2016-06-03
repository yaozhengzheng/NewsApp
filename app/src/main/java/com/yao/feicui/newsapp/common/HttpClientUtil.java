package com.yao.feicui.newsapp.common;

import org.apache.http.client.HttpClient;

/**
 * Created by 16245 on 2016/06/03.
 */
public class HttpClientUtil {
    //网络链接对象
    private static HttpClient mHttpClient;
    //超时时间
    private static int Timeout = 5000;
    //最大连接数量
    private static int MaxTotalConnections = 8;

}
