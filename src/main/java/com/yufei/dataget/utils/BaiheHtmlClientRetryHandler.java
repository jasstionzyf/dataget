/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yufei.dataget.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.ProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jasstion
 */
public class BaiheHtmlClientRetryHandler implements HttpMethodRetryHandler {

    private int maxRetryCount = 5;

    public BaiheHtmlClientRetryHandler(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    private final static Logger mLog = LoggerFactory.getLogger(BaiheHtmlClientRetryHandler.class);

    @Override
    public boolean retryMethod(HttpMethod method, IOException exception, int executionCount) {
        String targetServer = method.getHostConfiguration().getHost();
        if (executionCount >maxRetryCount) {
            // Do not retry if over max retry count
            return false;
        }
        if (exception instanceof NoHttpResponseException) {
            // Retry if the server dropped connection on us
            mLog.info("Exception that can not connect to the target server " + targetServer + " for it too busy  to accept current connecton!");
            return true;
        }
        if (exception instanceof SocketTimeoutException) {
            // Retry if the client  unable to establish a connection with the target server or proxy server within the given period of time.
            mLog.info("Expection that can not get response from  target server " + targetServer + " within fixed amount time! ");
            return true;
        }
//        if (exception instanceof ConnectionPoolTimeoutException) {
//            // Retry if the client  fails to obtain a free connection from the connection pool within the given period of time.
//            mLog.info("Exception that can not obtain a free connection from current connection pool, current host is: " + targetServer + ", please check the connection config for this host! ");
//            return true;
//        }
        if (exception instanceof ConnectTimeoutException) {
            // Retry if the client  unable to establish a connection with the target server or proxy server within the given period of time.
            mLog.info("Expection that can not establish a connection with the target server " + targetServer + " within fixed amount time! ");
            return true;
        }
        
        if (exception instanceof ProtocolException) {
            mLog.info("Exception that violation of the HTTP specification against the target server: " + targetServer + "!");
            return false;

        }
        if (exception instanceof UnknownHostException) {
            mLog.info("UnknowHost:" + targetServer + "");
            return false;

        }
        if (exception instanceof ConnectException) {
            mLog.info(targetServer + " maybe have shutdown!");
            return false;

        }

        if (!method.isRequestSent()) {
            // Retry if the request has not been sent fully or
            // if it's OK to retry methods that have been sent
            return true;
        }
        // otherwise do not retry
        return false;
    }

}
