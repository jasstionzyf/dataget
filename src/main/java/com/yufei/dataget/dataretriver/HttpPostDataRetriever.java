package com.yufei.dataget.dataretriver;


import com.yufei.dataget.entity.ProxyServer;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class HttpPostDataRetriever extends AbstractHttpDataRetriever {
	private static Log mLog = LogFactory
			.getLog(HttpPostDataRetriever.class);

	private HttpClient httpclient=null;
	private HttpPost httpPost=null;
	HttpResponse response;

	public HttpPostDataRetriever(DataRetrieverFeatures dataRetrieverFeatures) {
		super(dataRetrieverFeatures);
		// TODO Auto-generated constructor stub
		init();
	}
	public void setUrl(URL url) {
		this.url = url;
		try {
			httpPost = new HttpPost(url.toURI());
			
		} catch (URISyntaxException e) {
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		httpclient = new DecompressingHttpClient(new DefaultHttpClient());
		setParametersForHttpClient();
		
	}
	private void setParametersForHttpClient() {

		//设置一些链接属性
		//链接超时
		Integer  connection_timeout=dataRetrieverFeatures.getConnectionTimeout();
		httpclient.getParams().
		setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connection_timeout==null?HttpDataRetriever.CONNECTION_TIMEOUT:connection_timeout);
	
		//请求超时
		Integer waitResponse_timeout=dataRetrieverFeatures.getRequestTimeout();
		httpclient.getParams().
		setIntParameter(CoreConnectionPNames.SO_TIMEOUT, waitResponse_timeout==null?HttpDataRetriever.REQUEST_TIMEOUT:waitResponse_timeout);
	
		//代理
		ProxyServer proxy = this.dataRetrieverFeatures.getProxy();
		if(proxy!=null){
			String port=proxy.getPort();
			HttpHost httpHost=new HttpHost(proxy.getHost(), Integer.parseInt(port));
	    	httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
			
		}
		//设置useragent
		String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.186 Safari/535.1";
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
	}
	public void setFormParams(List<BasicNameValuePair> formParams) {
		// TODO Auto-generated method stub
		HttpEntity entity=null;
		try {
			entity = new UrlEncodedFormEntity(formParams, "UTF-8");
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			mLog.error(e.getMessage());
			return;
		}
		this.httpPost.setEntity(entity);
	}
	@Override
	public String getHtmlContent() {
		// TODO Auto-generated method stub
		//return CommonUtil.getStrFromInputStream(inputStream, null);
		HttpEntity entity = response.getEntity();
		String cont=null;
		try {
			cont= EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			mLog.debug(ExceptionUtil.getExceptionDetailsMessage(e));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mLog.debug(ExceptionUtil.getExceptionDetailsMessage(e));
		}
		return cont;
	}


	@Override
	public void disconnect()  {
		// TODO Auto-generated method stub
		try{
			
			
			if(inputStream!=null){
				inputStream.close();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		}

	}

	@Override
	public void connect()  {
		// TODO Auto-generated method stub
		try {
			response = httpclient.execute(httpPost);
	
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
		     inputStream = entity.getContent();
		  
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public long getContentLenght()  {
		// TODO Auto-generated method stub
		return 0;
	}
public static void main(String[] args) throws Exception{
	/*<form target="_top" action="http://s.taobao.com/search" name="search" id="J_TSearchForm">
    <div class="search-panel-fields">
      <label for="q"><!-- Rgn file_name="fp2012/search_tips.php" site_id="1" --><span></span><!--end--></label>
      <input id="q" name="q" accesskey="s" autofocus="true" autocomplete="off" x-webkit-speech="" x-webkit-grammar="builtin:translate">
      <s></s>
    </div>
    <button type="submit">搜 索</button>
    <input type="hidden" name="commend" value="all">
    <input type="hidden" name="ssid" value="s5-e" autocomplete="off">
    <input type="hidden" name="search_type" value="item" autocomplete="off">
    <input type="hidden" name="sourceId" value="tb.index">
    <!--[if lt IE 9]><s class="search-fix search-fix-panellt"></s><s class="search-fix search-fix-panellb"></s><![endif]-->
  <input type="hidden" name="initiative_id" value="tbindexz_20130305"></form>*/
	/*HttpDataRetriever httpDataRetriever=DataRetrieverFactory.createDataRetriever(new DataRetrieverFeatures(false,"post"));
	List<BasicNameValuePair> formParams=new ArrayList<BasicNameValuePair>();
	String url="http://localhost:8080/jcategory";
	
	httpDataRetriever.setUrl(new URL(url));
	formParams.add(new BasicNameValuePair("alg", "a3"));
	formParams.add(new BasicNameValuePair("host", "i-54a14700.JS.dataprocess.eja.hk"));
	formParams.add(new BasicNameValuePair("port", "9081"));
	formParams.add(new BasicNameValuePair("groups", "21"));
	formParams.add(new BasicNameValuePair("max", "10"));

	formParams.add(new BasicNameValuePair("pissn", "1234"));
	formParams.add(new BasicNameValuePair("eissn", "1234"));

	formParams.add(new BasicNameValuePair("papers", "java language"));


	


	httpDataRetriever.setFormParams(formParams);

	httpDataRetriever.connect();
	System.out.print(httpDataRetriever.getHtmlContent());
	httpDataRetriever.disconnect();
	
 formParams=new ArrayList<BasicNameValuePair>();

	httpDataRetriever.setUrl(new URL(url));
	formParams.add(new BasicNameValuePair("alg", "a3"));
	formParams.add(new BasicNameValuePair("host", "i-54a14700.JS.dataprocess.eja.hk"));
	formParams.add(new BasicNameValuePair("port", "9081"));
	formParams.add(new BasicNameValuePair("groups", "21"));
	formParams.add(new BasicNameValuePair("max", "10"));

	formParams.add(new BasicNameValuePair("pissn", "1234"));
	formParams.add(new BasicNameValuePair("eissn", "1234"));

	formParams.add(new BasicNameValuePair("papers", "computer"));


	


	httpDataRetriever.setFormParams(formParams);

	httpDataRetriever.connect();
	System.out.print(httpDataRetriever.getHtmlContent());
	httpDataRetriever.disconnect();*/
	HttpDataRetriever httpDataRetriever=DataRetrieverFactory.createDataRetriever(new DataRetrieverFeatures(false,"post"));
	List<BasicNameValuePair> formParams=new ArrayList<BasicNameValuePair>();
	String url="http://www.ncbi.nlm.nih.gov/pubmed";
	
	httpDataRetriever.setUrl(new URL(url));
	/*formParams.add(new BasicNameValuePair("alg", "a3"));
	formParams.add(new BasicNameValuePair("host", "i-54a14700.JS.dataprocess.eja.hk"));
	formParams.add(new BasicNameValuePair("port", "9081"));
	formParams.add(new BasicNameValuePair("groups", "21"));
	formParams.add(new BasicNameValuePair("max", "10"));

	formParams.add(new BasicNameValuePair("pissn", "1234"));
	formParams.add(new BasicNameValuePair("eissn", "1234"));

	formParams.add(new BasicNameValuePair("papers", "java language"));*/
	List<String> formdatalines=FileUtil.readLines("/home/zhaoyufei/pubmed/post");
	for(String line:formdatalines){
		String key=null,value=null;
		String[] strs=new String[2];//line.split(":");
		int position=line.indexOf(":");
		strs[0]=line.substring(0, position);
		strs[1]=line.substring(position+1);
		if(strs.length<2){
			formParams.add(new BasicNameValuePair(strs[0],""));

		}
		else{
			formParams.add(new BasicNameValuePair(strs[0], strs[1]));

		}
	}
	List<String> requestHeaders=FileUtil.readLines("/home/zhaoyufei/pubmed/requestHeaders");
	for(String line:requestHeaders){
		String key=null,value=null;
		String[] strs=new String[2];//line.split(":");
		int position=line.indexOf(":");
		strs[0]=line.substring(0, position);
		strs[1]=line.substring(position+1);
		if(strs.length<2){
			httpDataRetriever.addHeader(strs[0],"");

		}
		else{
			httpDataRetriever.addHeader(strs[0],strs[1]);

		}
	}

	//request headers
	


	httpDataRetriever.setFormParams(formParams);

	httpDataRetriever.connect();
	System.out.print(httpDataRetriever.getHtmlContent());
	httpDataRetriever.disconnect();
	

	
}
@Override
public void addHeader(String key, String value) {
	// TODO Auto-generated method stub
	this.httpPost.setHeader(key, value);
}
}
