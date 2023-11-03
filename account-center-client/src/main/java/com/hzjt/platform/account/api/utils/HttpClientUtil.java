package com.hzjt.platform.account.api.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzjt.platform.account.api.model.AccountResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * HttpClientUtil
 * 功能描述：TODO
 *
 * @author zhanghaojie
 * @date 2023/10/31 09:30
 */
@Slf4j
public class HttpClientUtil {
    /**
     * 配置
     */
    private static final RequestConfig config = RequestConfig.custom()
            //连接超时时间 单位毫秒
            .setConnectTimeout(2000)
            // 请求超时时间 单位毫秒
            .setConnectionRequestTimeout(2000)
            // socket 读写超时时间
            .setSocketTimeout(1000)
            // 是否允许重定向 默认为true
            .setRedirectsEnabled(true)
            // 是否启用内容压缩 默认为true
            .setContentCompressionEnabled(true).build();
    /**
     * 获得Http客户端
     */
    private final static CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create()
            //失败重试 默认3次
            .setRetryHandler(new DefaultHttpRequestRetryHandler()).build();
    /**
     * 异步Http客户端
     */
    private final static CloseableHttpAsyncClient HTTP_ASYNC_CLIENT = HttpAsyncClients.custom().setDefaultRequestConfig(config).build();

    /**
     * 异步请求 * @param httpRequestBase
     */
    private static void executeAsync(final HttpRequestBase httpRequestBase) {
        HTTP_ASYNC_CLIENT.start();
        HTTP_ASYNC_CLIENT.execute(httpRequestBase, new FutureCallback<HttpResponse>() {
            @SneakyThrows
            @Override
            public void completed(HttpResponse httpResponse) {
                log.info("thread id is :{}", Thread.currentThread().getId());
                StringBuffer stringBuffer = new StringBuffer();
                Header[] requestHeaders = httpRequestBase.getAllHeaders();
                for (Header header : requestHeaders) {
                    stringBuffer.append(header.toString()).append(",");
                }
                log.info("HttpClientUtil executeAsync请求头信息 ：{}", stringBuffer.toString());
                String result = null;
                HttpEntity entity = httpResponse.getEntity();
                log.info("HttpClientUtil executeAsync响应状态：{}", httpResponse.getStatusLine().getStatusCode());
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
                log.info("HttpClientUtil executeAsync响应信息：{}", result);
                stringBuffer = new StringBuffer();
                Header[] responseHeaders = httpResponse.getAllHeaders();
                for (Header header : responseHeaders) {
                    stringBuffer.append(header.toString()).append(",");
                }
                log.info("HttpClientUtil executeAsync响应头信息 ：{}", stringBuffer.toString());
            }

            @Override
            public void failed(Exception e) {
                log.info("thread id is :{}", Thread.currentThread().getId());
                log.error("HttpClientUtil executeAsync请求失败 ：{}", e.getMessage());
            }

            @Override
            public void cancelled() {
                log.info(httpRequestBase.getRequestLine() + " cancelled");
            }
        });
    }

