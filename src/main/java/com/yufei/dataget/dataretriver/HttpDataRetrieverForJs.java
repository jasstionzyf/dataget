package com.yufei.dataget.dataretriver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpDataRetrieverForJs extends AbstractHttpDataRetriever {
	private static Log mLog = LogFactory
			.getLog(HttpDataRetrieverForJs.class);
	private HttpDataRetrieverUsingWebkit httpDataRetrieverUsingWebkit;
    private String htmlContent=null;
	Integer currentRetry=0;
	public HttpDataRetrieverForJs(DataRetrieverFeatures dataRetrieverFeatures) {
		super(dataRetrieverFeatures);
		// TODO Auto-generated constructor stub
		httpDataRetrieverUsingWebkit=new HttpDataRetrieverUsingWebkit(dataRetrieverFeatures);
	}

	@Override
	public String getHtmlContent() {
		// TODO Auto-generated method stub
		return htmlContent;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		httpDataRetrieverUsingWebkit.setUrl(url);
		httpDataRetrieverUsingWebkit.connect();
		htmlContent=httpDataRetrieverUsingWebkit.getHtmlContent();
		httpDataRetrieverUsingWebkit.disconnect();
		if(dataRetrieverFeatures.getRetryCount()>0&&dataRetrieverFeatures.getRetryCount()>currentRetry){
			currentRetry++;
			connect();
			mLog.info("访问链接失败开始进行第"+currentRetry+"次访问");
		}


	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

}
