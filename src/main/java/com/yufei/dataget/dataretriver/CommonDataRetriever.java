package com.yufei.dataget.dataretriver;


import com.google.common.collect.Maps;
import com.yufei.dataget.entity.ProxyServer;
import com.yufei.dataget.utils.HtmlUtil;
import com.yufei.utils.ExceptionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;



/**
 * created by @author jasstion at 2012-9-17
 */
public class CommonDataRetriever extends AbstractHttpDataRetriever {
	private static Log mLog = LogFactory
			.getLog(CommonDataRetriever.class);

	private HttpClient httpclient=null;
	private HttpGet httpget = null;
	private int currentRetry=0;
	HttpResponse response;

	/**
	 * @param dataRetrieverFeatures
	 */
	public CommonDataRetriever(DataRetrieverFeatures dataRetrieverFeatures) {
		super(dataRetrieverFeatures);
		init();
	
		// TODO Auto-generated constructor stub
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
	/*private ProxyServer setProxyServer() {
		String proxyServerIdentity=this.dataRetrieverFeatures.getProxyServerIdentity();
		ProxyServer proxyServer=null;
		if(!CommonUtil.isEmptyOrNull(proxyServerIdentity)){
			proxyServer=(ProxyServer) infoExtractorM.getProxyServer(proxyServerIdentity);
			if(proxyServer==null){
				mLog.info(""+proxyServerIdentity +"代理服务器没有配置！");
			}
			
		}
		return proxyServer;
	}*/
	/* (non-Javadoc)
	 * @see com.rzx.crawler.io.AbstractHttpDataRetriever#getHtmlContent()
	 */
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
		try{if(inputStream!=null){
			inputStream.close();

		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//访问延迟
			long conIntervalTime=dataRetrieverFeatures.getConnectIntervalTime();
			try {
				Thread.currentThread().sleep(conIntervalTime*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.httpget.releaseConnection();
                       

		}

	}

	@Override
	public void connect()  {
		// TODO Auto-generated method stub
		
			
		
		try {			httpget = new HttpGet(url.toURI());
                
                    for (Map.Entry<String,String> entry:headers.entrySet()) {
                        httpget.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
                    }
			response = httpclient.execute(httpget);
		
		
		/*
		if (entity != null) {
		     inputStream = entity.getContent();
		  
		}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
                    mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
			
			if(dataRetrieverFeatures.getRetryCount()>0&&dataRetrieverFeatures.getRetryCount()>currentRetry){
				currentRetry++;
				connect();
				mLog.info("访问链接失败开始进行第"+currentRetry+"次访问");
			}
			else{
				mLog.info("访问链接:"+this.getUrl().toString()+"失败,原因:"+e.getMessage()+"");
			}
		}
	}
	@Override
	public long getContentLenght() {
		// TODO Auto-generated method stub
		return 0;
	}
	





public static void main(String[] args) throws URISyntaxException, IOException{
/*	HttpClient httpclient1=new DefaultHttpClient();
	HttpClient httpclient = new DecompressingHttpClient(httpclient1);
	String userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.186 Safari/535.1";
	httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
	
	//HttpPost httpPost=new HttpPost(new URI("http://beijing.lashou.com/ajax/getComment.php"));
HttpGet httpPost=new HttpGet("http://www.dianping.com/shop/4125777/review_all?pageno=2");
	//http://beijing.lashou.com/deal/7365463.html?tj=1

String cookieValues="client_key=1359343360wb1109ed5fcf1071e99409; ThinkID=q0lnnct5ah07la26vpvmaejdl2; view_goods=%7B%227394541%22%3A7394541%2C%227342999%22%3A7342999%2C%227277020%22%3A7277020%7D; city_b=2419; __utma=1.1414663934.1359343363.1359356407.1359436714.3; __utmb=1.1.10.1359436714; __utmc=1; __utmz=1.1359343363.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)";
List<BasicClientCookie> basicClientCookies=new ArrayList();
	String[] cookies=cookieValues.split(";");
	BasicClientCookie stdCookie=null;
	for(String cookie:cookies){
		String[] strs=cookie.trim().split("=");
		stdCookie = new BasicClientCookie(strs[0], strs[1]);
		stdCookie.setVersion(1);
		stdCookie.setPath("/");
		stdCookie.setDomain("lashou.com");
		
		stdCookie.setSecure(true);
		stdCookie.setExpiryDate(DateUtil.getDate("2023-04-12 09:34:32"));
		basicClientCookies.add(stdCookie);
	}
	



	httpPost.setHeader(new BasicHeader("Accept", "application/json"));
	httpPost.setHeader(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
	httpPost.setHeader(new BasicHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3"));

	
	httpPost.setHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));

	httpclient.getParams().setParameter(CoreProtocolPNames.ORIGIN_SERVER, "http://www.lashou.com");
	httpPost.setHeader(new BasicHeader("Origin", "http://www.lashou.com"));

	httpPost.setHeader(new BasicHeader("Host", "beijing.lashou.com"));
	httpPost.setHeader(new BasicHeader("Referer", "http://www.lashou.com"));
	httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
	httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

	
	httpPost.setHeader(new BasicHeader("Proxy-Connection", "keep-alive"));
	//?goodsId=7350500
	List formParams = new ArrayList();
	formParams.add(new BasicNameValuePair("goodsId", "7372043"));
	formParams.add(new BasicNameValuePair("page", "1"));
	HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
   // httpPost.setEntity(entity);
    BasicCookieStore basicCookieStore=new BasicCookieStore();
    for(BasicClientCookie basicClientCookie:basicClientCookies){
    	basicCookieStore.addCookie(basicClientCookie);
    }
    httpPost.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
 // Create local HTTP context
 HttpContext localContext = new BasicHttpContext();
 // Bind custom cookie store to the local context
 localContext.setAttribute(ClientContext.COOKIE_STORE, basicCookieStore);
try {
	HttpResponse httpResponse=httpclient.execute(httpPost);//,localContext);

	String htmlContent= CommonUtil.getStrFromInputStream(httpResponse.getEntity().getContent(), "utf-8");
System.out.print(StringUtil.ascii2Native(htmlContent));
	//System.out.print(CommonUtil.getHtmlContent(null, false, "http://www.dianping.com/ajax/json/index/channel/common/searchbar?do=getHTML&cid=1&rid=10") );
} catch (ClientProtocolException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IllegalStateException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/
	
//	//****************************************************************
//	HttpClient httpclient=new DefaultHttpClient();
//	//HttpClient httpclient = new DecompressingHttpClient(httpclient1);
//	String userAgent="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:21.0) Gecko/20100101 Firefox/21.0";
//	httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, userAgent);
//	String url="https://encrypted.google.com/search?btnI=1&q=0306%2D4603%20%22addictive%20behaviors%22";
//	//String str=URLEncoder.encode("{\"Plant Genetic Resources\" }");
////	url=url.replace("{}", str);
//	System.out.print(url);
//	//Host:www.google.com.hk
//	HttpGet httpPost=new HttpGet(new URI(url));
//	//httpPost.setHeader(new BasicHeader("Host", "www.google.com.hk"));
//    
//	HttpResponse httpResponse=httpclient.execute(httpPost);
//	 for(Header h:httpResponse.getAllHeaders()){
//		 System.out.print(h.getName()+":"+h.getValue()+"\n");
//	 }
//	 System.out.print(httpResponse.getStatusLine().getStatusCode());
//	
	
	
	//String url="http://www.google.com/search?hl=en&q=%7B%22Plant+Genetic+Resources%22+%7D&btnI=I";

	
	//HttpGet httpPost=new HttpGet("http://www.lashou.com/deal/869571.html");
	//http://beijing.lashou.com/deal/7365463.html?tj=1

//String cookieValues="ezproxy=tv19y7SXmgqv2KL; wt3_eid=%3B935649882378213%7C2136340929900158848; ki_r=http%3A//hongvip.com/post.aspx%3Fid%3D130; ki_u=a1ec243d-c79a-b97c-3447-488c1fa2287a; __gads=ID=cfc539b985e29f5f:T=1363409300:S=ALNI_MZFIo5sBOYA_O3Zt6LVHOPxZr5_Vw; ki_t=1363409299492%3B1363409299492%3B1363409425273%3B1%3B2; __utma=68500236.2102166281.1363409300.1363409300.1363409300.1; __utmb=68500236.2.10.1363409300; __utmc=68500236; __utmz=68500236.1363409300.1.1.utmcsr=hongvip.com|utmccn=(referral)|utmcmd=referral|utmcct=/post.aspx; wt3_sid=%3B935649882378213";

	
//	httpPost.setHeader(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
//	httpPost.setHeader(new BasicHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3"));
//
//	
//	httpPost.setHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
//
//	//httpclient.getParams().setParameter(CoreProtocolPNames.ORIGIN_SERVER, "http://www.lashou.com");
////	httpPost.setHeader(new BasicHeader("Origin", "http://www.lashou.com"));
//
//	httpPost.setHeader(new BasicHeader("Host", "list.tmall.com"));
//	httpPost.setHeader(new BasicHeader("Referer", "http://list.tmall.com/search_product.htm?active=1&area_code=110000&vmarket=0&zk_type=0&miaosha=1&post_fee=-1&style=g&sort=d&end_price=29.9&cat=50025135"));
//	httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//	httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
//	//httpPost.setHeader("Cookie", cookieValues);
//	//
//	//Referer:
//	
//	httpPost.setHeader(new BasicHeader("Proxy-Connection", "keep-alive"));
//	//?goodsId=7350500
//
//
//    BasicCookieStore basicCookieStore=new BasicCookieStore();
//   
//    httpPost.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
// // Create local HTTP context
// HttpContext localContext = new BasicHttpContext();
// // Bind custom cookie store to the local context
// localContext.setAttribute(ClientContext.COOKIE_STORE, basicCookieStore);
//try {
//	HttpResponse httpResponse=httpclient.execute(httpPost,localContext);
//
//	String htmlContent= CommonUtil.getStrFromInputStream(httpResponse.getEntity().getContent(), "utf-8");
//System.out.print(htmlContent);
//	//System.out.print(CommonUtil.getHtmlContent(null, false, "http://www.dianping.com/ajax/json/index/channel/common/searchbar?do=getHTML&cid=1&rid=10") );
//} catch (ClientProtocolException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (IOException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (IllegalStateException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (Exception e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
	/*for(int i=0;i<100;i++){
		String html=HtmlUtil.getHtmlContent("http://localhost:8080/http://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.33.AuIt0U&id=16689211309&user_id=360514459&is_b=1&cat_id=50025783&q=&rn=02527713fa29f4eaaf5566d1c1cac3a0", false);
		System.out.print(PatternUtils.getStrByRegex(html, "\"J_CurPrice\">(.*?)</strong>")+"\n");

	}
*/
//<strong class="J_CurPrice">168.00</strong>
    String url="https://www.google.com.sg/?gws_rd=ssl";
    System.out.print(HtmlUtil.getHtmlContent(null, false, url));
    
    

}
private Map<String,String> headers=Maps.newHashMap();
@Override
public void addHeader(String key, String value) {
	// TODO Auto-generated method stub
	headers.put(key, value);
}


}
