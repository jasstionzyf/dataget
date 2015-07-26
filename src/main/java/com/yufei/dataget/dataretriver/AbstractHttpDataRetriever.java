package com.yufei.dataget.dataretriver;


import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * @author jasstion
 * 基于http协议网页获取类的抽象类，里面有两个类需要子类实现
 *
 */
public abstract class AbstractHttpDataRetriever implements HttpDataRetriever {
protected URL url;
protected DataRetrieverFeatures dataRetrieverFeatures;
protected InputStream inputStream=null;
public void addHeader(String key, String value) {
	// TODO Auto-generated method stub
	throw new RuntimeException("此方法不支持！");
}
public void setFormParams(List<BasicNameValuePair> formParams) {
	// TODO Auto-generated method stub
	throw new RuntimeException("此方法不支持！");
}






	public   abstract String getHtmlContent();
	
		/*if(inputStream==null){
			return null;
		}
	return CommonUtil.getStrFromInputStream(inputStream, null);*/






	public AbstractHttpDataRetriever(DataRetrieverFeatures dataRetrieverFeatures) {
	super();
	this.dataRetrieverFeatures = dataRetrieverFeatures;
}


	public DataRetrieverFeatures getDataRetrieverFeatures() {
	return dataRetrieverFeatures;
}


public void setDataRetrieverFeatures(DataRetrieverFeatures dataRetrieverFeatures) {
	this.dataRetrieverFeatures = dataRetrieverFeatures;
}


public InputStream getInputStream() {
	return inputStream;
}


public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
}


public void setUrl(URL url) {
	this.url = url;
}


	public URL getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

	
	
	public abstract void connect() ;

	
	public abstract void disconnect() ;

	
	public long getContentLenght() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public InputStream getContent() {
		
		// TODO Auto-generated method stub
		return inputStream;
	}


	

	/* (non-Javadoc)
	 * @see com.rzx.crawler.io.DataRetriever#getConnectIntervalTime()
	 */
	
	public Long getConnectIntervalTime() {
		// TODO Auto-generated method stub
		return dataRetrieverFeatures.getConnectIntervalTime();
	}
}