    /**
     * execute请求 * @param httpRequestBase * @return string
     */
    private static String execute(HttpRequestBase httpRequestBase) {
        log.info("HttpClientUtil execute请求地址：{}，请求类型：{}", httpRequestBase.getURI(), httpRequestBase.getMethod());
        log.info("HttpClientUtil execute请求参数信息:{}", httpRequestBase.getURI().getQuery());
        StringBuffer stringBuffer = new StringBuffer();
        Header[] requestHeaders = httpRequestBase.getAllHeaders();
        for (Header header : requestHeaders) {
            stringBuffer.append(header.toString()).append(",");
        }
        log.info("HttpClientUtil execute请求头信息：{}", stringBuffer.toString());
        String result = null;
        //响应模型
        CloseableHttpResponse response = null;
        try {
            httpRequestBase.setConfig(config);
            long startTime = System.currentTimeMillis();
            response = HTTP_CLIENT.execute(httpRequestBase);
            // 从响应模型中获取响应实体
            HttpEntity entity = response.getEntity();
            log.info("HttpClientUtil execute响应状态码：{}", response.getStatusLine().getStatusCode());
            long endTime = System.currentTimeMillis();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
                log.info("HttpClientUtil execute响应信息：{}", result);
            }
            stringBuffer = new StringBuffer();
            Header[] responseHeaders = response.getAllHeaders();
            for (Header header : responseHeaders) {
                stringBuffer.append(header.toString()).append(",");
            }
            log.info("HttpClientUtil execute响应头信息：{}", stringBuffer.toString());
            log.info("HttpClientUtil execute执行时间：{}", endTime - startTime);
        } catch (Exception e) {
            log.error("HttpClientUtil execute请求失败信息：{}", e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("HttpClientUtil execute exception result:{}", e.getMessage());
            }
        }
        return result;
    }

    /**
     * 处理get请求
     *
     * @param url    地址
     * @param params 参数
     * @return
     */
    public static <T> T doGet(String url, Map params, Class<T> responseType) {
        return conversion(doGet(url, params, new HashMap()), responseType);
    }

    /**
     * 处理get请求
     *
     * @param url     地址
     * @param params  参数
     * @param headers 请求头 header key-value
     * @return
     */
    public static String doGet(String url, Map params, Map headers) {
        CloseableHttpResponse response = null;
        try {
            //1.处理参数
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                for (Object param : params.keySet()) {
                    uriBuilder.setParameter(param + "", params.get(param) + "");
                }
            }
            HttpGet get = new HttpGet(uriBuilder.build());
            log.info("HttpClientUtil-GET>>>>>>>>>>请求：{}", get);
            //2.设置请求头信息
            if (headers != null && !headers.isEmpty()) {
                for (Object headerKey : headers.keySet()) {
                    get.setHeader(String.valueOf(headerKey), String.valueOf(headers.get(headerKey)));
                }
            }
            //3.请求数据
            response = HTTP_CLIENT.execute(get);
            //4.处理响应信息
            String result = null;
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                result = handleData(response.getEntity().getContent());
            }
            log.info("HttpClientUtil-GET>>>>>>>>>返回的数据：{}", result);
            return result;
        } catch (Exception e) {
            log.info("HttpClientUtil-GET 出错：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 处理post请求
     *
     * @param url    地址
     * @param params 参数
     * @return response
     */
    public static String doJsonPost(String url, String params, Map<String, String> head) {
        head.put("Content-Type", "application/json");
        return doPost(url, params, head);
    }

    /**
     * 处理post请求
     *
     * @param url    地址
     * @param params 参数
     * @return response
     */
    public static String doJsonPost(String url, String params, Map<String, String> head, Integer timeOut) {
        head.put("Content-Type", "application/json");
        return doPost(url, params, head, timeOut);
    }


    /**
     * 处理post请求
     *
     * @param url     地址
     * @param params  参数
     * @param headers 请求头 header key-value
     * @param timeOut 超时时间
     * @return response
     */
    private static String doPost(String url, String params, Map<String, String> headers, Integer timeOut) {
        HttpResponse response = null;
        try {
            //1.处理参数
            HttpPost post = new HttpPost(url);
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).build();
            post.setConfig(requestConfig);
            HttpEntity entity = new StringEntity(params, "UTF-8");
            post.setEntity(entity);
            //2.处理请求头信息
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    post.setHeader(key, String.valueOf(headers.get(key)));
                }
            }
            log.info("HttpClientUtil-POST>>>>>>>>>>请求：{}", post);
            //3.请求数据
            response = HTTP_CLIENT.execute(post);
            // 4.解析数据
            String result = null;
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                result = handleData(response.getEntity().getContent());
                log.info("HttpClientUtil-POST>>>>>>>>>>请求返回：{}", result);
            }
            return result;
        } catch (Exception e) {
            log.info("HttpClientUtil-POST 出错：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 处理post请求
     *
     * @param url     地址
     * @param params  参数
     * @param headers 请求头 header key-value
     * @return response
     */
    private static String doPost(String url, String params, Map<String, String> headers) {
        HttpResponse response = null;
        try {
            //1.处理参数
            HttpPost post = new HttpPost(url);
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(500).build();
            post.setConfig(requestConfig);
            HttpEntity entity = new StringEntity(params, "UTF-8");
            post.setEntity(entity);
            //2.处理请求头信息
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    post.setHeader(key, String.valueOf(headers.get(key)));
                }
            }
            log.info("HttpClientUtil-POST>>>>>>>>>>请求：{}", post);
            //3.请求数据
            response = HTTP_CLIENT.execute(post);
            // 4.解析数据
            String result = null;
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                result = handleData(response.getEntity().getContent());
                log.info("HttpClientUtil-POST>>>>>>>>>>请求返回：{}", result);
            }
            return result;
        } catch (Exception e) {
            log.info("HttpClientUtil-POST 出错：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 流对象 * @param is 流 * @return * @throws Exception
     */
    private static String handleData(InputStream is) throws Exception {
        int len = 0;
        //将is字节流，转化为字符流
        InputStreamReader reader = new InputStreamReader(is);
        //创建StringBuffer对象
        char[] buf = new char[1024];
        StringBuffer result = new StringBuffer();
        while ((len = reader.read(buf)) != -1) {
            result.append(String.valueOf(buf, 0, len));
        }
        reader.close();
        return String.valueOf(result);
    }


    public static <T> T conversion(String result, Class<T> responseType) {
        AccountResponse accountResponse = JSON.parseObject(result, AccountResponse.class);
        if (accountResponse.getIsSuccess() && Objects.nonNull(accountResponse.getData())) {
            return JSON.parseObject(result, responseType);
        }
        return null;
    }

}