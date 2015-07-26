package com.yufei.dataget.dataretriver;


import com.yufei.dataget.entity.ProxyServer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author jasstion
   2012-11-27
 *包含一些浏览器访问网页的参数比如：是否代理，超时设置等
 */
@XmlRootElement
public class DataRetrieverFeatures {
public DataRetrieverFeatures() {
		super();
		// TODO Auto-generated constructor stub
	}
public DataRetrieverFeatures(Boolean requestExecuteJs, ProxyServer proxy) {
		super();
		this.requestExecuteJs = requestExecuteJs;
		this.proxy = proxy;
	}
public DataRetrieverFeatures(Boolean requestExecuteJs, String requestType) {
		super();
		this.requestExecuteJs = requestExecuteJs;
		this.requestType = requestType;
	}

private Boolean requestExecuteJs=true;
/**
 * 系统能够利用此字符去获取对应的代理配置
 */
private ProxyServer proxy;
/**
 * 请求超时时间
 */
private Integer requestTimeout=10*1000;
private Integer retryCount=3;

/**
 * 链接超时时间
 */
private Integer connectionTimeout=10*1000;
public Integer getConnectionTimeout() {
	return connectionTimeout;
}
@XmlElement
public void setConnectionTimeout(Integer connectionTimeout) {
	this.connectionTimeout = connectionTimeout;
}
public boolean isCookiesSupport() {
	return isCookiesSupport;
}
@XmlElement
public void setCookiesSupport(boolean isCookiesSupport) {
	this.isCookiesSupport = isCookiesSupport;
}
public String getRequestType() {
	return requestType;
}
@XmlElement
public void setRequestType(String requestType) {
	this.requestType = requestType;
}
public Integer getRequestTimeout() {
	return requestTimeout;
}
@XmlElement
public void setRequestTimeout(Integer requestTimeout) {
	this.requestTimeout = requestTimeout;
}

public DataRetrieverFeatures(Boolean requestExecuteJs, ProxyServer proxy,
		Long connectIntervalTime,Integer requestTimeout) {
	super();
	this.requestExecuteJs = requestExecuteJs;
	this.proxy = proxy;
	this.connectIntervalTime = connectIntervalTime;
	this.requestTimeout=requestTimeout;
}
/**
 * 两次请求之间的时间间隔单位是秒
 */
private Long connectIntervalTime=new Long(0);


public Long getConnectIntervalTime() {
	return connectIntervalTime;
}
@XmlElement
public void setConnectIntervalTime(Long connectIntervalTime) {
	this.connectIntervalTime = connectIntervalTime;
}
public Boolean getRequestExecuteJs() {
	return requestExecuteJs;
}
@XmlElement
public void setRequestExecuteJs(Boolean requestExecuteJs) {
	this.requestExecuteJs = requestExecuteJs;
}
public Integer getRetryCount() {
	return retryCount;
}
@XmlElement
public void setRetryCount(Integer retryCount) {
	this.retryCount = retryCount;
}


public ProxyServer getProxy() {
	return proxy;
}
@XmlElement
public void setProxy(ProxyServer proxy) {
	this.proxy = proxy;
}
private boolean isCookiesSupport=false;
/**
 * 请求方式：request,post
 */

private String requestType=null;
public DataRetrieverFeatures(Boolean requestExecuteJs,
		ProxyServer proxyServerIdentity, Integer requestTimeout,
		Integer connectionTimeout, Long connectIntervalTime,
		boolean isCookiesSupport, String requestType) {
	super();
	this.requestExecuteJs = requestExecuteJs;
	this.proxy = proxyServerIdentity;
	this.requestTimeout = requestTimeout;
	this.connectionTimeout = connectionTimeout;
	this.connectIntervalTime = connectIntervalTime;
	this.isCookiesSupport = isCookiesSupport;
	this.requestType = requestType;
}

}
