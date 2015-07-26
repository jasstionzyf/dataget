package com.yufei.dataget.dataretriver;





/**
 * @author jasstion
 * 2013-1-29
 * 用于实例化满足各种需求的HttpDataRetriever
 * 比如：一般的采用get方式请求网页内容的；
 * 采用浏览器去请求连接诶的返回js执行之后的网页内容；
 * 采用cookies去请求链接内容的；
 */
public class DataRetrieverFactory {
public static HttpDataRetriever  createDataRetriever(DataRetrieverFeatures dataRetrieverFeatures){
	HttpDataRetriever dataRetriever=null;
	if(dataRetrieverFeatures==null){
		throw new IllegalArgumentException("dataRetrieverFeatures can not be null!");
	}
	if(dataRetrieverFeatures.getRequestExecuteJs()==null){
		throw new IllegalArgumentException("IsRequestExecuteJs can not be null!");
	}
	    
		if(dataRetrieverFeatures.getRequestExecuteJs()){
			dataRetriever=new HttpDataRetriverUsingFirefoxDriverWithTimeOut(dataRetrieverFeatures);
			//dataRetriever=new HttpDataRetrieverForJs(dataRetrieverFeatures);

			
		}
		else{
			//普通的get方式获取网页内容
			if(dataRetrieverFeatures.getRequestType()==null||dataRetrieverFeatures.getRequestType().equalsIgnoreCase("get")){
				dataRetriever=new CommonDataRetriever(dataRetrieverFeatures);

			}
			//普通的post方式获取网页内容
			if(dataRetrieverFeatures.getRequestType()!=null&&dataRetrieverFeatures.getRequestType().equalsIgnoreCase("post")){
				dataRetriever=new HttpPostDataRetriever(dataRetrieverFeatures);

			}
					
		}
	
	
      
	
	
	return dataRetriever;
}
 
}
