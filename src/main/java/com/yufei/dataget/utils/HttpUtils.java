package com.yufei.dataget.utils;

import com.google.common.base.Stopwatch;
import java.util.Iterator;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;

/**
 *
 * @author jasstion
 */
public class HttpUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     *
     */
    public final static Map<String, HttpClient> httpClientsMap = new ConcurrentHashMap<String, HttpClient>();

    /**
     *
     * @param connectionTimeout
     * @param soTimeout
     * @param connectionPoolTimeout
     * @return
     */
    public static HttpClient createHttpClientV3(int connectionTimeout, int soTimeout, int connectionPoolTimeout) {
        MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(500);

        httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(5000);

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        httpClient.getHttpConnectionManager().getParams().setTcpNoDelay(true);
        httpClient.getHttpConnectionManager().getParams().setStaleCheckingEnabled(false);
        HttpClientParams htp = new HttpClientParams();
        htp.setConnectionManagerTimeout(connectionPoolTimeout);
        httpClient.setParams(htp);

        httpClient.getHttpConnectionManager().getParams().setLinger(1000);
        return httpClient;
    }

    /**
     *
     * @param connectionTimeout
     * @param soTimeout
     * @param connectionPoolTimeout
     * @return
     */
    public static CloseableHttpClient createHttpClientV4(int connectionTimeout, int soTimeout, int connectionPoolTimeout) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(1000);
        cm.setDefaultMaxPerRoute(500);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, true);

        return httpClient;
    }

    /**
     *
     * @param thirdURL
     * @param connectionTimeout
     * @param soTimeout
     * @param connectionPoolTimeout
     * @param retryCount
     * @param params
     * @return
     */
    public static String executeWithHttpV4(String thirdURL, int connectionTimeout, int soTimeout, int connectionPoolTimeout, int retryCount, Map<String, String> params) {
        String result = null;

        throw new RuntimeException("Not Supported HttpClient Of V4!");

    }

    /**
     *
     * @param thirdURL
     * @param connectionTimeout 连接超时
     * @param soTimeout 返回超时
     * @param connectionPoolTimeout 等待连接池超时
     * @param retryCount
     * 失败之后的重试次数，仅仅支持超时以及服务器busy导致的连接失败，如果等待连接池超时或者服务器shutdown或者发送的url格式有问题等不会进行重试验直接返回错误信息
     * @param params 可以为NULL， 表示请求没有额外的参数
     * @return
     *
     * 使用此方法请确定使用合适的连接超时以及返回超时，retryCount值。 原则是尽可能的快速超时，快速重试
     * 另外需要注意的是，使用此方法时候尽量避免多个不同的connectionTimeout,soTimeout，
     * 目前的实现是上述两个值唯一的决定了一个连接池，如果你请求的太多这两个值不唯一，就有可能生成多个 连接池示例，有可能引起内存泄露
     */
    public static String executeWithHttpV3(String thirdURL, int connectionTimeout, int soTimeout, int connectionPoolTimeout, int retryCount, Map<String, Object> params) {

        String result = null;
        HttpMethod httpMethod = new GetMethod(thirdURL);
        NameValuePair[] nvps = convertMapToNameValuePair(params);
        if (nvps != null && (nvps.length > 0)) {
            httpMethod.setQueryString(nvps);
        }
        httpMethod.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new BaiheHtmlClientRetryHandler(retryCount));
        StringBuffer keyBuffer = new StringBuffer().append(String.valueOf(connectionTimeout)).append(String.valueOf(soTimeout)).append(String.valueOf(connectionPoolTimeout));
        HttpClient httpClient = httpClientsMap.get(keyBuffer.toString());
        if (httpClient == null) {
            httpClient = createHttpClientV3(connectionTimeout, soTimeout, connectionPoolTimeout);
            httpClientsMap.put(keyBuffer.toString(), httpClient);
        }

        try {
            Stopwatch timer = Stopwatch.createUnstarted();
            timer.start();
            int statusCode = httpClient.executeMethod(httpMethod);
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.info("执行Http Get方法出错,返回状态码:" + statusCode + "");
            }
            result = httpMethod.getResponseBodyAsString();
            timer.stop();
            long takedTime = timer.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.info("访问--> " + httpMethod.getURI().toString() + " 花费时间：" +takedTime+ " ms");

        } catch (Exception ex) {
            LOGGER.info("执行Http Get方法出错,异常信息是" + ex.getMessage() + "");
        } finally {
            httpMethod.releaseConnection();
        }

        return result;
    }

    /**
     *
     * @param params
     * @return
     */
    public static NameValuePair[] convertMapToNameValuePair(Map<String, Object> params) {
        if (params == null) {
            return null;
        }
        Set<String> paramsSet = params.keySet();
        NameValuePair[] nvps = new NameValuePair[paramsSet.size()];
        int i = 0;
        for (Iterator<String> it = paramsSet.iterator(); it.hasNext(); i++) {
            String name = it.next();
            Object value = params.get(name);
            nvps[i] = new NameValuePair(name, value.toString());
        }
        return nvps;
    }

}
